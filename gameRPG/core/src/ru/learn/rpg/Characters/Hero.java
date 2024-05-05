package ru.learn.rpg.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.learn.rpg.GameScreen;
import ru.learn.rpg.Item;
import ru.learn.rpg.Weapon;

public class Hero extends GameCharacter {
    private int coins;
    private int level;
    private int exp;
    private int[] expTo = {0, 0, 100, 300, 600, 1000, 5000};

    public Hero(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.level = 1;
        this.texture = new Texture("Knight.png");
        this.textureHp = new Texture("Bar.png");
        this.position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0, 720));
        while (!gameScreen.getMap().isCellPassable(position)) {
            this.position.set(MathUtils.random(0, 1280), MathUtils.random(0, 720));
        }
        this.direction = new Vector2(0, 0);
        this.temp = new Vector2(0, 0);
        this.maxHp = 100.0f;
        this.hp = maxHp;
        this.speed = 100.0f;
        this.weapon = new Weapon("Spear", 150.0f, 1.0f, 5.0f);
    }

    public void renderHUD(SpriteBatch batch, BitmapFont font24) {
        font24.draw(batch, "Knight: Yuriy\nLevel: " + level + "\nExp: " + exp + " / " + expTo[level+1] + "\nCoins: " + coins, 20, 700);
    }

    @Override
    public void update(float dt) {
        damageEffectTimer -= dt;
        if (damageEffectTimer < 0.0f) {
            damageEffectTimer = 0.0f;
        }

        Monster nearestMonster = null;
        float minDist = Float.MAX_VALUE;
        for (int i = 0; i < gameScreen.getAllMonsters().size(); i++) {
            float dst = gameScreen.getAllMonsters().get(i).getPosition().dst(this.position);
            if (dst < minDist) {
                minDist = dst;
                nearestMonster = gameScreen.getAllMonsters().get(i);
            }
        }

        if (nearestMonster != null && minDist < weapon.getAttackRadius()) {
            attackTimer += dt;
            if (attackTimer > weapon.getAttackPeriod()) {
                attackTimer = 0.0f;
                nearestMonster.takeDamage(weapon.getDamage());
            }
        }

        direction.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            direction.x = 1.0f;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            direction.x = -1.0f;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            direction.y = 1.0f;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            direction.y = -1.0f;

        temp.set(position).mulAdd(direction, speed * dt);
        if (gameScreen.getMap().isCellPassable(temp)) {
            position.set(temp);
        }
        checkScreenBounds();
    }

    public void killMonster(Monster mob) {
        exp += mob.maxHp * 5;
        if (exp >= expTo[level + 1]) {
            level++;
            exp -= expTo[level];
            maxHp += 10;
            hp = maxHp;
        }
    }

    public void useItem(Item it) {
        switch (it.getType()) {
            case COINS:
                coins += MathUtils.random(3, 5);
                break;
        }
        it.deactivate();
    }
}
