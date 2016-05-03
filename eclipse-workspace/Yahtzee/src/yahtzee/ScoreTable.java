package yahtzee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mark-seygan on 3/3/16.
 */
public class ScoreTable {

    private final int tableRows = 17;
    private final int tableCols = 3;
    private boolean[][] filledCells;
    public JTable table;
    private boolean[] bonus;
    private Component preparedTableRenderer;

    public boolean[][] getFilledCells() {
        return filledCells;
    }

    public void cleanScoreTable() {
        filledCells = new boolean[2][13];
        bonus = new boolean[2];

        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < 16; i++) {
                    table.setValueAt("", i + 1, k + 1);
            }
        }
    }

    public ScoreTable() {

        filledCells = new boolean[2][13];

        table = new JTable(tableRows,tableCols){

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {


                preparedTableRenderer = super.prepareRenderer(renderer, row, column);
                JComponent jc = (JComponent)preparedTableRenderer;
                preparedTableRenderer.setForeground(Color.black);

                ((DefaultTableCellRenderer) preparedTableRenderer).setBorder(new EmptyBorder(1,1,1,1));

                preparedTableRenderer.setBackground(Color.white);

                //DEFAULT cell editor
               // http://stackoverflow.com/questions/12348932/change-background-color-of-one-cell-in-jtable
                if(row == 0 && column == 1 && Main.getmain().getPlayer() == 0)
                    preparedTableRenderer.setBackground(Color.gray);
                if(row == 0 && column == 2 && Main.getmain().getPlayer() == 1)
                    preparedTableRenderer.setBackground(Color.gray);


                if(row == 7 || row == 8 || row == 16 || row == 0) {
                    preparedTableRenderer.setFont(preparedTableRenderer.getFont().deriveFont(Font.BOLD));
                }
                if(row == 0) {
                    jc.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
                }
                if(row == 16 || row == 7)
                    jc.setBorder(new MatteBorder(1, 0, 0, 0, Color.black));
                if(row == 8)
                    jc.setBorder(new MatteBorder(1, 0, 1, 0, Color.black));
                ((DefaultTableCellRenderer)preparedTableRenderer).setHorizontalAlignment(SwingConstants.LEFT);


                if(column != 0) {
                    for (int i = 0; i < 6; i++) {
                        if (filledCells[column - 1][i] == false && row == i + 1)
                            preparedTableRenderer.setForeground(Color.orange);
                    }

                    for (int i = 6; i < 13; i++) {
                        if (filledCells[column - 1][i] == false && row == i + 3)
                            preparedTableRenderer.setForeground(Color.orange);
                    }
                }



                return preparedTableRenderer;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    ScoreTableMouseClick(target);
                }
            }
        });

        table.setRowSelectionAllowed(false);
        table.setRowHeight(18);
        table.setBorder(new MatteBorder(1,1,1,1, Color.black));

        table.setValueAt("Hráč",0,1);
        table.setValueAt("Počítač",0,2);

        table.setValueAt(" Yahtzee",0,0);
        table.setValueAt(" Jedničky",1,0);
        table.setValueAt(" Dvojky",2,0);
        table.setValueAt(" Trojky",3,0);
        table.setValueAt(" Čtyřky",4,0);
        table.setValueAt(" Pětky",5,0);
        table.setValueAt(" Šestky",6,0);

        table.setValueAt(" Horní součet",7,0);
        table.setValueAt(" Bonus",8,0);

        table.setValueAt(" 3 of kind",9,0);
        table.setValueAt(" 4 of kind",10,0);
        table.setValueAt(" Full house",11,0);
        table.setValueAt(" Malá postupka",12,0);
        table.setValueAt(" Velká postupka",13,0);
        table.setValueAt(" Yahtzee!",14,0);
        table.setValueAt(" Šance",15,0);

        table.setValueAt(" Celkem",16,0);



       /* for (int i = 1; i < tableRows; i++) {
            table.setValueAt(0,i,1);
            table.setValueAt(0,i,2);
        }*/

        bonus = new boolean[2];

    }

    public void ScoreTableMouseClick(JTable target){
        if (Main.getmain().getPlayer() != 1) {

            int row = target.getSelectedRow();
            int column = target.getSelectedColumn();

            //determines if the cell has option to be filled e.g. nonempty, not already filled
            if(table.getValueAt(row,column) != null && table.getValueAt(row,column) != "" &&
                    column > 0 && column != 0 && row != 0 && row != 16 && (row <= 6 ? !filledCells[column - 1][row - 1] : !filledCells[column - 1][row -3]))

                FillCombination(row, (Integer)table.getValueAt(row,column),column - 1);
        }
    }


    public void ShowPossible(ArrayList<Integer> combinations, int player){

        for (int i = 0; i < 6; i++) { //first 6 combinations
            if(!filledCells[player][i]) {
                table.setValueAt(combinations.get(i),i+1, player+1);
            }
        }

        for (int i = 6; i < 13; i++) {
            if(!filledCells[player][i]) {
                table.setValueAt(combinations.get(i),i+3, player+1); //i+3 it goes over Sum and Bonus rows
            }
        }
    }

    public void FillCombination(int index, int val, int player) {
        if(Main.getmain().getPlayer() == player) {

            if (index <= 6 && index > 0) {

                if (!filledCells[player][index-1])
                    table.setValueAt(val, index, player + 1);
                else return;

                filledCells[player][index-1] = true;
            }
            if(index >= 9 && index <= 15) {
                if (!filledCells[player][index-3])
                    table.setValueAt(val, index, player + 1);
                else return;

                filledCells[player][index-3] = true;
            }

            int j;
            for (int i = 0; i < 13; i++) {
                    j = i + 1;
                if (i >= 6)
                    j = i + 3;
                if (!filledCells[player][i])
                    table.setValueAt("", j, player + 1);
            }

            if (index <= 6 && index > 0)
                UpdateSum(val, true, player + 1);
            else
                UpdateSum(val, false, player + 1);

            Main.getmain().setTurnEnd(true);

        }
    }

    public void UpdateSum(int val, boolean upperTable, int player) {

        if(upperTable) {
            int currVal = table.getValueAt(7,player) != null ? (Integer)table.getValueAt(7,player) : 0;
            table.setValueAt(currVal + val, 7, player);
        }

        //bonus check
        if(table.getValueAt(7,player) != null && (Integer)table.getValueAt(7,player) >= 63 && !bonus[player - 1] ) {
            bonus[player - 1] = true;
            table.setValueAt(35, 8, player);
            UpdateSum(35, false, player);
        }

        int currVal = table.getValueAt(tableRows - 1,player) != null ? (Integer)table.getValueAt(tableRows - 1,player) : 0; //sum is in the last row
        table.setValueAt(currVal + val, tableRows - 1, player);

    }

    ArrayList<Integer> diceVals;
    public ArrayList<Integer> FindPossibleCombinations(ArrayList<Die> allDice) {

        diceVals = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            diceVals.add(allDice.get(i).getVal());

        return new ArrayList<Integer>(Arrays.asList(
                CombinationsProcedures.SameNumber(diceVals,1),
                CombinationsProcedures.SameNumber(diceVals,2),
                CombinationsProcedures.SameNumber(diceVals,3),
                CombinationsProcedures.SameNumber(diceVals,4),
                CombinationsProcedures.SameNumber(diceVals,5),
                CombinationsProcedures.SameNumber(diceVals,6),
                CombinationsProcedures.OfKind(diceVals,3),
                CombinationsProcedures.OfKind(diceVals,4),
                CombinationsProcedures.FullHouse(diceVals),
                CombinationsProcedures.Straight(diceVals,4),
                CombinationsProcedures.Straight(diceVals,5),
                CombinationsProcedures.OfKind(diceVals,5),
                CombinationsProcedures.Sum(diceVals)
        ));
    }
}
