package test.basic;

import main.basic.Card;
import main.basic.Hand;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tofuwen on 10/28/16.
 */
public class HandTest {

    @Test
    public void testCreateHand() {
        Card [] cards = new Card[Hand.numCardsInHand];
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            cards[i] = new Card(i);
        }
        Hand hand = new Hand(cards);
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            Card card = hand.getCard(i);
            assert (card.getSuit() == 0);
            assert (card.getNumber() == i+2);
            assert (hand.getIsPlayed(i) == false);
        }
        assertTrue(hand.play(0));
        assert (hand.getIsPlayed(0) == true);
        assertFalse(hand.play(0));
    }

}