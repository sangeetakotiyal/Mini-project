package com.grimnirdesign.scrollingshooter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class GameBoard extends View {

    private PlayerPlane mPlayerPlane;
    private List<Plane> mEnemyPlanes;
    private Background mBackground;

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBackground = new Background(context, getWidth(), getHeight());
    }

    public void setActors(PlayerPlane playerPlane, List<Plane> enemyPlanes) {
        mPlayerPlane = playerPlane;
        mEnemyPlanes = enemyPlanes;
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
        // Draw background
        mBackground.setWidth(getWidth());
        mBackground.setHeight(getHeight());
        mBackground.update();
        mBackground.draw(canvas);

        // Draw Player plane
        if (mPlayerPlane != null) {
            mPlayerPlane.draw(canvas);

            for (PlayerBullet bullet : mPlayerPlane.getBullets())
                bullet.draw(canvas);
        }

        // Draw enemy planes
        if (mEnemyPlanes != null)
            for (Plane plane : mEnemyPlanes) {
                if (inBounds(plane)) {
                    plane.draw(canvas);
                }
            }

        // Stats table
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        canvas.drawRect(0, getHeight() / 7 * 6, getWidth(), getHeight(), p);

        p.setColor(Color.WHITE);
        p.setTextSize(25);
        canvas.drawText("Score: " + (mPlayerPlane != null ? mPlayerPlane.getScore() : ""), 25, getHeight() / 7 * 6 + (getHeight() / 25), p);

        canvas.drawText("Lives: " + (mPlayerPlane != null ? mPlayerPlane.getHealth() : ""), getWidth() / 2, getHeight() / 7 * 6 + (getHeight() / 25), p);
    }

    private boolean inBounds(Actor actor) {
        return (actor.getX() + actor.getSize() / 2) >= 0 && (actor.getX() - actor.getSize() / 2) <= getWidth() &&
                (actor.getY() + actor.getSize() / 2) >= 0 && (actor.getY() - actor.getSize() / 2) <= getHeight();

    }


}
