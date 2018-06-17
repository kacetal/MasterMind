/**
 *
 */
package fr.kacetal.mastermind.controller;

import java.util.Arrays;

/**
 * @author artem
 * @see ArraysComparator
 */
public class MasterMindComparator extends ArraysComparator {

    @Override
    protected String intToStrMastermindNormalizer(final int[] sbQst, final int[] sbRsp) {

        if (Arrays.equals(arrCompare(sbQst, sbRsp), arrCompare(sbQst, sbQst))) {
            return "Vous avez gagné";
        }

        int present = 0;
        int goodPlace = 0;

        for (int i = 0; i < sbQst.length; i++) {
            for (int j = 0; j < sbRsp.length; j++) {
                if (sbQst[i] == sbRsp[j] && i == j) {
                    goodPlace++;
                    present++;
                    break;
                } else if (sbQst[i] == sbRsp[j]) {
                    present++;
                    break;
                }
            }
        }


        if ((present - goodPlace) == 0 && goodPlace != 0) {
            return String.format("Réponse : %d bien placés", goodPlace);
        } else if ((present - goodPlace) > 0 && goodPlace != 0) {
            return String.format("Réponse : %d présent, %d bien placés", (present - goodPlace), goodPlace);
        } else if ((present - goodPlace) > 0 && goodPlace == 0) {
            return String.format("Réponse : %d présent", present);
        } else {
            return "Réponse : 0 présent, 0 bien placés";
        }
    }
}