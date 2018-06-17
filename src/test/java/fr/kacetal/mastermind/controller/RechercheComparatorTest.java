package fr.kacetal.mastermind.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RechercheComparatorTest {

    @Test
    public void intToStrRechercheV1() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{4, 2, 7, 8};
        String expected = "-=--";
        int[] intActual = new ArraysComparator().arrCompare(question, response);
        String actual = new RechercheComparator().intToStrRechercheNormalizer(intActual);
        assertEquals(expected, actual);
    }

    @Test
    public void intToStrRechercheV2() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{2, 2, 1, 4};
        String expected = "-=+=";
        int[] intActual = new ArraysComparator().arrCompare(question, response);
        String actual = new RechercheComparator().intToStrRechercheNormalizer(intActual);
        assertEquals(expected, actual);
    }

    @Test
    public void arrCompareEquals() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{1, 2, 3, 4};
        String expected = "====";
        int[] intActual = new ArraysComparator().arrCompare(question, response);
        String actual = new RechercheComparator().intToStrRechercheNormalizer(intActual);
        assertEquals(expected, actual);
    }
}