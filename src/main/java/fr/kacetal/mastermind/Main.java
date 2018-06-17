package fr.kacetal.mastermind;

import fr.kacetal.mastermind.controller.functions.RechercheDefenseFunction;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.view.GameInitDialog;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private Game game;

    private Path configPath = Paths.get("src/main/resources", "config.properties");

    public static void main(String[] args) {
        new Main().play();
    }

    private Game gameInitializateur() {
        Game.GameBuilder builder = new Game.GameBuilder();
        GameInitDialog dialog = new GameInitDialog();


        return builder.setDevMode(dialog.isDeveloppeur())
                      .setGameType(dialog.getGameType())
                      .setGameMode(dialog.getGameMode())
                      .setSecretBlockLongeur(configPath)
                      .setTryNumber(configPath)
                      .buildGame();
    }

    private void play() {
        game = gameInitializateur();
        new RechercheDefenseFunction(game).play();
    }
}