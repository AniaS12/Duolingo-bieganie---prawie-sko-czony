package Object;

import Other.Resource;
import UserInterface.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemiesManager {

    private List<Enemy> enemies;
    private Random random;

    private BufferedImage imageTree, imageCloud;
    private MainCharacter mainCharacter;
    private Screen screen;

    public EnemiesManager(MainCharacter mainCharacter, Screen screen){
        this.screen = screen;
        this.mainCharacter = mainCharacter;
        enemies = new ArrayList<Enemy>();
        imageTree = Resource.getResourceImage("data/tree.png");
        imageCloud = Resource.getResourceImage("data/cloud.png");
        random = new Random();

        if(random.nextBoolean()){
            enemies.add(getTree());
        }
        else {
            enemies.add(getClouds());
        }
    }

    public void update(){
        for(Enemy e:enemies){
            e.update();

            if (e.isOver() && !e.addScore()){
                screen.plusScore(20);
                e.setAddScore(true);
            }

            if (e.getBound().intersects(mainCharacter.getBound())){
                mainCharacter.setAlive(false);
            }
        }

        Enemy firstEnemy = enemies.get(0);

        if(firstEnemy.isOutOfScreen()){
            enemies.clear();
            enemies.remove(firstEnemy);
            if(random.nextBoolean()){
                enemies.add(getTree());
            }
            else {
                enemies.add(getClouds());
            }
        }
    }

    public void draw(Graphics g){
        for (Enemy e:enemies){
            e.draw(g);
        }
    }

    public void reset(){
        enemies.clear();
        enemies.add(getTree());
    }

    private Tree getTree(){
        Tree tree;

        tree = new Tree(mainCharacter);
        tree.setX(1280);
        tree.setImage(imageTree);
        return tree;
    }

    private Clouds getClouds(){
        Clouds clouds;

        clouds = new Clouds(mainCharacter);
        clouds.setX(1280);
        return clouds;
    }
}
