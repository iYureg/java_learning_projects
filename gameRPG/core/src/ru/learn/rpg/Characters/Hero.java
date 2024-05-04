package ru.learn.rpg.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.learn.rpg.GameScreen;
import ru.learn.rpg.Weapon;

public class Hero extends GameCharacter {

    public Hero(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.texture = new Texture("Knight.png");
        this.textureHp = new Texture("Bar.png");
        this.position = new Vector2(200, 200);
        this.maxHp = 100.0f;
        this.hp = maxHp;
        this.speed = 100.0f;
        this.weapon = new Weapon("Spear", 150.0f, 1.0f, 1.0f);
    }

    @Override
    public void update(float dt) {
        damageEffectTimer -= dt;
        if (damageEffectTimer < 0.0f) {
            damageEffectTimer = 0.0f;
        }

        float dst = gameScreen.getMonster().getPosition().dst(this.position);
        if (dst < weapon.getAttackRadius()) {
            attackTimer += dt;
            if (attackTimer > weapon.getAttackPeriod()) {
                attackTimer = 0.0f;
                gameScreen.getMonster().takeDamage(weapon.getDamage());
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            position.x += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            position.x -= speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            position.y += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            position.y -= speed * dt;

        checkScreenBounds();
    }
}
