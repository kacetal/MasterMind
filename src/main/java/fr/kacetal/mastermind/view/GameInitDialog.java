package fr.kacetal.mastermind.view;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;

import java.io.IOException;

import static java.lang.Character.getNumericValue;

/**
 * Class for asking a player the parameters of the {@link Game}<p>
 * during the game initialization dialog.
 *
 * @author Artem
 * @see GameType
 * @see GameMode
 */
public class GameInitDialog {

    private StringBuilder sb = new StringBuilder();

    /**
     * Method return true or false for the mode developer
     *
     * @return boolean isDeveloper true or false
     */
    public boolean isDeveloper() {
        sb.append("Bonjour!\n")
                .append("Voulez-vouz jouer en mode \"Développeur\"\n")
                .append("\t1. Oui\n")
                .append("\t2. Non\n")
                .append("\nVotre choix est: ");
        System.out.print(sb);
        sb.delete(0, sb.length());

        final int choice = getChoice(2);

        return choice == 1;
    }

    /**
     * Method return the type of the Game.
     * @return {@link GameType}
     */
    public GameType getGameType() {
        sb.append("Choisissez le type de joue:\n")
                .append("\t1. " + GameType.MASTERMIND.getName() + '\n')
                .append("\t\t" + GameType.MASTERMIND.getExplication() + '\n')
                .append("\t2. " + GameType.RECHERCHE.getName() + '\n')
                .append("\t\t" + GameType.RECHERCHE.getExplication() + '\n')
                .append("\nVotre choix est: ");
        System.out.print(sb);
        sb.delete(0, sb.length());

        final int choice = getChoice(2);

        return choice == 1 ? GameType.MASTERMIND : GameType.RECHERCHE;
    }

    /**
     * Method return the mode of the Game.
     * @return {@link GameMode}
     */
    public GameMode getGameMode() {
        sb.append("Choisissez le mode de joue:\n")
                .append("\t1. " + GameMode.CHALLENGER.getName() + '\n')
                .append("\t\t" + GameMode.CHALLENGER.getExplication() + '\n')
                .append("\t2. " + GameMode.DEFENSEUR.getName() + '\n')
                .append("\t\t" + GameMode.DEFENSEUR.getExplication() + '\n')
                .append("\t3. " + GameMode.DUEL.getName() + '\n')
                .append("\t\t" + GameMode.DUEL.getExplication() + '\n')
                .append("\nVotre choix est: ");
        System.out.print(sb);
        sb.delete(0, sb.length());

        final int choice = getChoice(3);

        return choice == 1 ? GameMode.CHALLENGER : (choice == 2 ? GameMode.DEFENSEUR : GameMode.DUEL);
    }

    /**
     * Return one code in the finish the game
     *
     * @return {@code int 1, 2, 3}
     */
    public int getReplayMode() {

        sb.append("\nVoulez-vous rejouer?\n")
                .append("\t1. Rejouer le dernier jeu.\n")
                .append("\t2. Choisir le nouveau mode de jeu.\n")
                .append("\t3. Terminer le jeu.\n")
                .append("\nVotre choix est: ");

        System.out.print(sb);
        sb.delete(0, sb.length());

        return getChoice(3);
    }

    /**
     * Method gets one {@code char} from {@code System.in} and changes it to {@code int}
     * @param limit limit maximum for figure to return
     * @return int values for return are {@code [1 , limit]}
     */
    private int getChoice(int limit) {

        char choice = 0, ignore;

        if (limit < 0) {
            limit = 0;
        } else if (limit > 9) {
            limit = 9;
        }

        try {
            do {

                choice = (char) System.in.read();


                do {
                    ignore = (char) System.in.read();
                } while (ignore != '\n');


            } while (getNumericValue(choice) <= 0 || limit < getNumericValue(choice));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getNumericValue(choice);
    }
}