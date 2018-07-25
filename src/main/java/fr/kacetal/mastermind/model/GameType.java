/**
 *
 */
package fr.kacetal.mastermind.model;

/**
 * All the type of the game
 *
 * @author Artem
 *
 */
public enum GameType {

    RECHERCHE("Recherche +/-", "Le but : découvrir la combinaison à X chiffres de l'adversaire (le défenseur). Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+), plus petit (-) ou si c'est le bon chiffre (=)."),
    MASTERMIND("Mastermind", "Le but : découvrir la combinaison à X chiffres de l'adversaire (le défenseur). Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque proposition le nombre de chiffre de la proposition qui apparaissent à la bonne place et à la mauvaise place dans la combinaison secrète.");

    private final String name;

    private final String explication;

    GameType(String name, String explication) {
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.name;
    }
}