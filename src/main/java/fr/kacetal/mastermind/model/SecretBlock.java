package fr.kacetal.mastermind.model;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class wrapper stores an array of numbers.
 *
 * @author Artem
 */
public class SecretBlock {

    //public static final Logger LOGGER = LogManager.getLogger(SecretBlock.class.getName());

    private final int secretBlockLength;

    private final int[] arrOfNbr;

    public SecretBlock() {
        this.secretBlockLength = new Random().nextInt(10);
        this.arrOfNbr = new Random().ints(this.secretBlockLength, 0, 10).toArray();
    }

    public SecretBlock(final int[] arrOfNbr) {
        this.secretBlockLength = arrOfNbr.length;
        this.arrOfNbr = arrOfNbr.clone();
    }

    public SecretBlock(final Game game) {
        this.secretBlockLength = game.getSecretBlockLength();
        this.arrOfNbr = new Random().ints(this.secretBlockLength, 0, 10).toArray();
    }

    public SecretBlock(final Game game, final GameType type) {
        this.secretBlockLength = game.getSecretBlockLength();
        if (type == GameType.MASTERMIND) {
            this.arrOfNbr = new Random().ints(this.secretBlockLength, 0, game.getNmbrUtilisable()).toArray();
            return;
        }
        this.arrOfNbr = new Random().ints(this.secretBlockLength, 0, 10).toArray();
    }

    public int[] getArrOfNbr() {
        return arrOfNbr;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Arrays.stream(arrOfNbr).mapToObj(String::valueOf).collect(Collectors.joining());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + secretBlockLength;
        result = prime * result + Arrays.hashCode(arrOfNbr);
        return result;
    }

    /**
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
        if (secretBlockLength != other.secretBlockLength) {
            return false;
        }
        return Arrays.equals(arrOfNbr, other.arrOfNbr);
    }
}