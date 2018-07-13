package fr.kacetal.mastermind.view;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GamePlayDialog {
    private final Game game;

    public GamePlayDialog(final Game game) {
        this.game = game;
    }

    public SecretBlock getSecretBlockResponse() {

        int[] intArrFromInput;

        try {
            intArrFromInput = new BufferedReader(new InputStreamReader(System.in))
                    .readLine()
                    .chars()
                    .filter(Character::isDigit)
                    .map(Character::getNumericValue)
                    .limit(game.getSecretBlockLongeur())
                    .toArray();
        } catch (IOException e) {
            e.printStackTrace();
            intArrFromInput = new Random().ints(game.getSecretBlockLongeur(), 0, 10).toArray();
        }

        if (intArrFromInput.length < game.getSecretBlockLongeur()) {
            intArrFromInput = arrayExtender(intArrFromInput, game.getSecretBlockLongeur());
        }

        return new SecretBlock(intArrFromInput);
    }

    public SecretBlock getSecretBlockResponse(final int limMax) {

        int[] intArrFromInput;

        try {
            intArrFromInput = new BufferedReader(new InputStreamReader(System.in))
                    .readLine()
                    .chars()
                    .filter(Character::isDigit)
                    .map(Character::getNumericValue)
                    .filter(x -> x < limMax)
                    .limit(game.getSecretBlockLongeur())
                    .toArray();
        } catch (IOException e) {
            e.printStackTrace();
            intArrFromInput = new Random().ints(game.getSecretBlockLongeur(), 0, 10).toArray();
        }

        if (intArrFromInput.length < game.getSecretBlockLongeur()) {
            intArrFromInput = arrayExtender(intArrFromInput, game.getSecretBlockLongeur());
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

    private int[] arrayExtender(final int[] arrToExt, final int limit) {

        int[] newArr = new int[limit];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = 0;
        }

        System.arraycopy(arrToExt, 0, newArr, 0, arrToExt.length);

        return newArr;
    }
}