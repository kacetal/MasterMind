/**
 *
 */
package fr.kacetal.mastermind.controller;

import java.util.stream.IntStream;

/**
 * @author artem
 */
public class ArraysComparator {

    protected int[] arrCompare(final int[] qst, final int[] rep) {
        return IntStream
                .range(0, qst.length)
                .sequential()
                .map(i -> Integer.compare(qst[i], rep[i]))
                .toArray();
    }

    protected String intToStrRechercheNormalizer(final int[] intToNormalize) {
        return null;
    }

    protected String intToStrMastermindNormalizer(final int[] sbQst, final int[] sbRsp) {
        return null;
    }

    public void play() {
    }
}