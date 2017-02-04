package yahtzee;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * object representing a die. Handling rng, its images and scaling it for a keep bar.
 *
 * @author Marek Zidek
 */
public class Die {

    Random rng = new Random();

    private int val;
    public boolean toKeep;
    public int rollingTime;
    private ImageIcon img;
    public int x,y;
    public boolean justRolled;

    public JLabel dieLabel;

    public int getVal(){
        return val;
    }

    public ImageIcon getImg(){
        return img;
    }


    public ImageIcon getSmaller() {
        BufferedImage smallerImg = new BufferedImage(this.img.getIconWidth()*2/3,this.img.getIconHeight()*2/3,BufferedImage.TYPE_INT_BGR);
        Graphics p = smallerImg.createGraphics();
        p.drawImage(this.getImg().getImage(),0,0,this.img.getIconWidth()*2/3,this.img.getIconHeight()*2/3,null);
        p.dispose();
        return new ImageIcon(smallerImg);
    }

    public Die Roll() {
        val = rng.nextInt(6) + 1;
        switch (val){
            case 1: img = new ImageIcon(getClass().getResource("jednicka.png")); break;
            case 2: img = new ImageIcon(getClass().getResource("dvojka.png")); break;
            case 3: img = new ImageIcon(getClass().getResource("trojka.png")); break;
            case 4: img = new ImageIcon(getClass().getResource("ctverka.png")); break;
            case 5: img = new ImageIcon(getClass().getResource("petka.png")); break;
            case 6: img = new ImageIcon(getClass().getResource("sestka.png")); break;
        }

        return this;
    }

    public void Animate(){
        rollingTime = rng.nextInt(20) + 10;
    }

    public Die(int val) {
        this.val = val;
        dieLabel = new JLabel();
        dieLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        justRolled = false;

        switch (val){
            case 0: img = new ImageIcon(getClass().getResource("jednicka.png")); break;
            case 2: img = new ImageIcon(getClass().getResource("dvojka.png")); break;
            case 3: img = new ImageIcon(getClass().getResource("trojka.png")); break;
            case 4: img = new ImageIcon(getClass().getResource("ctverka.png")); break;
            case 5: img = new ImageIcon(getClass().getResource("petka.png")); break;
            case 1: img = new ImageIcon(getClass().getResource("sestka.png")); break;
        }
    }



}
