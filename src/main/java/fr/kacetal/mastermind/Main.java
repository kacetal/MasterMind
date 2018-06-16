package fr.kacetal.mastermind;

import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.controller.functions.RechercheChallengerFunction;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import fr.kacetal.mastermind.view.GameInitDialog;
import fr.kacetal.mastermind.view.GamePlayDialog;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    private Game game;

    private Path configPath = Paths.get("src/main/resources", "config.properties");

    public static void main(String[] args) {
        new Main().play();
    }

    public Game gameInitializateur() {
        Game.GameBuilder builder = new Game.GameBuilder();
        GameInitDialog dialog = new GameInitDialog();


        return builder.setDevMode(dialog.isDeveloppeur())
                      .setGameType(dialog.getGameType())
                      .setGameMode(dialog.getGameMode())
                      .setSecretBlockLongeur(configPath)
                      .setTryNumber(configPath)
                      .buildGame();
    }

    public void play() {
        game = gameInitializateur();
        new RechercheChallengerFunction(game).play();
    }
}