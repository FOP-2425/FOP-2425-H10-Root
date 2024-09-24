package h10;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CardGame {
    private DoublyLinkedList<CardGamePlayer> players;
    private DoublyLinkedList<PlayingCard> cardDeck;

    public CardGame() {
        players = new DoublyLinkedList<>();
        cardDeck = new DoublyLinkedList<>();
    }

    public void addPlayer(CardGamePlayer player) {
        players.add(player);
    }

    public static CardGame generateRandomGame() {
        CardGame game = new CardGame();

        // Create card deck with 100 random cards
        PlayingCard[] cards = PlayingCard.values();
        game.cardDeck = new DoublyLinkedList<>();
        for (int i = 0; i < 100; i++) {
            game.cardDeck.add(cards[(int)(Math.random() * 4)]);
        }

        // 4 players with 5 cards each
        for ( int i = 1; i < 5; i++) {
            CardGamePlayer player = new CardGamePlayer("Player " + i);
            game.addPlayer(player);
            for (int j = 0; j < 5; j++) {
                player.takeCard(game.cardDeck.removeAtIndex(0));
            }
        }

        return game;
    }

    /**
     * Determines the loser of the game
     * @return the last player who still has cards in their hand
     */
    @StudentImplementationRequired
    public CardGamePlayer determineLoser() {
        DoublyLinkedList<CardGamePlayer>.CyclicIterator iter = players.cyclicIterator();

        boolean reverseDirection = false;
        boolean skipNextPlayer = false;
        int takeCards = 0;
        CardGamePlayer currentPlayer;

        while(players.size() > 1) {
            currentPlayer = reverseDirection ? iter.previous() : iter.next();

            if(skipNextPlayer) {
                skipNextPlayer = false;
                continue;
            }

            // the players are dumb and just play the next card in their hand
            PlayingCard currentCard = currentPlayer.playNextCard();

            if(PlayingCard.DRAW_TWO.equals(currentCard)) {
                takeCards += 2;
            }
            if (PlayingCard.SKIP.equals(currentCard)) { // Skip next player
                skipNextPlayer = true;
            }
            if (PlayingCard.REVERSE.equals(currentCard)) { // Change of direction
                reverseDirection = !reverseDirection;
            }

            if(takeCards > 0) {
                for (int i = 0; i < takeCards; i++) {
                    currentPlayer.takeCard(cardDeck.removeAtIndex(0));
                }
                takeCards = 0;
            }

            if (currentPlayer.getHandSize() == 0) { // This player won and is out of the game
                iter.remove();
            }
        }

        return players.get(0);
    }
}
