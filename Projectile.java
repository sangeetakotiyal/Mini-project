package com.grimnirdesign.scrollingshooter;

import android.graphics.Canvas;

public class Projectile extends Actor {

    protected static final int BULLET_SIZE = 32;
    protected int mSpeed;
    protected Sprite mSprite;

    public Projectile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }

    @Override
    public void update() {
        setY(getY() - mSpeed);
    }

    @Override
    public void draw(Canvas canvas) {
        mSprite.draw(canvas, getX() - BULLET_SIZE / 2, getY() - BULLET_SIZE / 2);
    }
}
