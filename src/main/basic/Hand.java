package main.basic;

import java.util.Arrays;

/**
 * Created by tofuwen on 10/28/16.
 */
public class Hand {

    public static final int numCardsInHand = 13;

    private Card [] cards = null;
    private boolean [] isPlayed = null;

    public Hand(Card [] cards) {
        assert (cards.length == numCardsInHand);
        this.cards = new Card[numCardsInHand];
        this.isPlayed = new boolean[numCardsInHand];
        Arrays.fill(this.isPlayed, false);
        this.cards = cards.clone();
    }

    public Card getCard(int id) {
        return this.cards[id];
    }

    public boolean getIsPlayed(int id) {
        return this.isPlayed[id];
    }

    /**
     * @param suitId
     * @return true if current hand has the suit
     *         false otherwise
     */
    public boolean hasSuit(int suitId) {
        for (int i = 0; i < numCardsInHand; i++) {
            if (this.getIsPlayed(i)) {
                continue;
            }
            Card card = this.getCard(i);
            if (card.getSuit() == suitId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Play the card as it is the first card of a round
     * @param id the card id (0-12) of the card that the player want to play
     * @return true if we can play this card successfully.
     *         false otherwise.
     */
    public boolean play(int id) {
        assert (id >= 0 && id < numCardsInHand);
        if (this.isPlayed[id]) {
            return false;
        }
        return this.isPlayed[id] = true;
    }

    /**
     * Determine whether we can successfully play this card given this first card of the current round.
     * @param id the card id that wants to be played
     * @param firstInRound
     * @return true if we can successfully play this card, false otherwise
     */
    public boolean canPlay(int id, Card firstInRound) {
        assert (id >= 0 && id < numCardsInHand);
        if (this.isPlayed[id]) {
            return false;
        }
        Card beingPlayed = this.getCard(id);
        if (firstInRound == null) {
            return true;
        }
        if (firstInRound.getSuit() != beingPlayed.getSuit()) {
            if (this.hasSuit(firstInRound.getSuit())){
                return false;
            }
        }
        return true;
    }

    /**
     * Play the card as it is NOT the first card of a round
     * @param id the card id (0-12) of the card that the player want to play
     * @param firstInRound the first card of the round
     * @return true if we can play this card successfully.
     *         false otherwise.
     */
    public boolean play(int id, Card firstInRound) {
        assert (firstInRound != null);
        return this.isPlayed[id] = canPlay(id, firstInRound);
    }

    /**
     * Unplay the given card.
     * The card must be played before.
     * @param id the card id that wants to be unplayed
     */
    public void unplay(int id) {
        assert (id >= 0 && id < numCardsInHand);
        assert (this.isPlayed[id]);
        this.isPlayed[id] = false;
    }

    public void print() {
        for (int i = 0; i < numCardsInHand; ++i) {
            cards[i].print(isPlayed[i]);
        }
        System.out.println();
    }

}
