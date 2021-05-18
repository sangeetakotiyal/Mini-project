package com.grimnirdesign.scrollingshooter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import com.grimnirdesigns.scrollingshooter.R;
import java.util.ArrayList;
public class MainActivity extends Activity {

    private static final int FRAME_RATE = 50;
    // Game Assets
    public GameBoard mGameBoard;
    public ArrayList<Plane> mEnemyPlanes;
    // System Assets
    public Handler frame = new Handler();
    public boolean mScreenTouched = false;
    public float mXCoordTouch, mYCoordTouch;
    public int frameCount = 0;
    private PlayerPlane mPlayerPlane;

    @Override
    synchronized public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_MOVE:
                mScreenTouched = true;
                mXCoordTouch = ev.getX(ev.getPointerCount() - 1);
                mYCoordTouch = ev.getY(ev.getPointerCount() - 1);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mScreenTouched = false;
                break;
        }

        return true;
    }

    synchronized public void startGraphics() {
        try {
            mPlayerPlane = new PlayerPlane(this, mGameBoard.getWidth() / 2, mGameBoard.getHeight() / 4 * 3);
        }
        catch (NullPointerException e)
        {

        }
        mEnemyPlanes = new ArrayList<Plane>();
        try {
            mGameBoard.setActors(mPlayerPlane, mEnemyPlanes);
        }
        catch (NullPointerException e)
        {

        }
        EnemyPlane enemyPlane = new EnemyPlane(getApplicationContext(), (int) (Math.random() * mGameBoard.getWidth()), -50);
        mEnemyPlanes.add(enemyPlane);

        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, FRAME_RATE);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mGameBoard = (GameBoard) findViewById(R.id.the_gameboard);

        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startGraphics();
            }
        }, 1000);
    }



    private void gameOver(int score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER")
                .setMessage("You have been destroyed! Your final score was: " + score)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private Runnable frameUpdate = new Runnable() {
        @Override
        public void run() {
            //---------------
            // UPDATE
            //---------------
            frameCount++;   // Update time counter

            // Auto-fire weapon
            if (frameCount % 10 == 0)
                mPlayerPlane.fireWeapon();

            // Create new enemies
            if (frameCount % 30 == 0) {
                EnemyPlane newPlane = new EnemyPlane(getApplicationContext(), (int) (Math.random() * mGameBoard.getWidth()), -50);
                mEnemyPlanes.add(newPlane);
            }

            // Update player bullets
            for (PlayerBullet bullet : mPlayerPlane.getBullets())
                bullet.update();

            // Update player
            if (mScreenTouched) {
                double angle = Math.atan2((double) (mPlayerPlane.getY() - mYCoordTouch), (double) (mXCoordTouch - mPlayerPlane.getX()));
                int xSpeed = (int) (PlayerPlane.PLANE_SPEED * Math.cos(angle));
                int ySpeed = (int) (-1 * PlayerPlane.PLANE_SPEED * Math.sin(angle));

                mPlayerPlane.setXSpeed(xSpeed);
                mPlayerPlane.setYSpeed(ySpeed);
            } else {
                mPlayerPlane.setXSpeed(0);
                mPlayerPlane.setYSpeed(0);
            }
            mPlayerPlane.update();

            //Keep player in bounds
            if (mPlayerPlane.getX() - mPlayerPlane.getSize() / 2 < 0)
                mPlayerPlane.setX(mPlayerPlane.getSize() / 2);
            if (mPlayerPlane.getX() + mPlayerPlane.getSize() / 2 > mGameBoard.getWidth())
                mPlayerPlane.setX(mGameBoard.getWidth() - mPlayerPlane.getSize() / 2);
            if (mPlayerPlane.getY() < mGameBoard.getHeight() / 2)
                mPlayerPlane.setY(mGameBoard.getHeight() / 2);
            if (mPlayerPlane.getY() + mPlayerPlane.getSize() / 2 > mGameBoard.getHeight() / 7 * 6)
                mPlayerPlane.setY(mGameBoard.getHeight() / 7 * 6 - mPlayerPlane.getSize() / 2);

            // Update enemy planes
            ArrayList<Plane> planesToRemove = new ArrayList<Plane>();
            for (Plane plane : mEnemyPlanes) {
                plane.update();
                if (plane.getBounds().top > mGameBoard.getHeight())
                    planesToRemove.add(plane);
            }
            // Object Removal
            for (Plane plane : planesToRemove) {
                mEnemyPlanes.remove(plane);
            }
            planesToRemove.clear();

            ArrayList<PlayerBullet> bulletsToRemove = new ArrayList<PlayerBullet>();

            // COLLISION CHECKS
            ArrayList<PlayerBullet> playerBullets = mPlayerPlane.getBullets();
            for (PlayerBullet bullet : playerBullets) {
                for (Plane badPlane : mEnemyPlanes) {
                    if (bullet.getBounds().intersect(badPlane.getBounds())) {
                        planesToRemove.add(badPlane);
                        bulletsToRemove.add(bullet);
                        mPlayerPlane.setScore(mPlayerPlane.getScore() + 100);
                    }
                }
            }

            for (Plane plane : mEnemyPlanes) {
                if (mPlayerPlane.getBounds().intersect(plane.getBounds())) {
                    mPlayerPlane.decrementHealth();
                    planesToRemove.add(plane);
                }
            }
            if (mPlayerPlane.getHealth() == 0) {
                gameOver(mPlayerPlane.getScore());
            }
            // Object Removal
            for (Plane plane : planesToRemove) {
                mEnemyPlanes.remove(plane);
            }
            planesToRemove.clear();
            for (PlayerBullet bullet : bulletsToRemove) {
                playerBullets.remove(bullet);
            }
            bulletsToRemove.clear();


            //---------------
            // RENDER
            //---------------


            mGameBoard.invalidate();
            if (mPlayerPlane.getHealth() > 0)
                frame.postDelayed(frameUpdate, FRAME_RATE);
        }
    };
}
