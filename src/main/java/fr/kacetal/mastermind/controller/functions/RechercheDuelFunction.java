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
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.kacetal.mastermind.controller.functions.RechercheDefenseFunction.responseLimitsReduce;

/**
 * Class overrides the method {@link RechercheDuelFunction#play()}
 * inherited from {@link RechercheComparator#play()}<p>
 * Game's settings {@link GameType#RECHERCHE} and {@link GameMode#DUEL}
 *
 * @author Artem
 * @see RechercheComparator
 * @see ArraysComparator
 */
public class RechercheDuelFunction extends RechercheComparator {

    public static final Logger LOGGER = LogManager.getLogger(RechercheDuelFunction.class.getName());

    private SecretBlock responsePlayerBlock;

    private int[] responsePlayerArray;

    private SecretBlock responseAIBlock;

    private int[] responseAIArray;

    private String hintForPlayer;

    private String hintForAI;

    public RechercheDuelFunction(Game game) {
        super(game);
    }

    @Override
    public void play() {
        LOGGER.info("Entering in the method play()");
        nbrOfTry = game.getTryNumber();

        secretBlock = new SecretBlock(game);
        secretArray = secretBlock.getArrOfNbr();

        LOGGER.info("Secret Block is {}", secretBlock.toString());

        String limMIN, limMAX;

        System.out.println("Devinez un nombre qui contient " + game.getSecretBlockLength() + " chiffres");

        minAILimit = IntStream.generate(() -> 0).limit(game.getSecretBlockLength()).toArray();
        maxAILimit = IntStream.generate(() -> 9).limit(game.getSecretBlockLength()).toArray();

        boolean firstLoop = true;

        do {
            System.out.print("Il y a encore " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            System.out.print("Donnez un nombre de " + game.getSecretBlockLength() + " chiffres:");
            responsePlayerArray = (responsePlayerBlock = getPlayerResponse()).getArrOfNbr();

            System.out.println("Votre reponse:   |" + responsePlayerBlock + "|");
            hintForPlayer = parseStringFromArray(arrCompare(secretArray, responsePlayerArray));
            pause(1000);
            System.out.println("Votre astuce est:|" + hintForPlayer + "|");
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
                hintForAI = parseStringFromArray(arrDiffAI);
                System.out.println("Astuce pour AI:  |" + hintForAI + "|");
                firstLoop = false;
            } else {
                System.out.print("AI recalcule...: |");
                responseAIGenerator(minAILimit, maxAILimit, responseAIArray, game.getSecretBlockLength());
                arrDiffAI = arrCompare(secretArray, responseAIArray);
                hintForAI = parseStringFromArray(arrDiffAI);
                System.out.println("Astuce pour AI:  |" + hintForAI + "|");
            }

            LOGGER.info("Response of the player is {}", responsePlayerBlock.toString());
            LOGGER.info("Hint for Player is {}", hintForPlayer);
            LOGGER.info("Response of the AI is {}", responseAIBlock.toString());
            LOGGER.info("Hint for AI is {}", hintForAI);

            LOGGER.debug("Limit max is {}", Arrays.toString(maxAILimit));
            LOGGER.debug("Limit min is {}", Arrays.toString(minAILimit));

            if (game.isDevMode()) {
                limMIN = Arrays.stream(minAILimit).mapToObj(String::valueOf).collect(Collectors.joining());
                limMAX = Arrays.stream(maxAILimit).mapToObj(String::valueOf).collect(Collectors.joining());
                System.out.println("AI max limite:   |" + limMAX + "|");
                System.out.println("AI est proposé:  |" + responseAIBlock + "|");
                System.out.println("AI min limite:   |" + limMIN + "|");
                System.out.println("Vraie reponse:   |" + secretBlock + "|");
            }

            pause(2000);

            if (hintForPlayer.equals(hintForAI) && isWinner(hintForPlayer)) {
                System.out.println("La partie NULL!");
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                LOGGER.info("Tae game");
                LOGGER.info("Secret block is {}", secretBlock.toString());
                break;
            } else if (isWinner(hintForPlayer)) {
                System.out.println("Félicitation! Vous avez gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                LOGGER.info("Player win. AI lost");
                LOGGER.info("There are {} number of try", nbrOfTry);
                break;
            } else if (isWinner(hintForAI)) {
                System.out.println("Perdu! AI a gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                LOGGER.info("Player lost. AI win");
                LOGGER.info("There are {} number of try", nbrOfTry);
                break;
            } else if (nbrOfTry <= 0) {
                System.out.println("Il n'y a plus d'essai!");
                System.out.println("AI et vous êtes perdu!");
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                LOGGER.info("Player lost. AI lost");
                LOGGER.info("Secret block is {}", secretBlock.toString());
                break;
            }
            System.out.println("Mauvaise reponse.\n");
            pause(1000);
            responseLimitsReduce(minAILimit, maxAILimit, responseAIArray, arrDiffAI, game.getSecretBlockLength());
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
