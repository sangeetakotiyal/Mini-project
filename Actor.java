package com.grimnirdesign.scrollingshooter;
import android.graphics.Canvas;
import android.graphics.Rect;


public abstract class Actor {

    protected int mX, mY; // Position
    protected Sprite mSprite; // Sprite
    protected int mSize; // Sprite size

    public Actor(Sprite sprite, int x, int y) {
        mX = x;
        mY = y;
        mSprite = sprite;
        mSize = mSprite.getSize();
    }

    public abstract void update();

    public abstract void draw(Canvas canvas);

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
    }

    public int getSize() {
        return mSize;
    }

    public Rect getBounds() {
        return new Rect(mX - getSize() / 2, mY - getSize() / 2, getX() + getSize() / 2, getY() + getSize() / 2);
    }


}
