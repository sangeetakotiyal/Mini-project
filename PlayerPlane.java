package com.grimnirdesign.scrollingshooter;

import android.content.Context;

import java.util.ArrayList;

public class PlayerPlane extends Plane {

    private static final int PLAYERPLANE_SIZE = 64;
    private static final int DEFAULT_HEALTH = 3;
    private ArrayList<PlayerBullet> mBullets;
    private int mPlayerHealth;
    private int mScore = 0;

    private Context mContext;

    public PlayerPlane(Context context, int x, int y) {
        super(new Sprite(context, Sprite.PLAYER_PLANE_SPRITE_0, PLAYERPLANE_SIZE), x, y);

        mContext = context;


        mPlayerHealth = DEFAULT_HEALTH;
        mBullets = new ArrayList<PlayerBullet>();
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {

        mScore = score;
    }

    @Override
    public void fireWeapon() {
        PlayerBullet bullet = new PlayerBullet(mContext, getX(), getY() - getSize() / 2);
        mBullets.add(bullet);
    }

    public int getHealth() {
        return mPlayerHealth;
    }

    public void decrementHealth() {
        mPlayerHealth--;
    }

    public ArrayList<PlayerBullet> getBullets() {
        return mBullets;
    }
}
