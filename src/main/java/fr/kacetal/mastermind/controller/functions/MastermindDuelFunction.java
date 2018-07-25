package fr.kacetal.mastermind.controller.functions;

import fr.kacetal.mastermind.controller.MastermindComparator;
import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;

import java.util.stream.IntStream;

public class MastermindDuelFunction extends MastermindComparator {

    private SecretBlock responsePlayerBlock;

    private int[] responsePlayerArray;

    private int[] arrDiffPlayer;

    private SecretBlock responseAIBlock;

    private int[] responseAIArray;

    private String astucePlayer;

    private String astuceAI;

    public MastermindDuelFunction(Game game) {
        super(game);
    }

    @Override
    public void play() {

        //create list of all values possible
        listOfAllValues = getListOfAllValues(game.getSecretBlockLength(), game.getNmbrUtilisable());
        //Refresh number of try for new game
        nbrOfTry = game.getTryNumber();

        //Initialize new Secret Block
        secretBlock = new SecretBlock(game, game.getGameType());
        secretArray = secretBlock.getArrOfNbr();

        System.out.printf("Devinez un nombre qui contient %d chiffres.%n", game.getSecretBlockLength());
        System.out.printf("Les chiffres sont disponibles de 0 à %d.%n", (game.getNmbrUtilisable() - 1));

        //Initialize first response par default, par example for 4 elements [0, 0, 1, 1]
        responseAIArray = IntStream
                .range(0, game.getSecretBlockLength())
                .map(i -> {
                    if (i < game.getSecretBlockLength() / 2) return 0;
                    else return 1;
                })
                .toArray();
        //Initialize Block with AI response
        responseAIBlock = new SecretBlock(responseAIArray);

        int nbrActuel = listOfAllValues.size();

        int nbrEliminated;

        if (game.isDevMode()) {
            System.out.printf("% 8d valeurs dans la list%n", nbrActuel);
            pause(1500);
        }

        while (true) {
            System.out.print("Il y a " + nbrOfTry);
            System.out.println(gamePlayDialog.nbrOfTryDlg(nbrOfTry--));

            System.out.print("Donnez un nombre:");
            //get response from player
            responsePlayerArray = (responsePlayerBlock = getPlayerResponse()).getArrOfNbr();
            System.out.println("Votre reponse: |" + responsePlayerBlock + "|");

            //get array with hint for player
            arrDiffPlayer = arrCompare(secretArray, responsePlayerArray);
            //get String with hint for player
            astucePlayer = parseStringFromArray(arrDiffPlayer);

            //get array with hint for AI
            arrDiffAI = arrCompare(secretArray, responseAIArray);
            //get String with hint for AI
            astuceAI = parseStringFromArray(arrDiffAI);

            System.out.print("AI calcule...: |");
            for (int i = 0; i < game.getSecretBlockLength(); i++) {
                System.out.print('*');
                pause(1000);
            }
            System.out.println("|\n");

            pause(1000);
            System.out.println("Astuce pour vous : " + astucePlayer);
            pause(1000);
            System.out.println("Astuce pour AI   : " + astuceAI);
            pause(1000);

            if (game.isDevMode()) {
                System.out.println("Vraie reponse: |" + secretBlock + "|");
                System.out.println("AI est proposé:|" + responseAIBlock + "|");
            }

            if (isWinner(arrDiffPlayer) && isWinner(arrDiffAI)) {
                System.out.println("La partie NULL!");
                System.out.println("Nombre caché:  |" + secretBlock + "|");
                break;
            } else if (isWinner(arrDiffPlayer)) {
                System.out.println("Félicitation! Vous avez gagné! AI a perdu!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            } else if (isWinner(arrDiffAI)) {
                System.out.println("Perdu! AI a gagné!");
                System.out.println("Il y a encore " + nbrOfTry + gamePlayDialog.nbrOfTryDlg(nbrOfTry));
                break;
            } else if (nbrOfTry <= 0) {
                System.out.println("Il n'y a plus d'essai!");
                System.out.println("Vous et AI êtes perdu!");
                System.out.println("Nombre caché est:|" + secretBlock + "|");
                break;
            }

            System.out.println("Mauvaise reponse.\n");
            pause(1000);
            listEliminator(listOfAllValues, responseAIArray, arrDiffAI);

            responseAIBlock = listOfAllValues.get(listOfAllValues.size() / 2);
            responseAIArray = responseAIBlock.getArrOfNbr();

            pause(1500);
            System.out.println("AI élimine les valeurs inconvenables");
            pause(1500);
            if (game.isDevMode()) {
                nbrEliminated = nbrActuel - listOfAllValues.size();
                nbrActuel = listOfAllValues.size();
                System.out.printf("%6d valeurs éliminées%n", nbrEliminated);
                System.out.printf("%6d valeurs dans la list%n%n", nbrActuel);
                pause(2000);
            }
        }
    }
}
