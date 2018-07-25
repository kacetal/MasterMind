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
import java.util.HashMap;
import java.util.Map;


/**
 * This is the main class in the package fr.kacetal.mastermind.
 * <p>
 * Point of the entering in tne programme
 *
 * @author Artem
 * @version 1.0
 */
public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    private final GameInitDialog dialog = new GameInitDialog();
    private final String configFileName = "config.properties";
    private final String configDirectoryName = "src/main/resources";
    private Map<GameType, Map<GameMode, ? extends ArraysComparator>> typeBehaviour;

    private Game game;
    private Path configPath = Paths.get(configDirectoryName, configFileName);


    /**
     * Method for verification the path to <i>config.properties</i>
     * <p>
     * If the application not found the <i>config.properties</i> in the directory
     * <blockqoute><pre>
     *     src/main/resources/config.properties
     * </pre></blockqoute>
     *
     * It copes <i>config.properties</i> from mastermind.jar file near to the mastermind.jar
     *
     */
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

    /**
     * Method inputs all the functions in the
     * <blockqoute><pre>
     *     {@code Map<GameType, Map<GameMode, ? extends ArraysComparator>> typeBehaviour}
     * </pre></blockqoute>
     *
     * All the functions extend the class {@link ArraysComparator}.
     */
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

    /**
     * Method for create the new instance of {@link Game} by {@link Game.GameBuilder}
     *
     * @return {@link Game}
     */
    private Game gameInitializer() {

        LOGGER.debug("Entering in the method \"gameInitializer()\"");

        pathResourcesConfig();

        LOGGER.debug("Creation Class GameBuilder");
        Game.GameBuilder builder = new Game.GameBuilder();

        LOGGER.debug("Return new game built by GameBuilder");
        return builder.setDevMode(dialog.isDeveloper())
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

            game = gameInitializer();

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