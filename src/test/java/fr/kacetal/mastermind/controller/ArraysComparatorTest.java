package fr.kacetal.mastermind.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArraysComparatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void arrCompareV1() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{4, 2, 7, 8};
        int[] expected = new int[]{-1, 0, -1, -1};
        int[] actual = ArraysComparator.arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void arrCompareV2() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{2, 2, 1, 4};
        int[] expected = new int[]{-1, 0, 1, 0};
        int[] actual = ArraysComparator.arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void arrCompareEquals() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{1, 2, 3, 4};
        int[] expected = new int[]{0, 0, 0, 0};
        int[] actual = ArraysComparator.arrCompare(question, response);
        assertArrayEquals(expected, actual);
    }
}