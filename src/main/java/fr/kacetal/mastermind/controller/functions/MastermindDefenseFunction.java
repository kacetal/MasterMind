package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.MastermindComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MastermindDefenseFunction extends MastermindComparator {

    public static final Logger LOGGER = LogManager.getLogger(MastermindDefenseFunction.class.getName());

    private SecretBlock secretBlock;

    private int[] secretArray;

    public MastermindDefenseFunction(Game game) {
        super(game);
    }

    @Override
    public void play() {
        LOGGER.info("Entering in the method play()");
        listOfAllValues = getListOfAllValues(game.getSecretBlockLength(), game.getNmbrUtilisable());
        nbrOfTry = game.getTryNumber();

        LOGGER.info("Number of try is {}", nbrOfTry);

        System.out.println("Faire un nombre qui contient " + game.getSecretBlockLength() + " chiffres.");
        System.out.println("Les chiffres sont disponibles de 0 à " + (game.getNmbrUtilisable() - 1));

        secretArray = (secretBlock = getPlayerResponse(game.getNmbrUtilisable())).getArrOfNbr();

        LOGGER.info("Secret Block is {}", Arrays.toString(secretArray));

        responseArray = IntStream
                .range(0, game.getSecretBlockLength())
                .map(i -> {
                    if (i < game.getSecretBlockLength() / 2) return 0;
                    else return 1;
                })
                .toArray();

        LOGGER.info("Response from PC is {}", Arrays.toString(responseArray));

        responseBlock = new SecretBlock(responseArray);

        int nbrActuel = listOfAllValues.size();

        int nbrEliminated;

        if (game.isDevMode()) {
            System.out.printf("% 8d valeurs dans la list%n", nbrActuel);
            pause(1500);
        }

        while (true) {
            LOGGER.info("Number of try is {}", nbrOfTry);

            System.out.print("Il y a " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            arrDiffAI = arrCompare(secretArray, responseArray);
            hint = parseStringFromArray(arrDiffAI);

            System.out.println();

            System.out.println("AI essaye avec: |" + responseBlock + "|");
            System.out.println("Nmbr caché est: |" + secretBlock + "|");
            System.out.println("Astuce pour AI: " + hint + "\n");

            LOGGER.info("AI try with {}", Arrays.toString(responseArray));
            LOGGER.info("Hint for AI is {}", hint);

            pause(1500);

            if (Arrays.equals(arrDiffAI, responseToWin)) {
                System.out.println("Perdu! AI a gagné!");
                System.out.println("Il le reste encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                LOGGER.info("Player lost. AI win");
                LOGGER.info("There is {} umber of try", nbrOfTry);
                break;
            } else if (!Arrays.equals(arrDiffAI, responseToWin) && nbrOfTry <= 0) {
                System.out.println("Mauvaise reponse.\nAI n'a plus d'essai.\nFélicitation!");
                LOGGER.info("Bad response, AI doesn't have any try. Player win.");
                break;
            } else if (!Arrays.equals(arrDiffAI, responseToWin)) {
                System.out.println("Mauvaise reponse.\n");
                listEliminator(listOfAllValues, responseArray, arrDiffAI);
                responseBlock = listOfAllValues.get(listOfAllValues.size() / 2);
                responseArray = responseBlock.getArrOfNbr();
                pause(1500);
                System.out.println("AI élimine les valeurs inconvenables\n");
                pause(1500);
                nbrEliminated = nbrActuel - listOfAllValues.size();
                nbrActuel = listOfAllValues.size();
                if (game.isDevMode()) {
                    System.out.printf("%8d valeurs éliminées%n", nbrEliminated);
                    System.out.printf("%8d valeurs dans la list%n%n", nbrActuel);
                    pause(3000);
                }

                LOGGER.debug(nbrEliminated + "{ numbers was deleted");
                LOGGER.debug(nbrActuel + "");
            }
        }
    }
}
