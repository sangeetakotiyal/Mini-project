package com.grimnirdesign.scrollingshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.grimnirdesign.scrollingshooter.EnemyPlane;
import com.grimnirdesign.scrollingshooter.GameBoard;
import com.grimnirdesign.scrollingshooter.Plane;
import com.grimnirdesign.scrollingshooter.PlayerBullet;
import com.grimnirdesign.scrollingshooter.PlayerPlane;
import com.grimnirdesigns.scrollingshooter.R;


public class Sprite {

    public static final int PLAYER_PLANE_SPRITE_0 =R.drawable.spr_player_0;
    public static final int ENEMY_PLANE_SPRITE_0 = R.drawable.spr_enemy1_0;
    public static final int BULLET_UP_SPRITE = R.drawable.spr_bullet_up_0;
    public static final int ISLAND_1_SPRITE = R.drawable.spr_island1_0;
    public static final int ISLAND_2_SPRITE =R. drawable.spr_island2_0;
    public static final int ISLAND_3_SPRITE =R. drawable.spr_island3_0;
    public static final int ISLAND_SPRITE_SIZE = 64;
    public static final int WATER_SPRITE = R.drawable.water;
    public static final int WATER_SPRITE_SIZE = 32;

    private int mSize;
    private Bitmap mBitmap;

    public Sprite(Context context, int spriteID, int size) {
        mSize = size;

        mBitmap = BitmapFactory.decodeResource(context.getResources(), spriteID);

    }

    public void draw(Canvas canvas, int xPos, int yPos) {
        Paint paint = new Paint();
        canvas.drawBitmap(mBitmap, (float) xPos, (float) yPos, paint);
    }

    public int getSize() {
        return mSize;
    }

}
