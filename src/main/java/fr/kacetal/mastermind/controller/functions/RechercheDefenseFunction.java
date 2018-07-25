package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RechercheDefenseFunction extends RechercheComparator {

    private SecretBlock secretBlock;

    private int[] secretArray;

    public RechercheDefenseFunction(Game game) {
        super(game);
    }

    static void responseLimitsAnalyze(int[] min, int[] max, int[] responseArray, int[] arrDiff, int longeur) {
        for (int i = 0; i < longeur; i++) {
            if (arrDiff[i] < 0) {
                max[i] = responseArray[i];
            } else if (arrDiff[i] > 0) {
                min[i] = responseArray[i] + 1;
            } else {
                min[i] = responseArray[i];
                max[i] = responseArray[i];
            }
        }
    }

    private static void responseAIGenerator(int[] min, int[] max, int[] responseArray, int longeur) {
        for (int i = 0; i < longeur; i++) {
            pause(1000);
            if (min[i] == max[i]) {
                responseArray[i] = min[i];
            } else {
                responseArray[i] = new Random().ints(1, min[i], max[i]).findFirst().getAsInt();
            }
            System.out.print(responseArray[i]);
        }
        pause(1000);
        System.out.println("|");
    }

    @Override
    public void play() {
        nbrOfTry = game.getTryNumber();

        String limMIN, limMAX;

        System.out.println("Faire un nombre de " + game.getSecretBlockLength() + " chiffres");

        secretArray = (secretBlock = getPlayerResponse()).getArrOfNbr();

        minAILimit = IntStream.generate(() -> 0).limit(game.getSecretBlockLength()).toArray();
        maxAILimit = IntStream.generate(() -> 9).limit(game.getSecretBlockLength()).toArray();
        responseArray = IntStream.generate(() -> 5).limit(game.getSecretBlockLength()).toArray();

        responseBlock = new SecretBlock(responseArray);


        do {
            System.out.print("AI a " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            arrDiffAI = arrCompare(secretArray, responseArray);
            hint = parseStringFromArray(arrDiffAI);

            System.out.println();

            if (game.isDevMode()) {
                limMIN = Arrays.stream(minAILimit).mapToObj(String::valueOf).collect(Collectors.joining());
                limMAX = Arrays.stream(maxAILimit).mapToObj(String::valueOf).collect(Collectors.joining());
                System.out.println("Limite max est: |" + limMAX + "|");
                System.out.println("Limite min est: |" + limMIN + "|");
            }


            System.out.println("AI essaye avec: |" + responseBlock + "|");
            System.out.println("Nmbr caché est: |" + secretBlock + "|");
            System.out.println("Astuce pour AI: |" + hint + "|\n");

            if (!hint.equals(responseToWin) && nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nAI n'a plus d'essai.\nFélicitation!");
                break;
            } else if (!hint.equals(responseToWin)) {
                System.out.println("Mauvaise reponse.\n");
                responseLimitsAnalyze(minAILimit, maxAILimit, responseArray, arrDiffAI, game.getSecretBlockLength());
                System.out.print("Nombre calculé: |");
                responseAIGenerator(minAILimit, maxAILimit, responseArray, game.getSecretBlockLength());
                responseBlock = new SecretBlock(responseArray);
            } else if (hint.equals(responseToWin)) {
                System.out.println("Perdu! AI a gagné!");
                System.out.println("Il le reste encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            }
        } while (true);
    }
}
