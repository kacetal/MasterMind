package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;
import fr.kacetal.mastermind.model.SecretBlock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

/**
 * @author artem
 * @see ArraysComparator
 */
public class MastermindComparator extends ArraysComparator {

    protected final int[] responseToWin;

    protected List<SecretBlock> listOfAllValues;

    public MastermindComparator(Game game) {
        super(game);
        responseToWin = new int[]{game.getSecretBlockLongeur(), 0};
    }

    protected List<SecretBlock> getListOfAllValues(final int longeur, final int limMax) {

        List<SecretBlock> listOfValues = new LinkedList<>();

        int limOfValue = (int) pow(limMax, longeur);

        int[] arrBuffer = new int[longeur];

        for (int i = 0, t; i < limOfValue; i++) {

            t = i;
            for (int j = longeur - 1; j >= 0; j--) {
                arrBuffer[j] = t % limMax;
                t /= limMax;
            }
            listOfValues.add(new SecretBlock(arrBuffer));
        }

        return listOfValues;
    }

    @Override
    protected int[] arrCompare(final int[] secretArray, final int[] responseArray) {

        List<Integer> secretList = Arrays.stream(secretArray).boxed().collect(Collectors.toList());
        List<Integer> responseList = Arrays.stream(responseArray).boxed().collect(Collectors.toList());

        int goodPlace = 0;
        int present = 0;

        for (int i = 0; i < secretList.size(); ) {
            if (secretList.get(i).equals(responseList.get(i))) {
                goodPlace++;
                secretList.remove(i);
                responseList.remove(i);
                i = 0;
                continue;
            }
            i++;
        }

        out:
        for (int i = 0; i < secretList.size(); ) {
            for (int j = 0; j < responseList.size(); j++) {
                if (secretList.get(i).equals(responseList.get(j))) {
                    present++;
                    secretList.remove(i);
                    responseList.remove(j);
                    i = 0;
                    continue out;
                }
            }
            i++;
        }

        return new int[]{goodPlace, present};
    }

    @Override
    protected String intArrToStrNormalizer(final int[] arrayToNormalize) {
        return String.format("%d bien placés, %d présents.%n", arrayToNormalize[0], arrayToNormalize[1]);
    }

    protected boolean isWinner(final int[] arrToWin) {
        return Arrays.equals(arrToWin, responseToWin);
    }
}