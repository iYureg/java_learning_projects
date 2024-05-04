package ru.learn.rpg.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.learn.rpg.GameScreen;
import ru.learn.rpg.Weapon;

public class Monster extends GameCharacter{
    private Vector2 direction;
    private Vector2 temp;
    private float moveTimer;
    private float activityRadius;


    public Monster(GameScreen gameScreen) {
        this.texture = new Texture("Skeleton.png");
        this.textureHp = new Texture("Bar.png");

        this.gameScreen = gameScreen;
        this.position = new Vector2(400.0f, 400.0f);
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
        if(gameScreen.getHero().getPosition().dst(this.position) < activityRadius){
            temp.set(gameScreen.getHero().getPosition()).sub(this.position).nor();
            position.mulAdd(temp, speed * dt);
        } else {
            position.mulAdd(direction, speed * dt);
            moveTimer -= dt;
            if (moveTimer < 0.0f) {
                moveTimer = MathUtils.random(1.0f, 4.0f);
                direction.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f));
                direction.nor();
            }
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
