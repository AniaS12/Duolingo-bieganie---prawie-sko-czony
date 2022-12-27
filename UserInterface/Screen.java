package UserInterface;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import Object.*;
import Other.Resource;

public class Screen extends JPanel implements Runnable, KeyListener {

    public static final int GAME_FIRST_STATE = 0;
    public static final int GAME_PLAY_STATE = 1;
    public static final int GAME_DEATH_STATE = 2;
    public static final float GRAVITY = 0.17f;      //szybkosc spadania
    public static final float GROUNDY = 700;        //poziom ziemi

    private MainCharacter mainCharacter;
    private Thread thread;
    private Land land;
    private EnemiesManager enemiesManager;
    private Menu menu;
    private double sleep = 5;

    public static int score;
    public static int gameState = GAME_FIRST_STATE;

    private BufferedImage imageGameOver;

    private AudioClip scoreUpSound;

    public Screen(){
        thread = new Thread(this);
        mainCharacter = new MainCharacter();
        menu = new Menu();
        mainCharacter.setX(50);
        mainCharacter.setY(650);
        land = new Land();
        enemiesManager = new EnemiesManager(mainCharacter, this);
        imageGameOver = Resource.getResourceImage("data/gameover.png");
        try {
            scoreUpSound = Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void startGame(){
        thread.start();
    }

    @Override
    public void run() {                 //poruszanie się świata
        while(true){
            try {
                update();
                repaint();
                Thread.sleep((long) sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        switch (gameState){
            case GAME_PLAY_STATE:
                mainCharacter.update();
                land.update();
                enemiesManager.update();

                if(!mainCharacter.getAlive()){
                    gameState = GAME_DEATH_STATE;
                    mainCharacter.state = 4;
                }
                break;
        }
    }

    public void plusScore(int score){
        this.score += score;
        scoreUpSound.play();
        sleep = sleep * 0.9;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);                 //zmiana otoczenia, przesuwa się

        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);

        switch (gameState){
            case GAME_FIRST_STATE:
                mainCharacter.draw(g);
                menu.drawMainMenu(g);
                break;
            case GAME_PLAY_STATE:
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                g.drawString("Droga: " + String.valueOf(score), 1000, 20);
                break;
            case GAME_DEATH_STATE:
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                g.drawImage(imageGameOver, 540, 500, null);
                break;
        }
    }

    private void resetGame() throws IOException {
        mainCharacter.setAlive(true);
        mainCharacter.setX(50);
        mainCharacter.setY(650);
        enemiesManager.reset();

        Writer output = new BufferedWriter(new FileWriter("data/score.txt", true));     //zapisywanie punktów do pliku
        output.append(String.valueOf(score) + "\r\n");
        output.close();

        score = 0;
        sleep = 5;
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            mainCharacter.jump();                   //skok
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            mainCharacter.duck(true);        //początek kucania
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                if (gameState == GAME_DEATH_STATE){
                    try {
                        resetGame();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    gameState = GAME_FIRST_STATE;
                }
                break;
            case KeyEvent.VK_DOWN:
                    mainCharacter.duck(false);       //koniec kucania
                break;
        }
    }
}


/*public class Main extends JFrame{
    public Menu mainMenu; //Deklaracja pierwszego menu
    public int room = 0; // Zmienna definiująca które menu ma wyświetlić

    public InfoMenu infoMenu; // Deklaracja menu z zasadami
    public SelectionMenu selectionMenu; // Deklaracja menu wyboru poziomu trudności
    public Game game; // Deklaracja pozycji, w której dzieje się akcja gry
    public boolean controls[]; //Deklaracja tablicy przycisków
    //pętla główna
    class Loop extends TimerTask {


        public void run()
        {
            //wszystko co związane z rozgrywką jest aktualizowane tutaj.
            if(actualRoom == game && !game.finish)
            {
                game.update(controls);
            }
            repaint();
        }

    }

    Main(){
        super("Math Invaders"); //Napis na okienku
        setBounds(50,50,1030,803); // Wymiary okienka
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Możliwość wyłączenia programu przez krzyżyk
        setResizable(false); //Zablokowanie możliwości rozciągania okienka
        setVisible(true);
        createBufferStrategy(2);

        this.mainMenu = new MainMenu();
        this.selectionMenu = new SelectionMenu();
        this.infoMenu = new InfoMenu();
        this.game = new Game();

        this.actualRoom =mainMenu;
        controls = new boolean[4];
        timer = new Timer();
        timer.scheduleAtFixedRate(new Loop(),0,1000/60);
        this.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        controls[0] = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        controls[1] = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        controls[2] = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        controls[3] = true;
                        break;
                }



            }

            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        controls[0] = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        controls[1] = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        controls[2] = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        controls[3] = false;
                        break;

                }
            }

            public void keyTyped(KeyEvent e) {
            }
        });
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
    }

    public static void main(String[] args)
    {
        Main window = new Main();
        window.repaint();
    }

    public void paint(Graphics g)
    {

        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics2D g2D = (Graphics2D)bstrategy.getDrawGraphics();
        //Zmiana punktu (0,0)
        g2D.translate(3,32);
        //Rysowanie w zależności od pozycji
        this.actualRoom.draw(g2D);
        g2D.dispose();
        bstrategy.show();

    }
}*/