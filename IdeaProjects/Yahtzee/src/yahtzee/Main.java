package yahtzee;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mark-seygan on 3/3/16.
 */

public class Main {

    private DicePad dicepad;

    JFrame mainWindow;



    private void createAndShowGUI() {
        mainWindow = new JFrame("Yahtzee");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel();
        mainWindow.add(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        int gap = 15;
        mainPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));


        JButton roll = new JButton("Hod");
        roll.addActionListener(e -> {
            RollDice();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    dicepad.dicePadJPanel.repaint();
                }
            }, 50, 50);
        });

        dicepad = new DicePad();

        ScoreTable scoreTable = new ScoreTable();
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
        mainWindow.setMinimumSize(new Dimension(500,700));


    }

    Random rng = new Random();

    public void UpdateDiceLocations() {
        dicepad.allDice.get(0).x = mainWindow.getWidth()/40; dicepad.allDice.get(0).y = 140;
        dicepad.allDice.get(1).x = mainWindow.getWidth()/10*4; dicepad.allDice.get(1).y =130;
        dicepad.allDice.get(2).x = mainWindow.getWidth()/10*2; dicepad.allDice.get(2).y =25;
        dicepad.allDice.get(3).x = mainWindow.getWidth()/20*13; dicepad.allDice.get(3).y =163;
        dicepad.allDice.get(4).x = mainWindow.getWidth()/20*14; dicepad.allDice.get(4).y =2;

    }

    private ArrayList<Integer> initLocations;
    private void RollDice(){
        int i = -1;
        for( Die d : dicepad.allDice) {
            if (!d.toKeep) {
                d.Animate();
                d.Roll();
            }
        }
    }

    public static Main getmain() {
        return main;
    }

    private static Main main;

    public static void main(String[] args) {

        main = new Main();

        SwingUtilities.invokeLater(main::createAndShowGUI);

        //logger = new logeer;

        //start okna(uvnitr se zobrazi vsechno ve sve krase), ale nad tim se ukaze super videjko :D :D

        //ask na play mode

        //run game (arg play mode)

        //ask na new game( new mode, replay this mode)

    }
}
