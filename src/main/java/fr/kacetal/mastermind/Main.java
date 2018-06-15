package fr.kacetal.mastermind;

import fr.kacetal.mastermind.controller.RechercheComparator;
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
        SecretBlock secretBlock = new SecretBlock(game);
        GamePlayDialog gamePlayDialog = new GamePlayDialog(game);
        String response;
        String winnerResponse = "";
        for (int i = 0; i < game.getSecretBlockLongeur(); i++) {
            winnerResponse += "=";
        }
        int[] qst, rsp;
        System.out.println("Devinez nombre qui contien " + game.getSecretBlockLongeur() + " chiffres");
        do {
            qst = secretBlock.getArrOfNbr();
            rsp = gamePlayDialog.getSecretBlockResponse().getArrOfNbr();
            Arrays.stream(rsp).forEach(System.out::print);
            System.out.println();
            if (game.isDevMode()) {
                Arrays.stream(qst).forEachOrdered(System.out::print);
                System.out.println();
            }
            rsp = RechercheComparator.arrCompare(qst, rsp);
            response = RechercheComparator.intToStrRecherche(rsp);
            System.out.println(response);
        } while (!response.equals(winnerResponse));
    }
}