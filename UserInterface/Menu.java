package UserInterface;

import org.apache.groovy.groovysh.Main;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import Object.MainCharacter;
import UserInterface.Screen;

public class Menu {
    public Image background;
    public ArrayList<Button> buttonsMainMenu = new ArrayList<Button>();
    public ArrayList<Button> buttonsInstructionMenu = new ArrayList<Button>();

    public String rules[]= {"Witamy w grze 'Nous Courons.",
            "Twoim celem jest zdobycie największej liczby punktów!",
            "Możesz tego dokonać nurkując się pod chmurami - strzałka w dół - bądź skacząc nad drzewami - strzałka w górę."};

    Menu(){
        buttonsMainMenu.add(new Button(462,280,130,60, "Play"));
        buttonsMainMenu.add(new Button(462,360,130,60, "Instruction"));
        buttonsMainMenu.add(new Button(462,440,130,60, "Quit"));

        buttonsInstructionMenu.add(new Button(462, 600,130,60, "Return"));
    }

    public String checkMainMenuButtons(int x, int y){
        for(int i = 0; i<this.buttonsMainMenu.size();i++){

            if(this.buttonsMainMenu.get(i).clicked(x,y)){

                if(i == this.buttonsMainMenu.size()-1){
                    System.exit(0);
                }
                else if(i == 0){
                    this.buttonsMainMenu.get(i).refresh();
                    Screen.gameState = UserInterface.Screen.GAME_PLAY_STATE;
                    return "main";
                }
                else if(i == 1){
                    this.buttonsMainMenu.get(i).refresh();
                    //drawInstructionMenu();
                    return "instruction";
                }
            }
        }
        return "main";
    }

    public String checkInstructionMenuButtons(int x, int y){
        if(this.buttonsInstructionMenu.get(0).clicked(x,y)){

            this.buttonsInstructionMenu.get(0).refresh();
            return "main";
        }
        return "instruction";
    }

    public String click(int x, int y, String room){
        if(room.equals("main")){
            return checkMainMenuButtons(x,y);
        }
        else if(room.equals("instruction")){
            return checkInstructionMenuButtons(x,y);
        }
        return room;
    }

    public void move(int x, int y,String room){
        if(room.equals("main")) {
            for (int i = 0; i < this.buttonsMainMenu.size(); i++) {
                this.buttonsMainMenu.get(i).hovered(x,y);
            }
        }
        else if(room.equals("instruction"))
        {
            for (int i = 0; i < this.buttonsInstructionMenu.size(); i++) {
                this.buttonsInstructionMenu.get(i).hovered(x,y);
            }
        }
    }

    public void press(int x, int y, String room){
        if(room.equals("main")) {
            for (int i = 0; i < this.buttonsMainMenu.size(); i++) {
                this.buttonsMainMenu.get(i).pressed(x,y);
            }
        }
        else if(room.equals("instruction"))
        {
            for (int i = 0; i < this.buttonsInstructionMenu.size(); i++) {
                this.buttonsInstructionMenu.get(i).pressed(x,y);
            }
        }
    }

    public void draw(Graphics g, String room){
        switch (room) {
            case "main":
                this.drawMainMenu(g);
                break;
            case "instruction":
                this.drawInstructionMenu(g);
                break;
        }

    }

    public void drawMainMenu(Graphics g){
        g.setColor(Color.white);
        g.drawImage(background, 0,0,1024,768,null);
        for(int i =0;i < this.buttonsMainMenu.size();i++)
        {
            buttonsMainMenu.get(i).draw(g);
        }
    }

    public void drawInstructionMenu(Graphics g){
        g.drawImage(background, 0,0,1024,768,null);
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.BOLD,20));
        for(int i=0; i<rules.length;i++){                                       //Wypisanie kolejnych linijek zasad
            g.drawString(rules[i],50,50+20*i);
        }
        this.buttonsInstructionMenu.get(0).draw(g);
    }
}