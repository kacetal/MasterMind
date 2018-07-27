/**
 * package contain all the functions need for the game
 */
package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.ArraysComparator;
import fr.kacetal.mastermind.controller.MastermindComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.GameMode;
import fr.kacetal.mastermind.model.GameType;
import fr.kacetal.mastermind.model.SecretBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Class overrides the method {@link MastermindChallengerFunction#play()}
 * inherited from {@link MastermindComparator#play()}<p>
 * Game's settings {@link GameType#MASTERMIND} and {@link GameMode#CHALLENGER}
 *
 * @author Artem
 * @see MastermindComparator
 * @see ArraysComparator
 */
public class MastermindChallengerFunction extends MastermindComparator {

    public static final Logger LOGGER = LogManager.getLogger(MastermindChallengerFunction.class.getName());

    private int[] arrDiff;

    public MastermindChallengerFunction(Game game) {
        super(game);
    }

    @Override
    public void play() {
        LOGGER.info("Entering in the method play()");
        nbrOfTry = game.getTryNumber();

        secretBlock = new SecretBlock(game, game.getGameType());
        secretArray = secretBlock.getArrOfNbr();

        LOGGER.info("Secret Block is " + Arrays.toString(secretArray));

        System.out.printf("Devinez un nombre qui contient %d chiffres.%n", game.getSecretBlockLength());
        System.out.printf("Les chiffres sont disponibles de 0 à %d%n", (game.getNmbrUtilisable() - 1));

        while (true) {
            LOGGER.info("There are {} try", nbrOfTry);

            System.out.printf("Il y a encore %d", nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            System.out.print("Donnez un nombre:");

            responseArray = (responseBlock = getPlayerResponse()).getArrOfNbr();

            LOGGER.info("Player response is " + Arrays.toString(responseArray));

            System.out.println("Votre reponse: |" + responseBlock + "|");

            if (game.isDevMode()) {
                System.out.println("Vraie reponse: |" + secretBlock + "|");
            }

            arrDiff = arrCompare(secretArray, responseArray);
            hint = parseStringFromArray(arrDiff);

            LOGGER.info(hint);

            System.out.println(hint);

            if (isWinner(arrDiff)) {
                System.out.println("Félicitation! Vous avez gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                LOGGER.info("You win.");
                LOGGER.info("There are" + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            } else if (nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nVous n'avez plus d'essai.\nPerdu!");
                System.out.println("Vraie reponse: |" + secretBlock + "|");
                LOGGER.info("Bad response. You have no try. You lost.");
                break;
            }

            System.out.println("Mauvaise reponse.\n");
            LOGGER.info("Bad response.");
            pause(1000);
        }
    }
}
