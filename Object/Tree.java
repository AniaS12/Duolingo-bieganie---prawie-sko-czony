package Object;

import Other.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tree extends Enemy{

    private BufferedImage image;
    private int posX, posY;
    private Rectangle rectangle;
    private MainCharacter mainCharacter;
    private boolean addScore = false;

    public Tree(MainCharacter mainCharacter){
        this.mainCharacter = mainCharacter;
        posX = 400;
        posY = 655;
        rectangle = new Rectangle();
    }

    public void update(){                       //drzewo porusza się wraz ze światem
        posX -= 1;
        rectangle.x = posX;
        rectangle.y = posY;
        rectangle.width = image.getWidth();
        rectangle.height = image.getHeight();
    }

    @Override
    public Rectangle getBound(){                //rysowanie prostokąta, który może najść się z innym = śmierć
        return rectangle;
    }

    @Override
    public void draw(Graphics g){               //rysowanie drzewa //+ prostokąta
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

    @Override
    public boolean isOutOfScreen(){             //gdy drzewo jest poza oknem
        return (posX + image.getWidth() < 0);
    }

    @Override
    public boolean isOver() {                   //gdy bohater przeskoczył nad drzewem
        return(mainCharacter.getX() > posX);
    }

    @Override
    public boolean addScore() {                 //dodawanie punktów
        return addScore;
    }

    @Override
    public void setAddScore(boolean addScore) {
        this.addScore = addScore;
    }
}
