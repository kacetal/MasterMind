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
 * @author artem
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

    protected String astuce;

    ArraysComparator(Game game) {
        this.game = game;
        gamePlayDialog = new GamePlayDialog(game);

    }

    /**
     *
     *
     * @param listOfAllValues
     * @param responseArray
     * @param arrDiffAIMasque
     *
     *
     */
    protected void listEliminator(final List<SecretBlock> listOfAllValues, final int[] responseArray, final int[] arrDiffAIMasque) {

        LOGGER.debug("Executing method listEliminator(final List<SecretBlock> listOfAllValues, final int[] responseArray, final int[] arrDiffAIMasque)");
        LOGGER.debug("Size of the listOfAllValues before listEliminator is " + listOfAllValues.size());
        LOGGER.debug("responseArray = " + Arrays.toString(responseArray));
        LOGGER.debug("arrDiffAIMasque = " + Arrays.toString(arrDiffAIMasque));
        int[] arrToCompare;

        SecretBlock responseBlock = new SecretBlock(responseArray);

        for (int i = 0; i < listOfAllValues.size(); i++) {
            // Remove the same Block from List
            if (listOfAllValues.get(i).equals(responseBlock)) {
                listOfAllValues.remove(i--);
                continue;
            }

            //We compare each Block from list with response for get the new Masque
            arrToCompare = arrCompare(listOfAllValues.get(i).getArrOfNbr(), responseArray);

            //Remove the Block which have different masque
            if (!Arrays.equals(arrToCompare, arrDiffAIMasque)) {
                listOfAllValues.remove(i--);
            }
        }

        LOGGER.debug("Size of the listOfAllValues after listEliminator is " + listOfAllValues.size());

    }

    protected int[] arrCompare(final int[] qst, final int[] rep) {
        return IntStream
                .range(0, qst.length)
                .sequential()
                .map(i -> Integer.compare(qst[i], rep[i]))
                .toArray();
    }

    protected static void pause(int milliSecPause) {
        try {
            Thread.sleep(milliSecPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void play() {
    }

    protected String intArrToStrNormalizer(final int[] intToNormalize) {
        return null;
    }

    protected SecretBlock getPlayerResponse() {
        return gamePlayDialog.getSecretBlockResponse();
    }

    protected SecretBlock getPlayerResponse(final int limMax) {
        return gamePlayDialog.getSecretBlockResponse(limMax);
    }
}