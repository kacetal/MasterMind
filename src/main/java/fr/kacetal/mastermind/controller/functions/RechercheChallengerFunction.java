package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import fr.kacetal.mastermind.view.GamePlayDialog;

public class RechercheChallengerFunction extends RechercheComparator {

    private final Game game;

    private final GamePlayDialog gamePlayDialog;

    private final SecretBlock secretBlock;

    private final int[] secretArray;

    public RechercheChallengerFunction(final Game game) {
        this.game = game;
        gamePlayDialog = new GamePlayDialog(game);
        secretBlock = new SecretBlock(game);
        secretArray = secretBlock.getArrOfNbr();
    }

    public void play() {
        SecretBlock responseBlock;

        int[] responseArray, respArrNormalized;

        String response, responseToWin = "";

        int nbrOfTry = game.getTryNumber();

        for (int i = 0; i < game.getSecretBlockLongeur(); i++) {
            responseToWin += "=";
        }

        System.out.println("Devinez nombre qui contien " + game.getSecretBlockLongeur() + " chiffres");
        do {
            System.out.print("Vous avez " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            responseBlock = gamePlayDialog.getSecretBlockResponse();
            responseArray = responseBlock.getArrOfNbr();

            System.out.println("Votre reponse: ->" + responseBlock.toString() + "<-");

            if (game.isDevMode()) {
                System.out.println("Vraie reponse: ->" + secretBlock + "<-");
            }

            respArrNormalized = RechercheComparator.arrCompare(secretArray, responseArray);
            response = RechercheComparator.intToStrRecherche(respArrNormalized);

            if (!response.equals(responseToWin) && nbrOfTry <= 0) {
                System.out.println("Astuce cachée: ->" + response + "<-");
                System.out.println("Mauvaise reponse.\nVous n'avez plus d'essai.\nPerdu!");
                break;
            } else if (!response.equals(responseToWin)) {
                System.out.println("Astuce cachée: ->" + response + "<-");
                System.out.println("Mauvaise reponse.");
                continue;
            } else if (response.equals(responseToWin)) {
                System.out.println("Astuce cachée: ->" + response + "<-");
                System.out.println("Félicitation! Vous avez gagné!");
                System.out.println("Il vous reste encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            }
        } while (true);
    }
}