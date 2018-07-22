package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.MastermindComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MastermindChallengerFunction extends MastermindComparator {

    public static final Logger LOGGER = LogManager.getLogger(MastermindChallengerFunction.class.getName());

    private int[] arrDiff;

    public MastermindChallengerFunction(Game game) {
        super(game);
    }


    @Override
    public void play() {
        nbrOfTry = game.getTryNumber();

        secretBlock = new SecretBlock(game, game.getGameType());
        secretArray = secretBlock.getArrOfNbr();

        System.out.println("Devinez un nombre qui contient " + game.getSecretBlockLongeur() + " chiffres.");
        System.out.println("Les chiffres sont disponibles de 0 à " + (game.getNmbrUtilisable() - 1));

        while (true) {
            System.out.print("Il y a encore " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            System.out.print("Donnez un nombre:");

            responseArray = (responseBlock = getPlayerResponse()).getArrOfNbr();

            System.out.println("Votre reponse: |" + responseBlock + "|");

            if (game.isDevMode()) {
                System.out.println("Vraie reponse: |" + secretBlock + "|");
            }

            arrDiff = arrCompare(secretArray, responseArray);
            astuce = intArrToStrNormalizer(arrDiff);

            System.out.println(astuce);

            if (isWinner(arrDiff)) {
                System.out.println("Félicitation! Vous avez gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            } else if (nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nVous n'avez plus d'essai.\nPerdu!");
                break;
            }

            System.out.println("Mauvaise reponse.\n");
            pause(1000);
        }
    }
}
