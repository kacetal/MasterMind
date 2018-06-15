/**
 *
 */
package fr.kacetal.mastermind.controller;

import java.util.stream.IntStream;

/**
 * @author artem
 */
public abstract class ArraysComparator {

    public static int[] arrCompare(final int[] qst, final int[] rep) {

        return IntStream
                .range(0, qst.length)
                .sequential()
                .map(i -> Integer.compare(qst[i], rep[i]))
                .toArray();
    }
}