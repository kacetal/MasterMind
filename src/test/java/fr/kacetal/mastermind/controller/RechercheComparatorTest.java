package fr.kacetal.mastermind.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RechercheComparatorTest {

    @Test
    public void intToStrRechercheV1() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{4, 2, 7, 8};
        String expected = "-=--";
        int[] intActual = ArraysComparator.arrCompare(question, response);
        String actual = RechercheComparator.intToStrRecherche(intActual);
        assertEquals(expected, actual);
    }

    @Test
    public void intToStrRechercheV2() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{2, 2, 1, 4};
        String expected = "-=+=";
        int[] intActual = ArraysComparator.arrCompare(question, response);
        String actual = RechercheComparator.intToStrRecherche(intActual);
        assertEquals(expected, actual);
    }

    @Test
    public void arrCompareEquals() {
        int[] question = new int[]{1, 2, 3, 4};
        int[] response = new int[]{1, 2, 3, 4};
        String expected = "====";
        int[] intActual = ArraysComparator.arrCompare(question, response);
        String actual = RechercheComparator.intToStrRecherche(intActual);
        assertEquals(expected, actual);
    }
}