package fr.kacetal.mastermind;

import fr.kacetal.mastermind.controller.ArraysComparator;
import fr.kacetal.mastermind.controller.functions.*;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;
import fr.kacetal.mastermind.view.GameInitDialog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    private final GameInitDialog dialog = new GameInitDialog();
    private final String configFileName = "config.properties";
    private final String configDirectoryName = "src/main/resources";
    private Map<GameType, Map<GameMode, ? extends ArraysComparator>> typeBehaviour;

    private Game game;
    private Path configPath = Paths.get(configDirectoryName, configFileName);


    private void pathResourcesConfig() {
        LOGGER.debug("Entering in the method \"pathResourcesConfig()\"");

        if (!configPath.toFile().exists()) {
            LOGGER.warn("File config.properties not found in " + configPath.toString());
            try (InputStream resInStream = getClass().getClassLoader().getResourceAsStream(configFileName)) {
                configPath = Paths.get(configFileName);
                Files.copy(resInStream, configPath);
                LOGGER.info("Creation config.properties in " + configPath.toString());
            } catch (FileAlreadyExistsException e) {
                LOGGER.info("config.properties already exist in " + configPath.toString());
            } catch (IOException e) {
                LOGGER.error("IOException " + e.getMessage());
            }
        } else {
            LOGGER.info("File config.properties found in " + configPath.toString());
        }
    }

    public static void main(String[] args) {
        LOGGER.debug("Start the application");
        new Main().play();
    }

    private void functionsInitializer() {

        LOGGER.debug("Entering in the method \"functionsInitializer()\"");

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
        LOGGER.debug("All behaviours are " + typeBehaviour.toString());
    }

    private Game gameInitializateur() {

        LOGGER.debug("Entering in the method \"gameInitializateur()\"");

        pathResourcesConfig();

        LOGGER.debug("Creation Class GameBuilder");
        Game.GameBuilder builder = new Game.GameBuilder();

        LOGGER.debug("Return new game built by GameBuilder");
        return builder.setDevMode(dialog.isDeveloppeur())
                      .setGameType(dialog.getGameType())
                      .setGameMode(dialog.getGameMode())
                      .setSecretBlockLength(configPath)
                      .setTryNumber(configPath)
                      .setNmbrUtilisable(configPath)
                      .buildGame();
    }

    private void play() {

        LOGGER.info("Entering in the method \"play()\" ");

        mode:
        while (true) {

            game = gameInitializateur();

            functionsInitializer();

            jeu:
            while (true) {

                LOGGER.info("Start the Game");
                LOGGER.debug("Function for game is " + (typeBehaviour.get(game.getGameType()).get(game.getGameMode())).getClass().getName());
                typeBehaviour.get(game.getGameType()).get(game.getGameMode()).play();

                switch (dialog.getReplayMode()) {
                    case 1:
                        LOGGER.info("Choice the last game");
                        continue jeu;
                    case 2:
                        LOGGER.info("Choice the new game");
                        continue mode;
                    case 3:
                        LOGGER.info("Exit the game");
                        System.exit(0);
                }
            }
        }
    }
}