package com.grimnirdesign.scrollingshooter;

import android.content.Context;

public class PlayerBullet extends Projectile {
    private final int BASE_SPEED = 20;

    public PlayerBullet(Context context, int x, int y) {
        super(new Sprite(context, Sprite.BULLET_UP_SPRITE, BULLET_SIZE), x, y);

        mSprite = new Sprite(context, Sprite.BULLET_UP_SPRITE, BULLET_SIZE);
        mSpeed = BASE_SPEED;
    }

}
