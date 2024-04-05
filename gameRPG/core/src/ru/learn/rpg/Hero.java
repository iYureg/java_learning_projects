package ru.learn.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hero {
    private Texture texture;
    private Texture textureHp;
    private float x;
    private float y;
    private float speed;

    private float hp, maxHp;

    public Hero() {
        texture = new Texture("Knight.png");
        textureHp = new Texture("Bar.png");
        x = 200.0f;
        y = 200.0f;
        maxHp = 100.0f;
        hp = maxHp;
        speed = 100.0f;
    }

    public void takeDamage(float amount){
        hp -= amount;
    }

    public float getHp() {
        return hp;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public void recoverHp(float amount){
            hp += amount;

    }
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
        batch.setColor(1,0,0,1);
        batch.draw(textureHp, x, y + 80,0,0,hp,20,1,1,0,0,0,80,20,false,false);
        batch.setColor(1,1,1,1);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            x += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            x -= speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            y += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            y -= speed * dt;

    }
}
