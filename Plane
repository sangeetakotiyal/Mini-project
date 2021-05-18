package com.grimnirdesign.scrollingshooter;

import android.graphics.Canvas;


public abstract class Plane extends Actor {

    public static final int PLANE_SPEED = 20;

    protected int mXSpeed, mYSpeed; // Speed

    public Plane(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }

    public int getXSpeed() {
        return mXSpeed;
    }

    public void setXSpeed(int xSpeed) {
        mXSpeed = xSpeed;
    }

    public int getYSpeed() {
        return mYSpeed;
    }

    public void setYSpeed(int ySpeed) {
        mYSpeed = ySpeed;
    }

    public abstract void fireWeapon();


    @Override
    public void update() {
        setX(getX() + getXSpeed());
        setY(getY() + getYSpeed());

    }

    @Override
    public void draw(Canvas canvas) {
        if ((getX() - getSize() / 2) >= 0 && (getX() - getSize()) <= canvas.getWidth() &&
                (getY() + getSize() / 2) >= 0 && (getY() - getSize() / 2) <= canvas.getHeight())
            mSprite.draw(canvas, getX() - getSize() / 2, getY() - getSize() / 2);

    }

}
