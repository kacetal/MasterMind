package fr.kacetal.mastermind.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Artem
 *
 * Class wrapper for store an array of numbers.
 */
public class SecretBlock {

    public static final Logger LOGGER = LogManager.getLogger(SecretBlock.class.getName());

    private final int secretBlockLongeur;

    private final int[] arrOfNbr;

    public SecretBlock() {
        this.secretBlockLongeur = new Random().nextInt(10);
        this.arrOfNbr = new Random().ints(this.secretBlockLongeur, 0, 10).toArray();
    }

    public SecretBlock( final int[] arrOfNbr) {
        this.secretBlockLongeur = arrOfNbr.length;
        this.arrOfNbr = arrOfNbr.clone();
    }

    public SecretBlock( final Game game) {
        this.secretBlockLongeur = game.getSecretBlockLongeur();
        this.arrOfNbr = new Random().ints(this.secretBlockLongeur, 0, 10).toArray();
    }

    public SecretBlock(final Game game, final GameType type) {
        this.secretBlockLongeur = game.getSecretBlockLongeur();
        if (type == GameType.MASTERMIND) {
            this.arrOfNbr = new Random().ints(this.secretBlockLongeur, 0, game.getNmbrUtilisable()).toArray();
            return;
        }
        this.arrOfNbr = new Random().ints(this.secretBlockLongeur, 0, 10).toArray();
    }

    public int[] getArrOfNbr() {
        return arrOfNbr;
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
        return Arrays.equals(arrOfNbr, other.arrOfNbr);
    }
}