package ru.learn.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private Stage stage;
    private BitmapFont font24;
    private Map map;
    private ItemsEmitter itemEmitter;
    private TextEmitter textEmitter;
    private Hero hero;

    private float spawnTimer;

//    private Music music;
//    private Sound sound;
    private boolean paused;

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

    public TextEmitter getTextEmitter() {
        return textEmitter;
    }

    public void create() {
        map = new Map();
        allCharacters = new ArrayList<>();
        allMonsters = new ArrayList<>();
        hero = new Hero(this);
        itemEmitter = new ItemsEmitter();
        textEmitter = new TextEmitter();
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
        stage = new Stage();

        Skin skin = new Skin();
        skin.add("simpleButton", new Texture("SimpleButton.png"));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;

        TextButton pauseButton = new TextButton("Pause", textButtonStyle);
        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = !paused;
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Group buttonGroup = new Group();
        buttonGroup.addActor(pauseButton);
        buttonGroup.addActor(exitButton);
        exitButton.setPosition(150, 0);
        buttonGroup.setPosition(980, 680);

        stage.addActor(buttonGroup);
        Gdx.input.setInputProcessor(stage);


        drawOrderComparator = (o1, o2) -> (int) (o2.getPosition().x - o1.getPosition().y);

//        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
//        music.setLooping(true);
//        music.setVolume(0.1f);
//        music.play();
//
//        sound = Gdx.audio.newSound(Gdx.files.internal("sound.mp3"));
//        sound.setVolume(1, 0.1f);
//        sound.play();

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

        itemEmitter.render(batch);
        textEmitter.render(batch, font24);
        hero.renderHUD(batch, font24);
        batch.end();
        stage.draw();
    }


    public void update(float dt) {
        if(!paused) {
            spawnTimer += dt;
            if(spawnTimer > 5.0f){
                Monster monster = new Monster(this);
                allCharacters.add(monster);
                allMonsters.add(monster);
                spawnTimer = 0.0f;
            }
            for (int i = 0; i < allCharacters.size(); i++) {
                allCharacters.get(i).update(dt);
            }
            for (int i = 0; i < allMonsters.size(); i++) {
                Monster currentMonster = allMonsters.get(i);
                if (!currentMonster.isAlive()) {
                    allMonsters.remove(currentMonster);
                    allCharacters.remove(currentMonster);
                    itemEmitter.generateRandomItem(currentMonster.getPosition().x, currentMonster.getPosition().y, 5, 0.6f);
                    hero.killMonster(currentMonster);
                }
            }
            for (int i = 0; i < itemEmitter.getItems().length; i++) {
                Item it = itemEmitter.getItems()[i];
                if (it.isActive()) {
                    float dst = hero.getPosition().dst(it.getPosition());
                    if (dst < 24.0f) {
                        hero.useItem(it);
                    }
                }
            }
            textEmitter.update(dt);
            itemEmitter.update(dt);
        }
        stage.act(dt);
    }
}
