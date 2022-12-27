package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Window extends JFrame {

    private Screen screen;
    public Menu menu;
    public String room = "main";

    public Window(){
        super("Nous Courons");
        setSize(1280,1024);
        setLocation(150,0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Możliwość wyłączenia programu przez krzyżyk
        setResizable(false);                            //Zablokowanie możliwości rozciągania okienka
        screen = new Screen();
        add(screen);
        addKeyListener(screen);

        this.menu = new Menu();

        getContentPane().addMouseListener(new MouseE(){ //Obsługa kliknięć myszy
            public void mouseClicked(MouseEvent e){
                changeRoom(e.getX(), e.getY());
            }

            public void mousePressed(MouseEvent e){
                changeButtonsStatus(e.getX(), e.getY(),"press");
                repaint();
            }

            public void mouseReleased(MouseEvent e){
                changeRoom(e.getX(), e.getY());
            }
        });

        getContentPane().addMouseMotionListener(new MouseE(){
            public void mouseMoved (MouseEvent e){
                changeButtonsStatus(e.getX(), e.getY(),"move");
            }
        });
    }

    public void changeButtonsStatus(int x, int y, String ev){
        if(ev.equals("move"))
        {
            menu.move(x,y,room);
        }
        else if(ev.equals("press")) {
            menu.press(x,y,room);
        }
        repaint();
    }

    public void changeRoom(int x, int y){
        room = menu.click(x, y, room);
        repaint();
    }

    public void startGame(){
        screen.startGame();
    }

    public static void main(String[] args){
        Window nw = new Window();
        nw.setVisible(true);
        nw.startGame();
    }

    public void paint(){
        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics g = (Graphics)bstrategy.getDrawGraphics();
        g.translate(3,32);
        this.menu.draw(g, room);
        g.dispose();
        bstrategy.show();

    }
}

/*this.mainMenu = new Menu();
        controls = new boolean[4];
        getContentPane().addMouseListener(new MouseE(){ //Obsługa kliknięć myszy


            public void mousePressed(MouseEvent e)
            {
                actualRoom.press(e.getX(), e.getY());
                //Wywołanie funkcji opowiedzialnej za rysowanie
                repaint();
            }
            public void mouseReleased(MouseEvent e) {

                room = actualRoom.click(e.getX(), e.getY());
                switch (room) {
                    case 0:
                        actualRoom= mainMenu;
                        break;
                    case 1:
                        actualRoom= selectionMenu;
                        break;
                    case 2:
                        actualRoom= infoMenu;
                        break;
                    default:
                        if(actualRoom!=game)
                        {
                            actualRoom= game;
                            game.begin(room-2);
                        }

                        break;
                }
                //Wywołanie funkcji opowiedzialnej za rysowanie
                repaint();
            }
        });
        getContentPane().addMouseMotionListener(new MouseE(){

            public void mouseMoved (MouseEvent e)
            {

                actualRoom.move(e.getX(), e.getY());

                //Wywołanie funkcji opowiedzialnej za rysowanie
                repaint();
            }
        });
    }*/
