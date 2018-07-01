package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import fr.kacetal.mastermind.view.GamePlayDialog;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author artem
 */
public class RechercheComparator extends ArraysComparator {

    protected final Game game;

    protected final GamePlayDialog gamePlayDialog;

    protected final String responseToWin;

    protected SecretBlock secretBlock;

    protected int[] secretArray;

    protected SecretBlock responseBlock;

    protected int[] responseArray;

    protected int[] minAILimit;

    protected int[] maxAILimit;

    protected int[] arrDiffAI;

    protected int nbrOfTry;

    protected String astuce;

    public RechercheComparator(Game game) {
        this.game = game;

        gamePlayDialog = new GamePlayDialog(game);

        responseToWin = Stream.generate(() -> "=")
                .limit(game.getSecretBlockLongeur())
                .collect(Collectors.joining());
        nbrOfTry = game.getTryNumber();
    }

    protected SecretBlock getPlayerResponse() {
        return gamePlayDialog.getSecretBlockResponse();
    }

    @Override
    public String intToStrRechercheNormalizer(final int[] intArr) {
        return Arrays.stream(intArr)
                .sequential()
                .mapToObj(i -> i == 0 ? "=" : (i > 0 ? "+" : "-") )
                .collect(Collectors.joining());
    }

    protected boolean isWinner(String astuce) {
        return astuce.equals(responseToWin);
    }
}