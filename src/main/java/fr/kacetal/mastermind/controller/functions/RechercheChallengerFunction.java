package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.ArraysComparator;
import fr.kacetal.mastermind.controller.RechercheComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;
import fr.kacetal.mastermind.model.SecretBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Class overrides the method {@link RechercheChallengerFunction#play()}
 * inherited from {@link RechercheComparator#play()}<p>
 * Game's settings {@link GameType#RECHERCHE} and {@link GameMode#CHALLENGER}
 *
 * @author Artem
 * @see RechercheComparator
 * @see ArraysComparator
 */
public class RechercheChallengerFunction extends RechercheComparator {

    public static final Logger LOGGER = LogManager.getLogger(RechercheChallengerFunction.class.getName());

    public RechercheChallengerFunction(final Game game) {
        super(game);
    }

    @Override
    public void play() {
        LOGGER.info("Entering in the method play()");
        nbrOfTry = game.getTryNumber();

        secretBlock = new SecretBlock(game);
        secretArray = secretBlock.getArrOfNbr();

        LOGGER.info("Secret Block is " + Arrays.toString(secretArray));

        System.out.println("Devinez nombre qui contient " + game.getSecretBlockLength() + " chiffres");

        do {
            LOGGER.info("Il y a encore {}", nbrOfTry);

            System.out.print("Il y a encore " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            System.out.print("Donnez un nombre de " + game.getSecretBlockLength() + " chiffres:");

            responseArray = (responseBlock = getPlayerResponse()).getArrOfNbr();

            LOGGER.info("player response is " + Arrays.toString(responseArray));

            System.out.println("Votre reponse: |" + responseBlock + "|");

            if (game.isDevMode()) {
                System.out.println("Vraie reponse: |" + secretBlock + "|");
            }

            hint = getAstuce();

            LOGGER.info(hint);

            pause(1000);
            System.out.println("Astuce cachée: |" + hint + "|");

            pause(1000);

            if (!isWinner(hint) && nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nVous n'avez plus d'essai.\nPerdu!");
                System.out.println("Vraie reponse: |" + secretBlock + "|");
                LOGGER.info("Mauvaise reponse. Vous n'avez plus d'essai. Perdu!");
                break;
            } else if (isWinner(hint)) {
                System.out.println("Félicitation! Vous avez gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                LOGGER.info("Félicitation! Vous avez gagné!");
                LOGGER.info("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            }
            System.out.println("Mauvaise reponse.\n");
            LOGGER.info("Mauvaise reponse.");
            pause(1000);
        } while (true);
    }


    private String getAstuce() {
        return parseStringFromArray(arrCompare(secretArray, responseArray));
    }
}