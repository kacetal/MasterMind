package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author artem
 */
public class RechercheComparator extends ArraysComparator {

    protected final String responseToWin;

    protected int[] minAILimit;

    protected int[] maxAILimit;

    public RechercheComparator(Game game) {
        super(game);

        responseToWin = Stream.generate(() -> "=")
                .limit(game.getSecretBlockLongeur())
                .collect(Collectors.joining());

    }

    @Override
    public String intArrToStrNormalizer(final int[] intArr) {
        return Arrays.stream(intArr)
                .sequential()
                .mapToObj(i -> i == 0 ? "=" : (i > 0 ? "+" : "-") )
                .collect(Collectors.joining());
    }

    protected boolean isWinner(String astuce) {
        return astuce.equals(responseToWin);
    }
}