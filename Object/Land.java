package Object;

import Other.Resource;
import UserInterface.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static UserInterface.Screen.GROUNDY;

public class Land{

    private List<ImageLand> listImage;
    private BufferedImage imageLand1, imageLand2, imageLand3;
    private Random random;

    public Land(){
        random = new Random();
        imageLand1 = Resource.getResourceImage("data/land1.png");
        imageLand2 = Resource.getResourceImage("data/land2.png");
        imageLand3 = Resource.getResourceImage("data/land3.png");
        listImage = new ArrayList<ImageLand>();
        int numberOfLandTiles = 1280 / imageLand1.getWidth() + 1;     //ile plytek ziemi potrzeba

        for(int i=0; i<numberOfLandTiles; i++){     //wypelnienie calej dlugosci ekranu
            ImageLand imageLand = new ImageLand();
            imageLand.posX = i * imageLand1.getWidth();
            imageLand.image = getImageLand();
            listImage.add(imageLand);
        }
    }

    public void update(){
        for(ImageLand imageLand:listImage){
            imageLand.posX --;
        }
        ImageLand firstElement = listImage.get(0);
        if (listImage.get(0).posX + imageLand1.getWidth() < 0){
            firstElement.posX = listImage.get(listImage.size() - 1).posX + imageLand1.getWidth();
            listImage.add(firstElement);
            listImage.remove(0);
        }
    }

    public void draw(Graphics g){
        for (ImageLand imageLand:listImage) {
            g.drawImage(imageLand.image, imageLand.posX, (int) GROUNDY - 20, null);   //rysowanie ziemi
        }
    }

    private BufferedImage getImageLand(){       //zmiana ziemi przez "losowanie"
        int i = random.nextInt(10);
        switch (i){
            case 0: return imageLand1;
            case 1: return imageLand3;
            default: return imageLand2;
        }
    }

    private class ImageLand{
        int posX;
        BufferedImage image;
    }
}
