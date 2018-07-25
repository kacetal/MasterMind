package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        nbrOfTry = game.getTryNumber();

        secretBlock = new SecretBlock(game);
        secretArray = secretBlock.getArrOfNbr();

        String limMIN, limMAX;

        System.out.println("Devinez un nombre qui contient " + game.getSecretBlockLength() + " chiffres");

        minAILimit = IntStream.generate(() -> 0).limit(game.getSecretBlockLength()).toArray();
        maxAILimit = IntStream.generate(() -> 9).limit(game.getSecretBlockLength()).toArray();
        responseAIArray = IntStream.generate(() -> 5).limit(game.getSecretBlockLength()).toArray();

        boolean firstLoop = true;

        do {
            System.out.print("Il y a encore " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            System.out.print("Donnez un nombre de " + game.getSecretBlockLength() + " chiffres:");
            responsePlayerArray = (responsePlayerBlock = getPlayerResponse()).getArrOfNbr();

            System.out.println("Votre reponse:   |" + responsePlayerBlock + "|");
            astucePlayer = parseStringFromArray(arrCompare(secretArray, responsePlayerArray));
            pause(1000);
            System.out.println("Votre astuce est:|" + astucePlayer + "|");
            pause(1000);

            if (firstLoop) {
                responseAIArray = IntStream.generate(() -> 5).limit(game.getSecretBlockLength()).toArray();
                responseAIBlock = new SecretBlock(responseAIArray);
                System.out.print("AI calcule...:   |");
                for (int i = 0; i < game.getSecretBlockLength(); i++) {
                    System.out.print('*');
                    pause(1000);
                }
                System.out.println("|");
                arrDiffAI = arrCompare(secretArray, responseAIArray);
                astuceAI = parseStringFromArray(arrDiffAI);
                System.out.println("Astuce pour AI:  |" + astuceAI + "|");
                firstLoop = false;
            } else {
                System.out.print("AI recalcule...: |");
                responseAIGenerator(minAILimit, maxAILimit, responseAIArray, game.getSecretBlockLength());
                arrDiffAI = arrCompare(secretArray, responseAIArray);
                astuceAI = parseStringFromArray(arrDiffAI);
                System.out.println("Astuce pour AI:  |" + astuceAI + "|");
            }

            if (game.isDevMode()) {
                limMIN = Arrays.stream(minAILimit).mapToObj(String::valueOf).collect(Collectors.joining());
                limMAX = Arrays.stream(maxAILimit).mapToObj(String::valueOf).collect(Collectors.joining());
                System.out.println("AI max limite:   |" + limMAX + "|");
                System.out.println("AI est proposé:  |" + responseAIBlock + "|");
                System.out.println("AI min limite:   |" + limMIN + "|");
                System.out.println("Vraie reponse:   |" + secretBlock + "|");
            }

            pause(2000);

            if (astucePlayer.equals(astuceAI) && isWinner(astucePlayer)) {
                System.out.println("La partie NULL!");
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                break;
            } else if (isWinner(astucePlayer)) {
                System.out.println("Félicitation! Vous avez gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                break;
            } else if (isWinner(astuceAI)) {
                System.out.println("Perdu! AI a gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                break;
            } else if (nbrOfTry <= 0) {
                System.out.println("Il n'y a plus d'essai!");
                System.out.println("Vous et AI êtes perdu!");
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                break;
            }
            System.out.println("Mauvaise reponse.\n");
            pause(1000);
            responseLimitsAnalyze(minAILimit, maxAILimit, responseAIArray, arrDiffAI, game.getSecretBlockLength());

        } while (true);
    }

    private void responseAIGenerator(int[] min, int[] max, int[] responseArray, int longeur) {
        for (int i = 0; i < longeur; i++) {
            pause(1000);
            if (min[i] == max[i]) {
                responseArray[i] = min[i];
            } else {
                responseArray[i] = new Random().ints(1, min[i], max[i]).findFirst().getAsInt();
            }
            System.out.print('*');
        }
        pause(1000);
        System.out.println("|");
    }
}
