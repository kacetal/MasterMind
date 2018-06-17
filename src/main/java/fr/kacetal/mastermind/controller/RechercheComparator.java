/**
 *
 */
package fr.kacetal.mastermind.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author artem
 */
public class RechercheComparator extends ArraysComparator {

    @Override
    public String intToStrRechercheNormalizer(final int[] intArr) {
        return Arrays.stream(intArr)
                .sequential()
                .mapToObj(i -> i == 0 ? "=" : (i > 0 ? "+" : "-") )
                .collect(Collectors.joining());
    }
}