package fr.kacetal.mastermind.model;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class SecretBlock {

    private final int secretBlockLongeur;

    private final int[] arrOfNbr;

    public SecretBlock() {
        this.secretBlockLongeur = new Random().nextInt(10);
        this.arrOfNbr = new Random().ints(this.secretBlockLongeur, 0, 10).toArray();
    }

    public SecretBlock(final int[] arrOfNbr) {
        this.secretBlockLongeur = arrOfNbr.length;
        this.arrOfNbr = arrOfNbr;
    }

    public SecretBlock(final Game game) {
        this.secretBlockLongeur = game.getSecretBlockLongeur();
        this.arrOfNbr = new Random().ints(this.secretBlockLongeur, 0, 10).toArray();
    }

    public SecretBlock(final int nbrToArr, final Game game) {
        this.secretBlockLongeur = game.getSecretBlockLongeur();
        this.arrOfNbr = String.valueOf(nbrToArr).chars().map(Character::getNumericValue).toArray();
    }

    public SecretBlock(final String strToNbrArr, final Game game) {
        this.secretBlockLongeur = game.getSecretBlockLongeur();
        this.arrOfNbr = strToNbrArr.chars().map(Character::getNumericValue).toArray();
    }

    public int[] getArrOfNbr() {
        return arrOfNbr;
    }

    public int getNbrAt(final int position) {

        if (position <= 0) {
            return arrOfNbr[0];
        } else if (position >= secretBlockLongeur) {
            return arrOfNbr[secretBlockLongeur - 1];
        }

        return arrOfNbr[position];
    }

    public boolean isNbrExist(int number) {

        if (number < 0) {
            number = 0;
        } else if (number > 9) {
            number = 9;
        }

        for (int i = 0; i < arrOfNbr.length; i++) {
            if (number == arrOfNbr[i]) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return Arrays.stream(arrOfNbr).mapToObj(String::valueOf).collect(Collectors.joining());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + secretBlockLongeur;
        result = prime * result + Arrays.hashCode(arrOfNbr);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SecretBlock)) {
            return false;
        }
        SecretBlock other = (SecretBlock) obj;
        if (secretBlockLongeur != other.secretBlockLongeur) {
            return false;
        }
        if (!Arrays.equals(arrOfNbr, other.arrOfNbr)) {
            return false;
        }
        return true;
    }
}