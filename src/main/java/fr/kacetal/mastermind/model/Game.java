package fr.kacetal.mastermind.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author artem
 *
 */
public class Game {

    public static final Logger LOGGER = LogManager.getLogger(Game.class.getName());

    private final boolean isDevMode;

    private final int tryNumber;

    private final int secretBlockLongeur;

    private final int nmbrUtilisable;

    private final GameType gameType;

    private final GameMode gameMode;

    private Game(final Game.GameBuilder builder) {
        this.isDevMode = builder.isDevMode;
        this.tryNumber = builder.tryNumber;
        this.secretBlockLongeur = builder.secretBlockLength;
        this.nmbrUtilisable = builder.nmbrUtilisable;
        this.gameType = builder.gameType;
        this.gameMode = builder.gameMode;
    }

    /**
     * @return the isDevMode
     */
    public boolean isDevMode() {
        return isDevMode;
    }

    /**
     * @return the tryNumber
     */
    public int getTryNumber() {
        return tryNumber;
    }

    /**
     * @return the secretBlockLength
     */
    public int getSecretBlockLongeur() {
        return secretBlockLongeur;
    }

    public int getNmbrUtilisable() {
        return nmbrUtilisable;
    }

    /**
     * @return the gameType
     */
    public GameType getGameType() {
        return gameType;
    }

    /**
     * @return the gameMode
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gameMode == null) ? 0 : gameMode.hashCode());
        result = prime * result + ((gameType == null) ? 0 : gameType.hashCode());
        result = prime * result + (isDevMode ? 1231 : 1237);
        result = prime * result + secretBlockLongeur;
        result = prime * result + tryNumber;
        return result;
    }

    /* (non-Javadoc)
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
        if (secretBlockLongeur != other.secretBlockLongeur) {
            return false;
        }
        return tryNumber == other.tryNumber;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Game [isDevMode=" + isDevMode + ", tryNumber=" + tryNumber + ", secretBlockLength="
                + secretBlockLongeur + ", gameType=" + gameType + ", gameMode=" + gameMode + "]";
    }

    public static class GameBuilder {

        private boolean isDevMode;

        private int tryNumber;

        private int secretBlockLength;

        private int nmbrUtilisable;

        private GameType gameType;

        private GameMode gameMode;

        /**
         * @param isDevMode the isDevMode to set
         */
        public GameBuilder setDevMode(boolean isDevMode) {
            this.isDevMode = isDevMode;
            Game.LOGGER.info("Developer mode is selected: " + isDevMode);
            return this;
        }

        /**
         * @param propertiesPath the tryNumber to set
         */
        public GameBuilder setTryNumber(final Path propertiesPath) {
            try (InputStream input = new FileInputStream(propertiesPath.toFile())){
                Properties properties = new Properties();
                properties.load(input);
                this.tryNumber = new Integer(properties.getProperty("tryNumber"));
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
         * @param propertiesPath the secretBlockLength to set
         */
        public GameBuilder setSecretBlockLength(final Path propertiesPath) {
            try (InputStream input = new FileInputStream(propertiesPath.toFile())){
                Properties properties = new Properties();
                properties.load(input);
                this.secretBlockLength = new Integer(properties.getProperty("secretBlockLongeur"));
                Game.LOGGER.info("Length of SecretBlock is " + secretBlockLength);
                return this;
            } catch (IOException ex) {
                Game.LOGGER.error("IOException " + ex.getMessage());
                this.secretBlockLength = 4;
                Game.LOGGER.error("File config.properties not found. secretBlockLength = " + secretBlockLength + " par default");
                return this;
            }
        }

        public GameBuilder setNmbrUtilisable(final Path propertiesPath) {
            try (InputStream input = new FileInputStream(propertiesPath.toFile())) {
                Properties properties = new Properties();
                properties.load(input);
                int nmbrFromFile = new Integer(properties.getProperty("nmbrUtilisable"));
                Game.LOGGER.info("Numbers of figures are [0 - " + nmbrFromFile + ")");
                if (nmbrFromFile < 4) {
                    Game.LOGGER.warn("Numbers of figures minimum is 4. nmbrUtilisable = 4");
                    this.nmbrUtilisable = 4;
                } else if (nmbrFromFile >= 10) {
                    Game.LOGGER.warn("Numbres of figures maximum is 10. nmbrUtilisable = 10");
                    this.nmbrUtilisable = 10;
                } else {
                    Game.LOGGER.info("Numbres of figures is " + nmbrFromFile);
                    this.nmbrUtilisable = nmbrFromFile;
                }
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
         */
        public GameBuilder setGameType(GameType gameType) {
            this.gameType = gameType;
            Game.LOGGER.info("Game type is " + gameType.getName());
            return this;
        }

        /**
         * @param gameMode the gameMode to set
         */
        public GameBuilder setGameMode(GameMode gameMode) {
            this.gameMode = gameMode;
            Game.LOGGER.info("Game mode is " + gameMode.getName());
            return this;
        }

        public Game buildGame() {
            Game.LOGGER.info(new Game(this).toString());
            return new Game(this);
        }
    }
}