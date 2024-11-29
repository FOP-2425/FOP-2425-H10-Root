package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a playing card in the card game.
 *
 * <p>The card can be one of the following:
 *
 * <ul>
 *     <li>PASS: no special action</li>
 *     <li>SKIP: the next player is skipped</li>
 *     <li>REVERSE: the direction of play is reversed</li>
 *     <li>DRAW_TWO: the next player draws two cards, unless they play a DRAW_TWO card themselves</li>
 * </ul>
 */
@DoNotTouch
public enum PlayingCard {

    /**
     * Represents a card that does nothing special.
     */
    PASS,

    /**
     * Represents a card that skips the next player.
     */
    SKIP,

    /**
     * Represents a card that reverses the direction of play.
     */
    REVERSE,

    /**
     * Represents a card that makes the next player draw two cards.
     */
    DRAW_TWO
}
