package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;
import fr.kacetal.mastermind.view.GameInitDialog;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class RechercheComparatorTest {

    private Game.GameBuilder builder = new Game.GameBuilder();

    private GameInitDialog dialog = new GameInitDialog();

    private Game game = builder.setDevMode(true)
            .setGameType(GameType.RECHERCHE)
            .setGameMode(GameMode.CHALLENGER)
            .setSecretBlockLength(Paths.get(""))
            .setTryNumber(Paths.get(""))
            .buildGame();

    @Test
    public void intToStrRechercheV1() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{4, 2, 7, 8};
        String expected = "-=--";
        int[] intActual = new ArraysComparator(game).arrCompare(question, response);
        String actual = new RechercheComparator(game).parseStringFromArray(intActual);
        assertEquals(expected, actual);
    }

    @Test
    public void intToStrRechercheV2() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{2, 2, 1, 4};
        String expected = "-=+=";
        int[] intActual = new ArraysComparator(game).arrCompare(question, response);
        String actual = new RechercheComparator(game).parseStringFromArray(intActual);
        assertEquals(expected, actual);
    }

    @Test
    public void arrCompareEquals() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{1, 2, 3, 4};
        String expected = "====";
        int[] intActual = new ArraysComparator(game).arrCompare(question, response);
        String actual = new RechercheComparator(game).parseStringFromArray(intActual);
        assertEquals(expected, actual);
    }
}