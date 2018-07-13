package fr.kacetal.mastermind;

import fr.kacetal.mastermind.controller.ArraysComparator;
import fr.kacetal.mastermind.controller.functions.*;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;
import fr.kacetal.mastermind.view.GameInitDialog;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private final GameInitDialog dialog = new GameInitDialog();
    private final String configFileName = "config.properties";
    private final String configDirectoryName = "src/main/resources";
    private Map<GameType, Map<GameMode, ? extends ArraysComparator>> typeBehaviour;

    private Game game;
    private Path configPath = Paths.get(configDirectoryName, configFileName);


    private void pathResourcesConfig() {
        if (!configPath.toFile().exists()) {
            try (InputStream resInStream = getClass().getClassLoader().getResourceAsStream(configFileName)) {
                configPath = Paths.get(configFileName);
                Files.copy(resInStream, configPath);
            } catch (FileAlreadyExistsException e) {
                System.out.println("config.properties already existé");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Main().play();
    }

    private void finctionsInitializateur() {

        Map<GameMode, ArraysComparator> modeRechercheBehaviour = new HashMap<>();
        Map<GameMode, ArraysComparator> modeMastermindBehaviour = new HashMap<>();

        typeBehaviour = new HashMap<>();

        modeRechercheBehaviour.put(GameMode.CHALLENGER, new RechercheChallengerFunction(game));
        modeRechercheBehaviour.put(GameMode.DEFENSEUR, new RechercheDefenseFunction(game));
        modeRechercheBehaviour.put(GameMode.DUEL, new RechercheDuelFunction(game));

        modeMastermindBehaviour.put(GameMode.CHALLENGER, new MastermindChallengerFunction(game));
        modeMastermindBehaviour.put(GameMode.DEFENSEUR, new MastermindDefenseFunction(game));
        modeMastermindBehaviour.put(GameMode.DUEL, new MastermindDuelFunction(game));

        typeBehaviour.put(GameType.RECHERCHE, modeRechercheBehaviour);
        typeBehaviour.put(GameType.MASTERMIND, modeMastermindBehaviour);
    }

    private Game gameInitializateur() {

        pathResourcesConfig();
        Game.GameBuilder builder = new Game.GameBuilder();

        return builder.setDevMode(dialog.isDeveloppeur())
                      .setGameType(dialog.getGameType())
                      .setGameMode(dialog.getGameMode())
                      .setSecretBlockLongeur(configPath)
                      .setTryNumber(configPath)
                .setnmbrUtilisable(configPath)
                      .buildGame();
    }

    private void play() {

        mode:
        while (true) {

            game = gameInitializateur();

            finctionsInitializateur();

            jeu:
            while (true) {

                typeBehaviour.get(game.getGameType()).get(game.getGameMode()).play();

                switch (dialog.getReplayMode()) {
                    case 1:
                        continue jeu;
                    case 2:
                        continue mode;
                    case 3:
                        System.exit(0);
                }
            }
        }
    }
}