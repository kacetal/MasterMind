package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import fr.kacetal.mastermind.view.GamePlayDialog;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RechercheDefenseFunction extends RechercheComparator {

    private final Game game;

    private final GamePlayDialog gamePlayDialog;

    private int nbrOfTry;

    private int[] max;
    private int[] min;
    private int[] arrDiff;
    private int[] arrRsp;
    private int[] arrQst;

    public RechercheDefenseFunction(Game game) {
        this.game = game;
        gamePlayDialog = new GamePlayDialog(game);
        nbrOfTry = game.getTryNumber();
    }


    @Override
    public void play() {

        SecretBlock qstBlock, rspBlock;

        String astuce, responseToWin;

        String limMIN, limMAX;

        responseToWin = Stream.generate(() -> "=")
                .limit(game.getSecretBlockLongeur())
                .collect(Collectors.joining());

        System.out.println("Faire un nombre de " + game.getSecretBlockLongeur() + " chiffres");
        qstBlock = gamePlayDialog.getSecretBlockResponse();

        arrQst = qstBlock.getArrOfNbr();

        min = IntStream.generate(() -> 0).limit(game.getSecretBlockLongeur()).toArray();
        max = IntStream.generate(() -> 9).limit(game.getSecretBlockLongeur()).toArray();
        arrRsp = IntStream.generate(() -> 5).limit(game.getSecretBlockLongeur()).toArray();

        rspBlock = new SecretBlock(arrRsp);


        do {
            System.out.print("AI a " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            arrDiff = arrCompare(arrQst, arrRsp);
            astuce = intToStrRechercheNormalizer(arrDiff);

            System.out.println();

            if (game.isDevMode()) {
                limMIN = Arrays.stream(min).mapToObj(String::valueOf).collect(Collectors.joining());
                limMAX = Arrays.stream(max).mapToObj(String::valueOf).collect(Collectors.joining());
                System.out.println("Limite max est: |" + limMAX + "|");
                System.out.println("Limite min est: |" + limMIN + "|");

            }


            System.out.println("AI essaye avec: |" + rspBlock + "|");
            System.out.println("Nmbr caché est: |" + qstBlock + "|");
            System.out.println("Astuce pour AI: |" + astuce + "|\n");

            if (!astuce.equals(responseToWin) && nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nAI n'a plus d'essai.\nFélicitation!");
                break;
            } else if (!astuce.equals(responseToWin)) {
                System.out.println("Mauvaise reponse.\n");
                responseAnalyze();
                responseGenerator();
                rspBlock = new SecretBlock(arrRsp);
            } else if (astuce.equals(responseToWin)) {
                System.out.println("Perdu! AI a gagné!");
                System.out.println("Il le reste encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            }
        } while (true);
    }

    private void responseAnalyze() {
        for (int i = 0; i < game.getSecretBlockLongeur(); i++) {
            if (arrDiff[i] < 0) {
                max[i] = arrRsp[i];
            } else if (arrDiff[i] > 0) {
                min[i] = arrRsp[i] + 1;
            } else {
                min[i] = arrRsp[i];
                max[i] = arrRsp[i];
            }
        }
    }

    private void responseGenerator() {
        System.out.print("Nombre calculé: |");
        for (int i = 0; i < game.getSecretBlockLongeur(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            if (min[i] == max[i]) {
                arrRsp[i] = min[i];
            } else {
                arrRsp[i] = new Random().ints(1, min[i], max[i]).findFirst().getAsInt();
            }
            System.out.print(arrRsp[i]);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        System.out.println("|");
    }
}
