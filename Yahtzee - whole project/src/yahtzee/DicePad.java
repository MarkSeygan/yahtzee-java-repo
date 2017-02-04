package yahtzee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * dicepad is an object which offers a place
 * for the dice to be rolled on. It controls painting, rolling times,
 * acknowledges and calls other objects and their method when all dice
 * is has just stopped rolling
 *
 * @author Marek Zidek
 */
public class DicePad {

    public ImageJPanel dicePadJPanel;
    public ImageIcon background;
    public ArrayList<Die> allDice = new ArrayList<>();
    public ImageIcon rollingDice;
    public JPanel keepPanel;
    private static boolean firstRoll;
    private ArrayList<Integer> currentCombinations;
    
    public boolean mouseEnabled = true;

    public ArrayList<Integer> getCurrentCombinations() {
        return currentCombinations;
    }

    public void setFistRoll(boolean fistRoll) {
        this.firstRoll = fistRoll;
    }

    /**
     * DicePad constructor. Sets up the image panel, mouseListeners to move dice for keeping
     */
    public DicePad(){
        background = new ImageIcon(getClass().getResource("background.png"));
        dicePadJPanel = new ImageJPanel();
        dicePadJPanel.setLayout(null);
        firstRoll = true;

        for (int i = 0; i < 5; i++) {
            allDice.add(new Die(i));
            int j = i;
            allDice.get(i).dieLabel.addMouseListener(
                new MouseAdapter() {
                    public void mousePressed(MouseEvent me) {
                    	if(mouseEnabled)
                    		Main.getmain().getDicepad().dieMove(allDice.get(j)); }
                }
            );
            dicePadJPanel.add(allDice.get(i).dieLabel);
        }

        rollingDice = new ImageIcon(getClass().getResource("rollingDice.gif"));

        keepPanel = new JPanel();
        keepPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    /**
     * moves dice. Just sets global vars for a repaint method to get thing moving.
     * @param d die to move
     */
    public void dieMove(Die d) {
        if(!firstRoll) {
            if (d.toKeep)
                d.toKeep = false;
            else {
                d.toKeep = true;
            }
        }
    }

    private int allRolledCount;


    /**
     * panel for dicepad with repaint method
     * @author Marek Zidek
     */
    class ImageJPanel extends JPanel {

        /**
         * gets repainted by mainTimer and handles moving the dice,
         * rolling dice, showing the actual values, and calling ShowPossible at ScoreTable object
         * to show score values
         */
        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.drawImage(background.getImage(), 0, 0, null);

            Main.getmain().UpdateDiceLocations();

            for(Die d : allDice){

                if(d.justRolled) {
                    allRolledCount++;
                    d.justRolled = false;
                }
                if(allRolledCount == 5) {

                    if(Main.getmain().getPlayer() == 0)
                        Main.getmain().enableRollButton();

                    ScoreTable st = Main.getmain().getScoreTable();

                    currentCombinations = st.FindPossibleCombinations(
                            new ArrayList<>(
                                    Arrays.asList(
                                            allDice.get(0),
                                            allDice.get(1),
                                            allDice.get(2),
                                            allDice.get(3),
                                            allDice.get(4))));

                    st.ShowPossible(currentCombinations,Main.getmain().getPlayer());

                    for (int i = 0; i < 5; i++)
                        allDice.get(i).justRolled = false;
                    allRolledCount = 0;

                    Enemy.allRolled =true;
                }

                keepPanel.repaint();
                if(d.rollingTime > 0) {
                    keepPanel.remove(d.dieLabel);
                    dicePadJPanel.add(d.dieLabel);
                    dicePadJPanel.add(d.dieLabel);
                    d.dieLabel.setBounds(d.x,d.y,d.getImg().getImage().getWidth(null),d.getImg().getImage().getHeight(null));
                    d.dieLabel.setIcon(null);

                    d.rollingTime--;
                    if(d.rollingTime == 0)
                        d.justRolled = true;

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
