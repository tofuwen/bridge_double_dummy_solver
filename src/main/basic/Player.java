package main.basic;

import java.util.Arrays;

/**
 * Created by tofuwen on 10/28/16.
 */
public class Player {

    private Hand hand;
    private String name;

    public Player(String name, int [] card_ids) {
        assert (card_ids.length == Hand.numCardsInHand);
        Arrays.sort(card_ids);
        for (int i = 0; i < Hand.numCardsInHand - 1; ++i) {
            assert (card_ids[i] != card_ids[i+1]);
        }
        Card [] cards = new Card[Hand.numCardsInHand];
        for (int i = 0; i< Hand.numCardsInHand; ++i) {
            cards[i] = new Card(card_ids[i]);
        }
        this.hand = new Hand(cards);
        this.name = name;
    }

    /**
     * Play the card as it is the first card of a round
     * @param id the card id (0-12) of the card that the player want to play
     * @return true if we can play this card successfully.
     *         false otherwise.
     */
    public boolean play(int id) {
        return hand.play(id);
    }

    /**
     * Play the card as it is NOT the first card of a round
     * @param id the card id (0-12) of the card that the player want to play
     * @param firstInRound the first card of the round
     * @return true if we can play this card successfully.
     *         false otherwise.
     */
    public boolean play(int id, Card firstInRound) {
        return hand.play(id, firstInRound);
    }

    public Card getCard(int id) {
        return hand.getCard(id);
    }

    public boolean getIsPlayed(int id) {
        return hand.getIsPlayed(id);
    }

    public String getName() {
        return this.name;
    }

    public void print() {
        System.out.println("name: " + this.name);
        hand.print();
        System.out.println();
    }
}
