package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a playing card in the card game
 * The card can be one of the following:
 * - PASS: no special action
 * - SKIP: the next player is skipped
 * - REVERSE: the direction of play is reversed
 * - DRAW_TWO: the next player draws two cards, unless they have a DRAW_TWO card themselves
 */
@DoNotTouch
public enum PlayingCard {
    PASS,
    SKIP,
    REVERSE,
    DRAW_TWO
}
