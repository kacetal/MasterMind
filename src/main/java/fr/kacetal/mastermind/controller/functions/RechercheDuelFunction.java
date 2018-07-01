package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.kacetal.mastermind.controller.functions.RechercheDefenseFunction.responseAIGenerator;
import static fr.kacetal.mastermind.controller.functions.RechercheDefenseFunction.responseLimitsAnalyze;

public class RechercheDuelFunction extends RechercheComparator {

    private SecretBlock responsePlayerBlock;

    private int[] responsePlayerArray;

    private SecretBlock responseAIBlock;

    private int[] responseAIArray;

    private String astucePlayer;

    private String astuceAI;

    public RechercheDuelFunction(Game game) {
        super(game);
    }

    @Override
    public void play() {

        secretBlock = new SecretBlock(game);
        secretArray = secretBlock.getArrOfNbr();

        String limMIN, limMAX;

        System.out.println("Devinez un nombre qui contient " + game.getSecretBlockLongeur() + " chiffres");

        minAILimit = IntStream.generate(() -> 0).limit(game.getSecretBlockLongeur()).toArray();
        maxAILimit = IntStream.generate(() -> 9).limit(game.getSecretBlockLongeur()).toArray();
        responseAIArray = IntStream.generate(() -> 5).limit(game.getSecretBlockLongeur()).toArray();

        boolean firstLoop = true;

        do {
            System.out.print("Il y a encore " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            System.out.print("Donnez un nombre de " + game.getSecretBlockLongeur() + " chiffres:");
            responsePlayerArray = (responsePlayerBlock = getPlayerResponse()).getArrOfNbr();

            System.out.println("Votre reponse:   |" + responsePlayerBlock + "|");
            astucePlayer = intToStrRechercheNormalizer(arrCompare(secretArray, responsePlayerArray));
            pause(1000);
            System.out.println("Votre astuce est:|" + astucePlayer + "|");
            pause(1000);

            if (firstLoop) {
                responseAIArray = IntStream.generate(() -> 5).limit(game.getSecretBlockLongeur()).toArray();
                responseAIBlock = new SecretBlock(responseAIArray);
                System.out.println("AI est proposée: |" + responseAIBlock + "|");
                firstLoop = false;
            } else {
                System.out.print("AI recalcule...: |");
                responseAIGenerator(minAILimit, maxAILimit, responseAIArray, game.getSecretBlockLongeur());
            }

            if (game.isDevMode()) {
                limMIN = Arrays.stream(minAILimit).mapToObj(String::valueOf).collect(Collectors.joining());
                limMAX = Arrays.stream(maxAILimit).mapToObj(String::valueOf).collect(Collectors.joining());
                System.out.println("AI max limite:   |" + limMAX + "|");
                System.out.println("AI min limite:   |" + limMIN + "|");
                System.out.println("Vraie reponse:   |" + secretBlock + "|");
            }

            pause(2000);


            arrDiffAI = arrCompare(secretArray, responseAIArray);
            astuceAI = intToStrRechercheNormalizer(arrDiffAI);

            if (astucePlayer.equals(astuceAI) && isWinner(astucePlayer)) {
                System.out.println("La partie NULL!");
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                break;
            } else if (isWinner(astucePlayer)) {
                System.out.println("Félicitation! Vous avez gagné! AI a été supprimé!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            } else if (isWinner(astuceAI)) {
                System.out.println("Perdu! AI a gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            } else if (nbrOfTry <= 0) {
                System.out.println("Il n'y a plus d'essai!");
                System.out.println("Vous et AI êtes perdu!");

            }
            System.out.println("Mauvaise reponse.\n");
            pause(1000);
            responseLimitsAnalyze(minAILimit, maxAILimit, responseAIArray, arrDiffAI, game.getSecretBlockLongeur());

        } while (true);
    }
}
