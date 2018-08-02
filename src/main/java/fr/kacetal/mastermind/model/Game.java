package fr.kacetal.mastermind.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Class contains all the parameters of the game
 *
 * @author Artem
 */
public class Game {

    public static final Logger LOGGER = LogManager.getLogger(Game.class.getName());

    private final boolean isDevMode;

    private final int tryNumber;

    private final int secretBlockLength;

    private final int nmbrUtilisable;

    private final GameType gameType;

    private final GameMode gameMode;

    private Game(final Game.GameBuilder builder) {
        this.isDevMode = builder.isDevMode;
        this.tryNumber = builder.tryNumber;
        this.secretBlockLength = builder.secretBlockLength;
        this.nmbrUtilisable = builder.nmbrUtilisable;
        this.gameType = builder.gameType;
        this.gameMode = builder.gameMode;
    }

    /**
     * Getter for developer mode.
     * @return the isDevMode
     */
    public boolean isDevMode() {
        return isDevMode;
    }

    /**
     * Getter for the numbers of try for the Game
     * @return the tryNumber
     */
    public int getTryNumber() {
        return tryNumber;
    }

    /**
     * Getter for the length maximal for the secret block
     * @return the secretBlockLength
     */
    public int getSecretBlockLength() {
        return secretBlockLength;
    }

    /**
     * Getter for figures maximal
     *
     * @return the nmbrUtilisable
     */
    public int getNmbrUtilisable() {
        return nmbrUtilisable;
    }

    /**
     * Getter for the type of the game
     * @return the gameType
     */
    public GameType getGameType() {
        return gameType;
    }

    /**
     * Getter for the mode of the game
     * @return the gameMode
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gameMode == null) ? 0 : gameMode.hashCode());
        result = prime * result + ((gameType == null) ? 0 : gameType.hashCode());
        result = prime * result + (isDevMode ? 1231 : 1237);
        result = prime * result + secretBlockLength;
        result = prime * result + tryNumber;
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Game)) {
            return false;
        }
        Game other = (Game) obj;
        if (gameMode != other.gameMode) {
            return false;
        }
        if (gameType != other.gameType) {
            return false;
        }
        if (isDevMode != other.isDevMode) {
            return false;
        }
        if (secretBlockLength != other.secretBlockLength) {
            return false;
        }
        return tryNumber == other.tryNumber;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Game [isDevMode=" + isDevMode + ", tryNumber=" + tryNumber + ", secretBlockLength="
                + secretBlockLength + ", gameType=" + gameType + ", gameMode=" + gameMode + "]";
    }

    /**
     * Class builds the {@link Game}
     */
    public static class GameBuilder {

        private boolean isDevMode;

        private int tryNumber;

        private int secretBlockLength;

        private int nmbrUtilisable;

        private GameType gameType;

        private GameMode gameMode;

        /**
         * @param isDevMode the developer mode to set
         * @return GameBuilder
         */
        public GameBuilder setDevMode(boolean isDevMode) {
            this.isDevMode = isDevMode;
            Game.LOGGER.info("Developer mode is selected: " + isDevMode);
            return this;
        }

        /**
         * It can't be less then 1.
         *
         * @param propertiesPath the number of the try to set
         * @return GameBuilder
         */
        public GameBuilder setTryNumber(final Path propertiesPath) {
            try (InputStream input = new FileInputStream(propertiesPath.toFile())){
                Properties properties = new Properties();
                properties.load(input);
                this.tryNumber = new Integer(properties.getProperty("tryNumber"));
                Game.LOGGER.info("Number of try from file is " + tryNumber);
                if (this.tryNumber < 1) {
                    this.tryNumber = 1;
                }
                Game.LOGGER.info("Number of try is " + tryNumber);
                return this;
            } catch (IOException ex) {
                Game.LOGGER.error("IOException " + ex.getMessage());
                this.tryNumber = 8;
                Game.LOGGER.error("File config.properties not found. tryNumber = " + tryNumber + " par default");
                return this;
            }
        }

        /**
         * It can't be less then 1.
         *
         * @param propertiesPath the path to config.properties
         * @return GameBuilder
         */
        public GameBuilder setSecretBlockLength(final Path propertiesPath) {
            try (InputStream input = new FileInputStream(propertiesPath.toFile())){
                Properties properties = new Properties();
                properties.load(input);
                this.secretBlockLength = new Integer(properties.getProperty("secretBlockLength"));
                Game.LOGGER.info("Length of SecretBlock from file is {}", secretBlockLength);
                if (secretBlockLength < 1) {
                    secretBlockLength = 1;
                }
                Game.LOGGER.info("Length of SecretBlock is {}", secretBlockLength);
                return this;
            } catch (IOException ex) {
                Game.LOGGER.error("IOException " + ex.getMessage());
                this.secretBlockLength = 4;
                Game.LOGGER.error("File config.properties not found. secretBlockLength = " + secretBlockLength + " par default");
                return this;
            }
        }

        /**
         * It can't be less than 4 and more then 10.
         *
         * @param propertiesPath the path to config.properties
         * @return GameBuilder
         */
        public GameBuilder setNmbrUtilisable(final Path propertiesPath) {
            try (InputStream input = new FileInputStream(propertiesPath.toFile())) {
                Properties properties = new Properties();
                properties.load(input);
                this.nmbrUtilisable = new Integer(properties.getProperty("nmbrUtilisable"));
                Game.LOGGER.info("Number of figures from file is {}", this.nmbrUtilisable);
                if (this.nmbrUtilisable <= 4) {
                    Game.LOGGER.warn("Numbers of figures minimum is 4. nmbrUtilisable = 4");
                    this.nmbrUtilisable = 4;
                } else if (this.nmbrUtilisable >= 10) {
                    Game.LOGGER.warn("Numbres of figures maximum is 10. nmbrUtilisable = 10");
                    this.nmbrUtilisable = 10;
                }
                Game.LOGGER.info("Number of figures in the game is [0 - {}]", (this.nmbrUtilisable - 1));
                return this;
            } catch (IOException ex) {
                Game.LOGGER.error("IOException " + ex.getMessage());
                this.nmbrUtilisable = 4;
                Game.LOGGER.error("File config.properties not found. nmbrUtilisable = " + nmbrUtilisable + " par default");
                return this;
            }
        }

        /**
         * @param gameType the gameType to set
         * @return GameBuilder
         */
        public GameBuilder setGameType(GameType gameType) {
            this.gameType = gameType;
            Game.LOGGER.info("Game type is " + gameType.getName());
            return this;
        }

        /**
         * @param gameMode the gameMode to set
         * @return GameBuilder
         */
        public GameBuilder setGameMode(GameMode gameMode) {
            this.gameMode = gameMode;
            Game.LOGGER.info("Game mode is " + gameMode.getName());
            return this;
        }

        /**
         * Method for building the {@link Game}
         * @return Game
         */
        public Game buildGame() {
            Game.LOGGER.info(new Game(this).toString());
            return new Game(this);
        }
    }
}