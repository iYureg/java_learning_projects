package ru.learn.rpg.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.learn.rpg.GameScreen;
import ru.learn.rpg.Weapon;

public class Monster extends GameCharacter{

    private float moveTimer;
    private float activityRadius;


    public Monster(GameScreen gameScreen) {
        this.texture = new Texture("Skeleton.png");
        this.textureHp = new Texture("Bar.png");

        this.gameScreen = gameScreen;
        this.position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0,720));
        while (!gameScreen.getMap().isCellPassable(position)){
            this.position.set(MathUtils.random(0, 1280), MathUtils.random(0,720));
        }
        this.direction = new Vector2(0, 0);
        this.temp = new Vector2(0, 0);
        this.activityRadius = 200.0f;
        this.speed = 40.0f;
        this.maxHp = 20;
        this.hp = this.maxHp;
        this.weapon = new Weapon("Rusty Sword", 50.0f, 0.8f, 5.0f);
    }


    @Override
    public void update(float dt) {
        damageEffectTimer -= dt;
        if (damageEffectTimer < 0.0f) {
            damageEffectTimer = 0.0f;
        }

        float dst = gameScreen.getHero().getPosition().dst(this.position);
        if(dst < activityRadius){
            direction.set(gameScreen.getHero().getPosition()).sub(this.position).nor();

        } else {
            moveTimer -= dt;
            if (moveTimer < 0.0f) {
                moveTimer = MathUtils.random(1.0f, 4.0f);
                direction.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f));
                direction.nor();
            }
        }

        temp.set(position).mulAdd(direction, speed * dt);
        if(gameScreen.getMap().isCellPassable(temp)){
            position.set(temp);
        }


        if(dst < weapon.getAttackRadius()){
            attackTimer += dt;
            if(attackTimer >= weapon.getAttackPeriod()){
                attackTimer = 0.0f;
                gameScreen.getHero().takeDamage(weapon.getDamage());
            }
        }
        checkScreenBounds();
    }
}
