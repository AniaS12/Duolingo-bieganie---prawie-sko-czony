package Object;

import Other.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Clouds extends Enemy{

    private BufferedImage image;
    private int posX, posY;
    private Rectangle rectangle;
    private MainCharacter mainCharacter;
    private boolean addScore = false;

    public Clouds(MainCharacter mainCharacter){
        this.mainCharacter = mainCharacter;
        image = Resource.getResourceImage("data/cloud.png");
        posX = 800;
        posY = 522;
        rectangle = new Rectangle();
    }

    public void update(){
        posX -= 1;
        rectangle.x = posX;
        rectangle.y = posY;
        rectangle.width = image.getWidth();
        rectangle.height = image.getHeight() - 3;
    }

    public Rectangle getBound(){
        return rectangle;
    }

    public void draw(Graphics g){
        g.drawImage(image, posX, posY, null);
        //g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setX(int x){
        posX = x;
    }

    public void setY(int y){
        posY = y;
    }

    public void setImage(BufferedImage image){
        this.image = image;
    }

    public boolean isOutOfScreen(){
        return (posX + image.getWidth() < 0);
    }

    @Override
    public boolean isOver() {
        return(mainCharacter.getX() > posX);
    }

    @Override
    public boolean addScore() {
        return addScore;
    }

    @Override
    public void setAddScore(boolean addScore) {
        this.addScore = addScore;
    }
}
