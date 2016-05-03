package yahtzee;

import java.util.*;

/**
 * A static class which provides functions to
 * resolve score or availability of the poker-like combinations
 *
 * All the functions are awaiting non-null parameters and will
 * throw uncaught exceptions if not used properly
 *
 * @author Marek Zidek
 */
public class CombinationsProcedures {

    static int score;

    /**
     * Counts score for combinations looking for the same numbers
     *
     * @param allDice  a list of all "values" on the dice
     * @param number a number to look for
     * @return score to be showed in the scoretable
     */

    public static int SameNumber(ArrayList<Integer> allDice, int number) {
        score = 0;
        for (int i = 0; i < allDice.size(); i++) {
            if (allDice.get(i) == number) {
                score += number;
            }
        }
        return score;
    }

    /**
     * Finds 4-ofKind and 3-ofKind and Yahtzee
     *
     * @param allDice  a list of all "values" on the dice
     * @param number 3, 4 or 5 and it's a number of same dice which has to be there
     * @return score to be showed in the scoretable
     */

    public static int OfKind(ArrayList<Integer> allDice, int number) {
        score = 0;

        for (int i = 6; i > 0; i--) {
            int count = 0;
            for (int j = 0; j < allDice.size(); j++) {
                if (allDice.get(j) == i) {
                    count ++;
                }
            }
            if (count >= number) {
                score = Sum(allDice);
                if(number == 5) //hit yahtzee
                    score = 50;
            }
        }
        return score;
    }



    private static Set<Integer> unique;

    /**
     * FullHouse
     * @param allDice  a list of all "values" on the dice
     * @return score to be showed in the scoretable
     */
    public static int FullHouse(ArrayList<Integer> allDice) {
        boolean three = false;
        boolean two = false;
        unique = new HashSet<>(allDice);
        for (Integer key : unique){
            if (Collections.frequency(allDice,key) == 3)
                three = true;
            if (Collections.frequency(allDice,key) == 2)
                two = true;
        }
        if (three && two)
            return 25;
        return 0;
    }


    /**
     * Straight
     * @param allDice  a list of all "values" on the dice
     * @param number the length of the straight to be found
     * @return score to be showed in the scoretable
     */
    public static int Straight(ArrayList<Integer> allDice, int number) {
        score =0;
        int count = 0;
        for (int i = 1; i < 7; i++) {
            if (allDice.contains(i))
                count ++;
            else count = 0;
            if (count == number)
                score = number*10-10;
        }
        return score;
    }

    /**
     * Sums up all the values
     * @param allDice  a list of all "values" on the dice
     * @return score to be showed in the scoretable
     */
    public static int Sum(ArrayList<Integer> allDice) {
        score = 0;
        for (int i = 0; i < allDice.size(); i++)
            score += allDice.get(i);
        return score;
    }
}
