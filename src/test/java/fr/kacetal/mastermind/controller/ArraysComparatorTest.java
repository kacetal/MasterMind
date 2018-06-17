package fr.kacetal.mastermind.controller;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ArraysComparatorTest {

    @Test
    public void arrCompareV1() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{4, 2, 7, 8};
        int[] expected = new int[]{-1, 0, -1, -1};
        int[] actual = new ArraysComparator().arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void arrCompareV2() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{2, 2, 1, 4};
        int[] expected = new int[]{-1, 0, 1, 0};
        int[] actual = new ArraysComparator().arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void arrCompareEquals() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{1, 2, 3, 4};
        int[] expected = new int[]{0, 0, 0, 0};
        int[] actual = new ArraysComparator().arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }
}