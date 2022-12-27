package UserInterface;

import javax.swing.*;
import java.awt.*;

public class Button {
    public int x;
    public int y;
    public int width;
    public int height;
    public String text;
    public Image defaultSprite = (new ImageIcon("data/button.png")).getImage();
    public Image hoveredSprite = (new ImageIcon("data/buttonHovered.png")).getImage();
    public Image pressedSprite = (new ImageIcon("data/buttonPressed.png")).getImage();
    public Image sprite = defaultSprite;

    Button(int x, int y, int width, int height, String text){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public boolean collision(int x, int y){
        return x >=this.x && x<=this.x+this.width && y>=this.y && y<=this.y+this.height;
    }

    public boolean clicked(int x, int y)
    {
        return collision(x,y);
    }

    public void hovered(int x, int y){
        if(collision(x, y)){
            //this.sprite = hoveredSprite;
            this.sprite = (new ImageIcon("data/buttonHovered.png")).getImage();
        }
        else{
            this.sprite = defaultSprite;
            //this.sprite = (new ImageIcon("data/button.png")).getImage();
        }
    }

    public void pressed(int x, int y){
        if(collision(x, y)){
            //this.sprite = pressedSprite;
            this.sprite = (new ImageIcon("data/buttonPressed.png")).getImage();
        }
        else{
            this.sprite = defaultSprite;
        }
    }

    public void refresh(){
        this.sprite = defaultSprite;
    }

    public void draw(Graphics g){
        g.drawImage(this.sprite, this.x, this.y, this.width, this.height,null);
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString(text,x+5,y+35);
    }
}
