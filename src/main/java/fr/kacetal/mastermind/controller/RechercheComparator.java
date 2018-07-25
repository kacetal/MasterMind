package fr.kacetal.mastermind.controller;

import fr.kacetal.mastermind.model.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class with the methods for the game Recherche +/-
 * @author Artem
 * @see ArraysComparator
 */
public class RechercheComparator extends ArraysComparator {

    public static final Logger LOGGER = LogManager.getLogger(RechercheComparator.class.getName());

    protected final String responseToWin;

    protected int[] minAILimit;

    protected int[] maxAILimit;

    /**
     * Constructor for {@link RechercheComparator}
     * and creating the String for winner response.<p>
     * The String contains "=" * {@link Game#getSecretBlockLength()}
     */
    public RechercheComparator(Game game) {
        super(game);
        responseToWin = Stream.generate(() -> "=")
                .limit(game.getSecretBlockLength())
                .collect(Collectors.joining());

        LOGGER.info("Response to win is {}", responseToWin);
    }

    /**
     * Method parses an Array as the String {@code "=+-="} for example.
     * <p>
     * <blockqoute><pre>
     *     int -1 -> String "-"
     *     int  0 -> String "="
     *     int +1 -> String "+"
     * </pre></blockqoute>
     *
     * @param arrToParse an {@code array} containing the {@code int} to parse
     * @return the String parsed from an array
     */
    @Override
    public String parseStringFromArray(final int[] arrToParse) {
        return Arrays.stream(arrToParse)
                .sequential()
                .mapToObj(i -> i == 0 ? "=" : (i > 0 ? "+" : "-") )
                .collect(Collectors.joining());
    }

    /**
     * Method compare a String with {@link RechercheComparator#responseToWin}
     *
     * @param hint a String to compare with {@link RechercheComparator#responseToWin}
     * @return true if the String equals to {@link RechercheComparator#responseToWin}
     */
    protected boolean isWinner(String hint) {
        return hint.equals(responseToWin);
    }
}