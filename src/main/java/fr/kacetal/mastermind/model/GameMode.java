/**
 *
 */
package fr.kacetal.mastermind.model;

/**
 * All the modes of the game
 *
 * @author Artem
 *
 */
public enum GameMode {

    CHALLENGER("Challenger", "Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur"),
    DEFENSEUR("Défenseur", "Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète"),
    DUEL("Duel", "Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné");

    private final String name;

    private final String explication;

    GameMode(String name, String explication) {
        this.name = name;
        this.explication = explication;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the explication
     */
    public String getExplication() {
        return explication;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }
}