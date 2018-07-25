package fr.kacetal.mastermind.view;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Class for asking a player during the Game<p>
 *
 * @author Artem
 * @see Game
 * @see SecretBlock
 */
public class GamePlayDialog {
    private final Game game;

    public GamePlayDialog(final Game game) {
        this.game = game;
    }

    /**
     * Method gets and filters all the chars from {@code System.in}
     * <p>and chooses only the figures [0 - 9]
     * <p>
     * <p>
     * If length of the array from {@code System.in}
     * <p>less than {@code secretBlockLength}<p>
     * It adds 0 at the end of array.<p>
     * <p>
     * If length of the array from {@code System.in}
     * <p>more than {@code secretBlockLength}<p>
     * It cuts unnecessary figures.
     *
     * @return SecretBlock
     */
    public SecretBlock getSecretBlockResponse() {

        int[] intArrFromInput;

        try {
            intArrFromInput = new BufferedReader(new InputStreamReader(System.in))
                    .readLine()
                    .chars()
                    .filter(Character::isDigit)
                    .map(Character::getNumericValue)
                    .limit(game.getSecretBlockLength())
                    .toArray();
        } catch (IOException e) {
            e.printStackTrace();
            intArrFromInput = new Random().ints(game.getSecretBlockLength(), 0, 10).toArray();
        }

        if (intArrFromInput.length < game.getSecretBlockLength()) {
            intArrFromInput = arrayExtender(intArrFromInput, game.getSecretBlockLength());
        }

        return new SecretBlock(intArrFromInput);
    }

    /**
     * Method gets and filters all the chars from {@code System.in}
     * <p>and chooses only the figures [0 - limMax)
     * <p>
     * <p>
     * If length of the array from {@code System.in}
     * <p>less than {@code secretBlockLength}<p>
     * It adds 0 at the end of array.<p>
     * <p>
     * If length of the array from {@code System.in}
     * <p>more than {@code secretBlockLength}<p>
     * It cuts unnecessary figures.
     *
     * @param limMax
     * @return SecretBlock
     */
    public SecretBlock getSecretBlockResponse(final int limMax) {

        int[] intArrFromInput;

        try {
            intArrFromInput = new BufferedReader(new InputStreamReader(System.in))
                    .readLine()
                    .chars()
                    .filter(Character::isDigit)
                    .map(Character::getNumericValue)
                    .filter(x -> x < limMax)
                    .limit(game.getSecretBlockLength())
                    .toArray();
        } catch (IOException e) {
            e.printStackTrace();
            intArrFromInput = new Random().ints(game.getSecretBlockLength(), 0, 10).toArray();
        }

        if (intArrFromInput.length < game.getSecretBlockLength()) {
            intArrFromInput = arrayExtender(intArrFromInput, game.getSecretBlockLength());
        }

        return new SecretBlock(intArrFromInput);
    }

    public String nbrOfTryDlg(final int nbrOfTry) {
        if (nbrOfTry > 1) {
            return " essais";
        } else {
            return " essai";
        }
    }

    /**
     * Method adds 0 at the end of array.
     *
     * @param arrToExt array to extend
     * @param limit    length of the array to return
     * @return one array int[] with length equals limit
     */
    private int[] arrayExtender(final int[] arrToExt, final int limit) {

        int[] newArr = new int[limit];

        System.arraycopy(arrToExt, 0, newArr, 0, arrToExt.length);

        return newArr;
    }
}