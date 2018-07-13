package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.MastermindComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MastermindDefenseFunction extends MastermindComparator {

    private SecretBlock secretBlock;

    private int[] secretArray;

    public MastermindDefenseFunction(Game game) {
        super(game);
    }

    @Override
    public void play() {
        listOfAllValues = getListOfAllValues(game.getSecretBlockLongeur(), game.getNmbrUtilisable());
        nbrOfTry = game.getTryNumber();

        System.out.println("Faire un nombre qui contient " + game.getSecretBlockLongeur() + " chiffres.");
        System.out.println("Les chiffres sont disponibles de 0 à " + (game.getNmbrUtilisable() - 1));

        secretArray = (secretBlock = getPlayerResponse(game.getNmbrUtilisable())).getArrOfNbr();

        responseArray = IntStream
                .range(0, game.getSecretBlockLongeur())
                .map(i -> {
                    if (i < game.getSecretBlockLongeur() / 2) return 0;
                    else return 1;
                })
                .toArray();

        responseBlock = new SecretBlock(responseArray);

        int randomPosition;

        int nbrActuel = listOfAllValues.size();

        int nbrEliminated;

        if (game.isDevMode()) {
            System.out.printf("% 8d valeurs dans la list%n", nbrActuel);
            pause(1500);
        }

        while (true) {

            System.out.print("Il y a " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            arrDiffAI = arrCompare(secretArray, responseArray);
            astuce = intArrToStrNormalizer(arrDiffAI);

            System.out.println();

            System.out.println("AI essaye avec: |" + responseBlock + "|");
            System.out.println("Nmbr caché est: |" + secretBlock + "|");
            System.out.println("Astuce pour AI: " + astuce + "\n");

            pause(1500);


            if (Arrays.equals(arrDiffAI, responseToWin)) {
                System.out.println("Perdu! AI a gagné!");
                System.out.println("Il le reste encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            } else if (!Arrays.equals(arrDiffAI, responseToWin) && nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nAI n'a plus d'essai.\nFélicitation!");
                break;
            } else if (!Arrays.equals(arrDiffAI, responseToWin)) {

                System.out.println("Mauvaise reponse.\n");
                listEliminator(listOfAllValues, responseArray, arrDiffAI);
                randomPosition = (int) (Math.random() * listOfAllValues.size());
                responseBlock = listOfAllValues.get(randomPosition);
                responseArray = responseBlock.getArrOfNbr();
                pause(1500);
                System.out.println("AI eliminet les valeurs inconvenables\n");
                pause(1500);
                if (game.isDevMode()) {
                    nbrEliminated = nbrActuel - listOfAllValues.size();
                    nbrActuel = listOfAllValues.size();
                    System.out.printf("%8d valeurs eliminées%n", nbrEliminated);
                    System.out.printf("%8d valeurs dans la list%n%n", nbrActuel);
                    pause(3000);
                }
            }
        }


    }

    void listEliminator(final List<SecretBlock> listOfAllValues, final int[] responseArray, final int[] arrDiffAIMasque) {

        int[] arrToCompare;

        SecretBlock responseBlock = new SecretBlock(responseArray);

        for (int i = 0; i < listOfAllValues.size(); i++) {
            // Remove the same Block from List
            if (listOfAllValues.get(i).equals(responseBlock)) {
                listOfAllValues.remove(i);
                i--;
                continue;
            }

            //We compare each Block from list with response for get the new Masque
            arrToCompare = arrCompare(listOfAllValues.get(i).getArrOfNbr(), responseArray);

            //Remove the Block which have different masque
            if (!Arrays.equals(arrToCompare, arrDiffAIMasque)) {
                listOfAllValues.remove(i);
                i--;
            }
        }
    }


}
