package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;


public class RechercheChallengerFunction extends RechercheComparator {

    public RechercheChallengerFunction(final Game game) {
        super(game);
    }

    @Override
    public void play() {
        nbrOfTry = game.getTryNumber();

        secretBlock = new SecretBlock(game);
        secretArray = secretBlock.getArrOfNbr();

        System.out.println("Devinez nombre qui contient " + game.getSecretBlockLongeur() + " chiffres");

        do {
            System.out.print("Il y a encore " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            System.out.print("Donnez un nombre de " + game.getSecretBlockLongeur() + " chiffres:");

            responseArray = (responseBlock = getPlayerResponse()).getArrOfNbr();

            System.out.println("Votre reponse: |" + responseBlock + "|");

            if (game.isDevMode()) {
                System.out.println("Vraie reponse: |" + secretBlock + "|");
            }

            astuce = getAstuce();

            pause(1000);
            System.out.println("Astuce cachée: |" + astuce + "|");

            pause(1000);

            if (!isWinner(astuce) && nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nVous n'avez plus d'essai.\nPerdu!");
                break;
            } else if (isWinner(astuce)) {
                System.out.println("Félicitation! Vous avez gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            }
            System.out.println("Mauvaise reponse.\n");
            pause(1000);
        } while (true);
    }


    private String getAstuce() {
        return intArrToStrNormalizer(arrCompare(secretArray, responseArray));
    }
}