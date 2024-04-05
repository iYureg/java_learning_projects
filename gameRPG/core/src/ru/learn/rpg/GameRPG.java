package ru.learn.rpg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameRPG extends ApplicationAdapter {
    private SpriteBatch batch;
    private Hero hero;
    private Monster monster;

    public Hero getHero() {
        return hero;
    }

    public Monster getMonster() {
        return monster;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        hero = new Hero();
        monster = new Monster(this);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        ScreenUtils.clear(0, 0.5f, 0, 1);
        batch.begin();
        hero.render(this.batch);
        monster.render(batch);
        batch.end();
    }

    public void update(float dt) {

        hero.update(dt);
        monster.update(dt);
        float dst = (float) Math.sqrt((hero.getX() - monster.getX()) * (hero.getX() - monster.getX()) + (hero.getY() - monster.getY()) * (hero.getY() - monster.getY()));
        if (dst < 40.0f) {
            hero.takeDamage(dt * 10.0f);
        } else {
            if(hero.getHp() < hero.getMaxHp()){
                hero.recoverHp(dt);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
