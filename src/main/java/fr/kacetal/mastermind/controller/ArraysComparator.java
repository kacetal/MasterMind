package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import fr.kacetal.mastermind.view.GamePlayDialog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Main class for the game functions
 * <p>
 * Class contain the method for comparing the arrays
 *
 * @author Artem
 * @see MastermindComparator
 * @see RechercheComparator
 */
public class ArraysComparator {

    public static final Logger LOGGER = LogManager.getLogger(ArraysComparator.class.getName());

    protected final Game game;

    protected final GamePlayDialog gamePlayDialog;

    protected SecretBlock secretBlock;

    protected int[] secretArray;

    protected SecretBlock responseBlock;

    protected int[] responseArray;

    protected int[] arrDiffAI;

    protected int nbrOfTry;

    protected String hint;

    ArraysComparator(Game game) {
        this.game = game;
        gamePlayDialog = new GamePlayDialog(game);
    }

    /**
     * Method for doing pause in the game.
     *
     * @param milliSecPause the length of time to sleep in milliseconds
     * @see Thread#sleep(long)
     */
    protected static void pause(int milliSecPause) {
        try {
            Thread.sleep(milliSecPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method eliminates all the {@link SecretBlock} from the List<p>
     * which doesn't correspond to the masque
     *
     * @param listOfAllValues
     * @param responseArray
     * @param arrDiffAIMasque
     */
    protected void listEliminator(final List<SecretBlock> listOfAllValues, final int[] responseArray, final int[] arrDiffAIMasque) {

        LOGGER.debug("Executing method listEliminator(final List<SecretBlock> listOfAllValues, final int[] responseArray, final int[] arrDiffAIMasque)");
        LOGGER.debug("Size of the listOfAllValues before listEliminator is " + listOfAllValues.size());
        LOGGER.debug("responseArray = " + Arrays.toString(responseArray));
        LOGGER.debug("arrDiffAIMasque = " + Arrays.toString(arrDiffAIMasque));
        int[] arrToCompare;

        SecretBlock responseBlock = new SecretBlock(responseArray);

        for (int i = 0; i < listOfAllValues.size(); i++) {
            //Remove the same block from the List
            if (listOfAllValues.get(i).equals(responseBlock)) {
                listOfAllValues.remove(i--);
                continue;
            }

            //Compare each Block from list with response for getting the new Masque
            arrToCompare = arrCompare(listOfAllValues.get(i).getArrOfNbr(), responseArray);

            //Remove all the blocks which have the different masque
            if (!Arrays.equals(arrToCompare, arrDiffAIMasque)) {
                listOfAllValues.remove(i--);
            }
        }

        LOGGER.debug("Size of the listOfAllValues after listEliminator is " + listOfAllValues.size());
    }

    /**
     * Method compares two arrays and<p>
     * returns an array which contains only {@code -1, 0, 1}
     * @param qst secret array
     * @param rep response array
     * @return an array contains only {@code -1, 0, 1}
     * @see Integer#compare(int, int)
     */
    protected int[] arrCompare(final int[] qst, final int[] rep) {
        return IntStream
                .range(0, qst.length)
                .sequential()
                .map(i -> Integer.compare(qst[i], rep[i]))
                .toArray();
    }

    /**
     * Parses the int[] argument as a String
     *
     * @param arrToParse an {@code array} containing the {@code int} to parse
     * @return the String value parsed from the array
     */
    protected String parseStringFromArray(final int[] arrToParse) {
        return null;
    }

    /**
     * @return SecretBlock from System.in
     */
    protected SecretBlock getPlayerResponse() {
        return gamePlayDialog.getSecretBlockResponse();
    }

    /**
     * @param limMax the figure maximal in the SecretBlock
     * @return SecretBlock from System.in
     */
    protected SecretBlock getPlayerResponse(final int limMax) {
        return gamePlayDialog.getSecretBlockResponse(limMax);
    }

    /**
     * Method to override in each functions
     */
    public void play() {
    }
}