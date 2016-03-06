package yahtzee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mark-seygan on 3/3/16.
 */
public class DicePad {

    public JLabel dicePadJLabel;
    public ImageJPanel dicePadJPanel;
    public ImageIcon background;
    public ArrayList<Die> allDice = new ArrayList<>();
    public ImageIcon rollingDice;
    public JPanel keepPanel;

    public DicePad(){
        dicePadJLabel = new JLabel();
        background = new ImageIcon(getClass().getResource("background.png"));
        dicePadJPanel = new ImageJPanel();
        dicePadJPanel.setLayout(null);

        for (int i = 0; i < 5; i++) {
            allDice.add(new Die(i));
            int j = i;
            allDice.get(i).dieLabel.addMouseListener(
                new MouseAdapter() {
                    public void mousePressed(MouseEvent me) {
                        DicePad.dieMove(allDice.get(j));    }
                }
            );
            dicePadJPanel.add(allDice.get(i).dieLabel);
        }

        rollingDice = new ImageIcon(getClass().getResource("rollingDice.gif"));

        keepPanel = new JPanel();
        keepPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    public static void dieMove(Die d) {
        if(d.toKeep)
            d.toKeep = false;
        else
            d.toKeep = true;
    }

    class ImageJPanel extends JPanel {

        Random rng = new Random();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background.getImage(), 0, 0, null);

            Main.getmain().UpdateDiceLocations();

            for(Die d : allDice){
                keepPanel.repaint();
                if(d.rollingTime > 0) {
                    keepPanel.remove(d.dieLabel);
                    dicePadJPanel.add(d.dieLabel);
                    dicePadJPanel.add(d.dieLabel);
                    d.dieLabel.setBounds(d.x,d.y,d.getImg().getImage().getWidth(null),d.getImg().getImage().getHeight(null));
                    d.dieLabel.setIcon(null);

                    d.rollingTime--;

                    g.drawImage(rollingDice.getImage(),d.x,d.y,null);
                } else if(d.x != 0 && !d.toKeep) {
                    keepPanel.remove(d.dieLabel);
                    g.drawImage(d.getImg().getImage(),d.x,d.y,null);
                    dicePadJPanel.add(d.dieLabel);

                    d.dieLabel.setBounds(d.x,d.y,d.getImg().getImage().getWidth(null),d.getImg().getImage().getHeight(null));
                    d.dieLabel.setIcon(null);
                }
                else{
                    keepPanel.add(d.dieLabel);
                    d.dieLabel.setIcon(d.getSmaller());
                }
            }
        }
    }
}
