/**
 *
 */
package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.SecretBlock;

import java.util.Arrays;

/**
 * @author artem
 * @see ArraysComparator
 */
public class MasterMindComparator extends ArraysComparator {

    private String masterMindComparator(SecretBlock sbSec, SecretBlock sbRep) {

        if (Arrays.equals(arrCompare(sbSec.getArrOfNbr(), sbRep.getArrOfNbr()),
                arrCompare(sbSec.getArrOfNbr(), sbSec.getArrOfNbr()))
                ) {
            return "Vous avez gagné";
        }
        int present = 0;
        int goodPlace = 0;

        for (int i = 0; i < sbSec.getArrOfNbr().length; i++) {
            for (int j = 0; j < sbRep.getArrOfNbr().length; j++) {
                if (sbSec.getNbrAt(i) == sbRep.getNbrAt(j) && i == j) {
                    goodPlace++;
                    present++;
                    break;
                } else if (sbSec.getNbrAt(i) == sbRep.getNbrAt(j)) {
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