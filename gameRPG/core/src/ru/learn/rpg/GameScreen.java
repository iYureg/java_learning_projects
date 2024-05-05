package ru.learn.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.learn.rpg.Characters.GameCharacter;
import ru.learn.rpg.Characters.Hero;
import ru.learn.rpg.Characters.Monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GameScreen {
    private SpriteBatch batch;
    private BitmapFont font24;
    private Map map;
    private ItemsEmitter emitter;
    private Hero hero;

    private List<GameCharacter> allCharacters;
    private List<Monster> allMonsters;

    private Comparator<GameCharacter> drawOrderComparator;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;

    }

    public List<Monster> getAllMonsters() {
        return allMonsters;
    }

    public Map getMap() {
        return map;
    }

    public Hero getHero() {
        return hero;
    }


    public void create() {
        map = new Map();
        allCharacters = new ArrayList<>();
        allMonsters = new ArrayList<>();
        hero = new Hero(this);
        emitter = new ItemsEmitter();
        allCharacters.addAll(Arrays.asList(
                hero,
                new Monster(this),
                new Monster(this),
                new Monster(this),
                new Monster(this),
                new Monster(this),
                new Monster(this)
        ));

        for (int i = 0; i < allCharacters.size(); i++) {
            if (allCharacters.get(i) instanceof Monster) {
                allMonsters.add((Monster) allCharacters.get(i));
            }
        }
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));

        drawOrderComparator = new Comparator<GameCharacter>() {
            @Override
            public int compare(GameCharacter o1, GameCharacter o2) {
                return (int) (o2.getPosition().y - o1.getPosition().y);
            }
        };
    }

    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        map.render(batch);

        allCharacters.sort(drawOrderComparator);
        for (int i = 0; i < allCharacters.size(); i++) {
            allCharacters.get(i).render(batch, font24);
        }

        emitter.render(batch);
        hero.renderHUD(batch, font24);
        batch.end();
    }


    public void update(float dt) {
        for (int i = 0; i < allCharacters.size(); i++) {
            allCharacters.get(i).update(dt);
        }
        for (int i = 0; i < allMonsters.size(); i++) {
            Monster currentMonster = allMonsters.get(i);
            if(!currentMonster.isAlive()){
                allMonsters.remove(currentMonster);
                allCharacters.remove(currentMonster);
                emitter.generateRandomItem(currentMonster.getPosition().x, currentMonster.getPosition().y, 5, 0.6f);
                hero.killMonster(currentMonster);
            }
        }
        for (int i = 0; i < emitter.getItems().length; i++) {
            Item it = emitter.getItems()[i];
            if(it.isActive()){
                float dst = hero.getPosition().dst(it.getPosition());
                if(dst < 24.0f){
                    hero.useItem(it);
                }
            }
        }

        emitter.update(dt);
    }
}
