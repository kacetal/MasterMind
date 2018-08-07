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
     * The method checks the path to <i>config.properties</i>.
     * <p>
     * If the application does not find the <i>config.properties</i>
     * in the directory {@code src/main/resources/config.properties},
     * it will copy <i>config.properties</i> from the mastermind.jar file
     * and put it next to the mastermind.jar.
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

    public static void main(String... args) {
        LOGGER.debug("Start the application");
        new Main().play(args);
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
     * The method creates a new instance of {@link Game} by {@link Game.GameBuilder}
     *
     * @return {@link Game}
     */
    private Game gameInitializer(String... args) {

        LOGGER.debug("Entering in the method \"gameInitializer()\"");

        pathResourcesConfig();

        LOGGER.debug("Creation Class GameBuilder");
        Game.GameBuilder builder = new Game.GameBuilder();

        if (args.length > 0 && args[0].equals("-dev")) {
            builder.setDevMode(true);
            System.out.println("Vous jouez en mode développeur");
        } else {
            builder.setDevMode(configPath);
        }

        LOGGER.debug("Create new game built by GameBuilder");
        builder.setGameType(dialog.getGameType())
                .setGameMode(dialog.getGameMode())
                .setSecretBlockLength(configPath)
                .setTryNumber(configPath)
                .setNmbrUtilisable(configPath);


        return builder.buildGame();
    }

    private void play(String... args) {

        LOGGER.info("Entering in the method \"play()\" ");

        mode:
        while (true) {

            game = gameInitializer(args);

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