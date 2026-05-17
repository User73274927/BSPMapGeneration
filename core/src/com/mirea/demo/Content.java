package com.mirea.demo;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Content {
    public TextureRegion sprite;
    public Rectangle bounds;

    public Content(TextureRegion sprite, Rectangle bounds) {
        this.sprite = sprite;
        this.bounds = bounds;
    }

    public Content(TextureRegion sprite) {
        this(sprite, new Rectangle());
    }

    public void draw(Batch batch) {
        batch.draw(sprite, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Content copy() {
        return new Content(sprite, new Rectangle(bounds));
    }
}
