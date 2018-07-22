package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;
import fr.kacetal.mastermind.view.GameInitDialog;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;

public class ArraysComparatorTest {

    private Game.GameBuilder builder = new Game.GameBuilder();

    private GameInitDialog dialog = new GameInitDialog();

    private Game game = builder.setDevMode(true)
            .setGameType(GameType.RECHERCHE)
            .setGameMode(GameMode.CHALLENGER)
            .setSecretBlockLength(Paths.get(""))
            .setTryNumber(Paths.get(""))
            .buildGame();

    @Test
    public void arrCompareV1() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{4, 2, 7, 8};
        int[] expected = new int[]{-1, 0, -1, -1};
        int[] actual = new ArraysComparator(game).arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void arrCompareV2() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{2, 2, 1, 4};
        int[] expected = new int[]{-1, 0, 1, 0};
        int[] actual = new ArraysComparator(game).arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void arrCompareEquals() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{1, 2, 3, 4};
        int[] expected = new int[]{0, 0, 0, 0};
        int[] actual = new ArraysComparator(game).arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }
}