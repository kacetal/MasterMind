package fr.kacetal.mastermind.view;

import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;

import java.io.IOException;

import static java.lang.Character.getNumericValue;

public class GameInitDialog {

    private StringBuilder sb = new StringBuilder();

    public boolean isDeveloppeur() {
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

    private int getChoice(int limit) {

        char choice = 0, ignore;

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