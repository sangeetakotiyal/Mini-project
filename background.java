package com.grimnirdesign.scrollingshooter;
import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Background {
    private int mWidth, mHeight;
    private Sprite mWaterSprite;
    private int mOffset;
    private Context mContext;
    private ArrayList<Island> mIslands = new ArrayList<Island>();
    private int mTimeCount = 0;

    public Background(Context context, int width, int height) {
        mWaterSprite = new Sprite(context, Sprite.WATER_SPRITE, Sprite.WATER_SPRITE_SIZE);

        mContext = context;

        mWidth = width;
        mHeight = height;
        mOffset = 0;

        Island island = new Island(context, (int) (Math.random() * getWidth()), -100);
        mIslands.add(island);
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {

        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public void update() {
        if (mOffset == 0 || mOffset % mWaterSprite.getSize() != 0) {
            mOffset += 8;
        } else {
            mOffset = 8;
        }

        mTimeCount++;
        if (mTimeCount % 50 == 0) {
            Island newIsland = new Island(mContext, (int) (Math.random() * getWidth()), -100);
            mIslands.add(newIsland);
        }

        ArrayList<Island> islandsToRemove = new ArrayList<Island>();
        for (Island island : mIslands) {
            island.update();

            if (island.getY() > getHeight() + 70)
                islandsToRemove.add(island);
        }
        for (Island island : islandsToRemove)
            mIslands.remove(island);
    }

    public void draw(Canvas canvas) {
        for (int x = 0; x < getWidth(); x += mWaterSprite.getSize()) {
            for (int y = -2 * mWaterSprite.getSize(); (y + mWaterSprite.getSize() / 2) < getHeight(); y += mWaterSprite.getSize()) {
                mWaterSprite.draw(canvas, x, y + mWaterSprite.getSize() + mOffset);
            }
        }

        for (Island island : mIslands)
            island.draw(canvas);
    }

    private class Island {
        private Sprite mIslandSprite;
        private int mX, mY, mSpeed = 8;

        public Island(Context context, int x, int y) {
            int rand = (int) (Math.random() * 3);
            if (rand == 0)
                mIslandSprite = new Sprite(context, Sprite.ISLAND_1_SPRITE, Sprite.ISLAND_SPRITE_SIZE);
            else if (rand == 1)
                mIslandSprite = new Sprite(context, Sprite.ISLAND_2_SPRITE, Sprite.ISLAND_SPRITE_SIZE);
            else
                mIslandSprite = new Sprite(context, Sprite.ISLAND_3_SPRITE, Sprite.ISLAND_SPRITE_SIZE);

            mX = x;
            mY = y;
        }

        public void update() {
            mY += mSpeed;
        }

        public void draw(Canvas canvas) {
            mIslandSprite.draw(canvas, mX, mY);
        }

        public int getY() {
            return mY;
        }
    }
}
