package yahtzee;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * Main class, has createAndShowGUI() method for all the components and initialization. In main method, there is implemented the main game cycle with player switching,
 * turn count and player dialog. These two methods are not commented, they are easy to follow.
 *
 * @author Marek Zidek
 */

public class Main {

    private DicePad dicepad;
    private static ScoreTable scoreTable;
    private int player;
    private int rolls;
    private volatile boolean turnEnd;
    private JButton roll;
    private static Timer mainTimer;

    public int getPlayer() {
        return player;
    }

    public DicePad getDicepad() {
        return dicepad;
    }

    public ScoreTable getScoreTable() {
        return scoreTable;
    }

    public void setTurnEnd(boolean b) {
        turnEnd = b;
    }

    public void enableRollButton() {
        roll.setEnabled(true);
    }

    public void disableRollButton() {
        roll.setEnabled(false);
    }

    public JFrame mainWindow;

    
    /**
     * Initializes all the components and prepares 
     */
    private void createAndShowGUI() {
        mainWindow = new JFrame("Yahtzee");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel();
        mainWindow.add(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        int gap = 15;
        mainPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));


        roll = new JButton("Hod");
        roll.addActionListener(e -> {

            disableRollButton();

            RollDice();
            dicepad.setFistRoll(false);

            if(mainTimer == null) {
                mainTimer = new Timer();
                mainTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        dicepad.dicePadJPanel.repaint();
                    }

                }, 50, 50);
            }
            rolls++;
            if(rolls >= 3) {
                roll.setEnabled(false);
            }
        });

        dicepad = new DicePad();

        scoreTable = new ScoreTable();
        mainPanel.add(scoreTable.table);

        mainPanel.add(dicepad.dicePadJPanel);
        dicepad.dicePadJPanel.setMinimumSize(new Dimension(20,280));
        dicepad.dicePadJPanel.setMaximumSize(new Dimension(2000,280));

        JPanel gaper = new JPanel();
        gaper.setBackground(Color.decode("0x765020"));
        gaper.setMaximumSize(new Dimension(2000,2));
        mainPanel.add(gaper);

        mainPanel.add(dicepad.keepPanel);
        dicepad.keepPanel.setMinimumSize(new Dimension(2000,50));
        dicepad.keepPanel.setMaximumSize(new Dimension(2000,80));
        dicepad.keepPanel.setBackground(Color.decode("0x775140"));

        mainPanel.add(roll);
        roll.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainWindow.setVisible(true);
        mainWindow.setMinimumSize(new Dimension(500,740));


    }

    Random rng = new Random();

    public void UpdateDiceLocations() {
        dicepad.allDice.get(0).x = mainWindow.getWidth()/40; dicepad.allDice.get(0).y = 140;
        dicepad.allDice.get(1).x = mainWindow.getWidth()/10*4; dicepad.allDice.get(1).y =130;
        dicepad.allDice.get(2).x = mainWindow.getWidth()/10*2; dicepad.allDice.get(2).y =25;
        dicepad.allDice.get(3).x = mainWindow.getWidth()/20*13; dicepad.allDice.get(3).y =163;
        dicepad.allDice.get(4).x = mainWindow.getWidth()/20*14; dicepad.allDice.get(4).y =2;
    }

    //private ArrayList<Integer> initLocations;
    public List<Die> RollDice(){
        List result = new ArrayList<Die>();
        for( Die d : dicepad.allDice) {
            if (!d.toKeep) {
                d.Animate();
                result.add(d.Roll());
            }
            else {
                d.justRolled = true;
                result.add(d);
            }
        }

        return result;
    }

    public static Main getmain() {
        return main;
    }

    private static Main main;

    /**
     * Has main game cycle, switches players, waits for inputs, does player dialog
     */
    public static void main(String[] args) {

        main = new Main();
        SwingUtilities.invokeLater(main::createAndShowGUI);

        while(true) {
            int turns = 13;

            while (turns > 0) {

                if(turns < 13)
                    main.enableRollButton();

                main.player = 0;
                if (scoreTable != null)
                    scoreTable.table.repaint();

                main.turnEnd = false;
                main.rolls = 0;
                if (main.roll != null)
                    main.roll.setEnabled(true);

                try {
                    while (!main.turnEnd) {
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupt");
                }
                
                main.dicepad.mouseEnabled = false;

                Enemy enemy = new Enemy();

                main.disableRollButton();

                main.player = 1;
                if (main.turnEnd) {
                    enemy.play();
                }

                main.turnEnd = false;

                main.dicepad.setFistRoll(true);

                for (Die d : main.dicepad.allDice) {
                    if (d.toKeep)
                        d.toKeep = false;
                }
                
                main.dicepad.mouseEnabled = true;

                turns--;
            }

            String text = "";
            if((Integer)main.getScoreTable().table.getValueAt(16,1) >= (Integer)main.getScoreTable().table.getValueAt(16,2))
                text = "Vyhrali jste, vase skore je " + (Integer)main.getScoreTable().table.getValueAt(16,1) + ". Nova hra?";
            else
                text = "Prohrali jste, vase skore je " + (Integer)main.getScoreTable().table.getValueAt(16,1) + ". Nova hra?";

            int response = JOptionPane.showConfirmDialog(null, text, "Konec hry",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                break;
            } else if (response == JOptionPane.YES_OPTION) {
                main.getScoreTable().cleanScoreTable();
            } else if (response == JOptionPane.CLOSED_OPTION) {
                break;
            }
        }

    }
}

