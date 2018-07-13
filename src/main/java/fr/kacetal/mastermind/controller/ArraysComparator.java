package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import fr.kacetal.mastermind.view.GamePlayDialog;

import java.util.stream.IntStream;

/**
 * @author artem
 */
public class ArraysComparator {

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