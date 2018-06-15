/**
 *
 */
package fr.kacetal.mastermind.model;

import fr.kacetal.mastermind.controller.ArraysComparator;
import fr.kacetal.mastermind.controller.RechercheComparator;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author artem
 *
 */
public class Game {

    //Mode développeur où la solution est affichée dès le début
    private final boolean isDevMode;

    private final int tryNumber;

    private final int secretBlockLongeur;

    private final GameType gameType;

    private final GameMode gameMode;

    private final Map<Boolean, Map<GameType, Map<GameMode, ArraysComparator>>> generalBehavior;

    /**
     * @param isDevMode
     * @param tryNumber
     * @param secretBlockLongeur
     * @param gameType
     * @param gameMode
     * @param gameBehaviors
     */
    private Game(final GameBuilder builder) {
        this.isDevMode = builder.isDevMode;
        this.tryNumber = builder.tryNumber;
        this.secretBlockLongeur = builder.secretBlockLongeur;
        this.gameType = builder.gameType;
        this.gameMode = builder.gameMode;

        Map<GameMode, ArraysComparator> modeBehaviour = new HashMap<>();
        modeBehaviour.put(gameMode, new RechercheComparator());

        Map<GameType, Map<GameMode, ArraysComparator>> typeBehaviour = new HashMap<>();
        typeBehaviour.put(gameType, modeBehaviour);

        generalBehavior = new HashMap<>();
        generalBehavior.put(isDevMode, typeBehaviour);
        RechercheComparator rc = (RechercheComparator) generalBehavior.get(isDevMode).get(gameType).get(gameMode);
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
     * @return the secretBlockLongeur
     */
    public int getSecretBlockLongeur() {
        return secretBlockLongeur;
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

    /* (non-Javadoc)
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
        if (tryNumber != other.tryNumber) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Game [isDevMode=" + isDevMode + ", tryNumber=" + tryNumber + ", secretBlockLongeur="
                + secretBlockLongeur + ", gameType=" + gameType + ", gameMode=" + gameMode + "]";
    }

    public static class GameBuilder {

        private boolean isDevMode;

        private int tryNumber;

        private int secretBlockLongeur;

        private GameType gameType;

        private GameMode gameMode;

        private Properties gameProperties;


        /**
         * @param isDevMode the isDevMode to set
         */
        public GameBuilder setDevMode(boolean isDevMode) {
            this.isDevMode = isDevMode;
            return this;
        }

        /**
         * @param tryNumber the tryNumber to set
         */
        public GameBuilder setTryNumber(final Path propertiesPath) {

            try (InputStream input = new FileInputStream(propertiesPath.toFile())){
                Properties properties = new Properties();
                properties.load(input);
                Integer tryNmbr = new Integer(properties.getProperty("tryNumber"));
                this.tryNumber = tryNmbr;
            } catch (FileNotFoundException ex ) {
                ex.printStackTrace();
                this.tryNumber = 8;
            } catch (IOException ex) {
                ex.printStackTrace();
                this.tryNumber = 8;
            } finally {
                return this;
            }
        }

        /**
         * @param secretBlockLongeur the secretBlockLongeur to set
         */
        public GameBuilder setSecretBlockLongeur(final Path propertiesPath) {

            try (InputStream input = new FileInputStream(propertiesPath.toFile())){
                Properties properties = new Properties();
                properties.load(input);
                Integer tryNmbr = new Integer(properties.getProperty("secretBlockLongeur"));
                this.secretBlockLongeur = tryNmbr;
            } catch (FileNotFoundException ex ) {
                ex.printStackTrace();
                this.secretBlockLongeur = 4;
            } catch (IOException ex) {
                ex.printStackTrace();
                this.secretBlockLongeur = 4;
            } finally {
                return this;
            }
        }

        /**
         * @param gameType the gameType to set
         */
        public GameBuilder setGameType(GameType gameType) {
            this.gameType = gameType;
            return this;
        }

        /**
         * @param gameMode the gameMode to set
         */
        public GameBuilder setGameMode(GameMode gameMode) {
            this.gameMode = gameMode;
            return this;
        }

        public Game buildGame() {
            return new Game(this);
        }
    }
}