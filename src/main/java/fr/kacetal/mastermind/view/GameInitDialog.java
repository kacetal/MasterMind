package fr.kacetal.mastermind.view;

import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;

import static java.lang.Character.getNumericValue;

import java.io.IOException;

public class GameInitDialog {

    public boolean isDeveloppeur() {

        StringBuilder sb = new StringBuilder();

        sb.append("Bonjour!\n")
                .append("Voulez-vouz jouer en mode \"Développeur\"\n")
                .append("\t1. Oui\n")
                .append("\t2. Non\n")
                .append("\nVotre choix: ");
        System.out.print(sb);

        final int choice = getChoice(2);

        return choice == 1 ? true : false;
    }

    public GameType getGameType() {

        StringBuilder sb = new StringBuilder();

        sb.append("Choisissez le type de joue:\n")
                .append("\t1. " + GameType.MASTERMIND.getName() + '\n')
                .append("\t\t" + GameType.MASTERMIND.getExplication() + '\n')
                .append("\t2. " + GameType.RECHERCHE.getName() + '\n')
                .append("\t\t" + GameType.RECHERCHE.getExplication() + '\n')
                .append("\nVotre choix: ");
        System.out.print(sb);

        final int choice = getChoice(2);

        return choice == 1 ? GameType.MASTERMIND : GameType.RECHERCHE;
    }

    public GameMode getGameMode() {

        StringBuilder sb = new StringBuilder();

        sb.append("Choisissez le mode de joue:\n")
                .append("\t1. " + GameMode.CHALLENGER.getName() + '\n')
                .append("\t\t" + GameMode.CHALLENGER.getExplication() + '\n')
                .append("\t2. " + GameMode.DEFENSEUR.getName() + '\n')
                .append("\t\t" + GameMode.DEFENSEUR.getExplication() + '\n')
                .append("\t3. " + GameMode.DUEL.getName() + '\n')
                .append("\t\t" + GameMode.DUEL.getExplication() + '\n')
                .append("\nVotre choix: ");
        System.out.print(sb);

        final int choice = getChoice(3);

        return choice == 1 ? GameMode.CHALLENGER : (choice == 2 ? GameMode.DEFENSEUR : GameMode.DUEL);
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