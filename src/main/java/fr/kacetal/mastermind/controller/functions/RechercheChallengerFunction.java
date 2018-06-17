package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import fr.kacetal.mastermind.view.GamePlayDialog;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public void play() {
        SecretBlock responseBlock;

        int[] responseArray, rspArrDiff;

        String astuce, responseToWin;

        int nbrOfTry = game.getTryNumber();

        responseToWin = Stream.generate(() -> "=")
                .limit(game.getSecretBlockLongeur())
                .collect(Collectors.joining());

        System.out.println("Devinez nombre qui contient " + game.getSecretBlockLongeur() + " chiffres");
        do {
            System.out.print("Vous avez " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            responseBlock = gamePlayDialog.getSecretBlockResponse();
            responseArray = responseBlock.getArrOfNbr();

            System.out.println("Votre reponse: |" + responseBlock + "|");

            if (game.isDevMode()) {
                System.out.println("Vraie reponse: |" + secretBlock + "|");
            }

            rspArrDiff = arrCompare(secretArray, responseArray);
            astuce = intToStrRechercheNormalizer(rspArrDiff);
            System.out.println("Astuce cachée: |" + astuce + "|");

            if (!astuce.equals(responseToWin) && nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nVous n'avez plus d'essai.\nPerdu!");
                break;
            } else if (!astuce.equals(responseToWin)) {
                System.out.println("Mauvaise reponse.");
            } else if (astuce.equals(responseToWin)) {
                System.out.println("Félicitation! Vous avez gagné!");
                System.out.println("Il vous reste encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            }
        } while (true);
    }
}