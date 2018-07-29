package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

/**
 * Class with the methods for the game Mastermind
 * @author Artem
 * @see ArraysComparator
 */
public class MastermindComparator extends ArraysComparator {

    public static final Logger LOGGER = LogManager.getLogger(MastermindComparator.class.getName());

    protected final int[] responseToWin;

    protected List<SecretBlock> listOfAllValues;

    public MastermindComparator(Game game) {
        super(game);
        responseToWin = new int[]{game.getSecretBlockLength(), 0};
        LOGGER.info("Response to win is " + Arrays.toString(this.responseToWin));
    }

    /**
     * The method creates the list of all the possible values
     *
     * @param lengthOfNumber
     * @param figureMaximal
     * @return
     */
    protected List<SecretBlock> getListOfAllValues(final int lengthOfNumber, final int figureMaximal) {

        List<SecretBlock> listOfValues = new LinkedList<>();

        int numberOfValues = (int) pow(figureMaximal, lengthOfNumber);

        int[] arrBuffer = new int[lengthOfNumber];

        for (int i = 0, t; i < numberOfValues; i++) {

            t = i;

            //Creation an array contains the figures [0, figureMaximal)
            for (int j = lengthOfNumber - 1; j >= 0; j--) {
                arrBuffer[j] = t % figureMaximal;
                t /= figureMaximal;
            }

            listOfValues.add(new SecretBlock(arrBuffer));
        }

        LOGGER.debug("Size listOfAllValues is " + listOfValues.size());

        return listOfValues;
    }

    /**
     * Method compares two arrays and<p>
     * returns an array which contains two int {goodPlace, present}
     *
     * @param secretArray
     * @param responseArray
     * @return an array contains two int {goodPlace, present}
     * @see {@link ArraysComparator#arrCompare(int[], int[])}
     */
    @Override
    protected int[] arrCompare(final int[] secretArray, final int[] responseArray) {

        List<Integer> secretList = Arrays.stream(secretArray).boxed().collect(Collectors.toList());
        List<Integer> responseList = Arrays.stream(responseArray).boxed().collect(Collectors.toList());

        int goodPlace = 0;
        int present = 0;

        //Eliminates all the same figures are at the same positions
        for (int i = 0; i < secretList.size(); ) {
            if (secretList.get(i).equals(responseList.get(i))) {

                goodPlace++;

                secretList.remove(i);
                responseList.remove(i);
                i = 0;

                continue;
            }
            i++;
        }

        //Eliminates all the same figures aren't the same positions
        out:
        for (int i = 0; i < secretList.size(); ) {
            for (int j = 0; j < responseList.size(); j++) {
                if (secretList.get(i).equals(responseList.get(j))) {

                    secretList.remove(i);
                    responseList.remove(j);
                    i = 0;

                    present++;

                    continue out;
                }
            }
            i++;
        }

        return new int[]{goodPlace, present};
    }

    /**
     * Method parses an Array as the String {@code "x bien placés, y présents."}
     *
     * @param arrToParse an {@code array} containing the {@code int} to parse
     * @return the String parsed from an array
     */
    @Override
    protected String parseStringFromArray(final int[] arrToParse) {
        return String.format("%d bien placés, %d présents.%n", arrToParse[0], arrToParse[1]);
    }

    /**
     * Method compare an array with {@link MastermindComparator#responseToWin}
     *
     * @param arrayToCompare an array to compare with {@link MastermindComparator#responseToWin}
     * @return true if an array equals to {@link MastermindComparator#responseToWin}
     */
    protected boolean isWinner(final int[] arrayToCompare) {
        return Arrays.equals(arrayToCompare, responseToWin);
    }
}