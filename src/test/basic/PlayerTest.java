package test.basic;

import main.basic.Board;
import main.basic.Card;
import main.basic.Hand;
import main.basic.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tofuwen on 10/28/16.
 */
public class PlayerTest {

    @Test
    public void testCreatePlayer() {
        int [] card_ids = new int[Hand.numCardsInHand];
        for (int i = 13; i < Hand.numCardsInHand + 13; ++i) {
            card_ids[i - 13] = i;
        }
        Player player = new Player("tofu", card_ids);
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            Card card = player.getCard(i);
            assertTrue(card.getSuit() == Board.HEART);
            assertTrue(card.getNumber() == i+2);
            assertTrue(player.getIsPlayed(i) == false);
        }
        assertTrue(player.play(0));
        assertTrue(player.getIsPlayed(0));
        assertFalse(player.play(0));
        player.print();
    }

    @Test
    public void testPlay() {
        int [] card_ids = new int[Hand.numCardsInHand];
        for (int i = 13; i < Hand.numCardsInHand + 13; ++i) {
            card_ids[i - 13] = i;
        }
        card_ids[Hand.numCardsInHand - 1] = Board.numCards - 1;
        Player player = new Player("tofu", card_ids);
        Card C2 = new Card(Board.CLUB, 2);
        assertTrue(player.play(0));
        assertFalse(player.play(0, C2));
        assertFalse(player.play(1, C2));
        assertTrue(player.play(Hand.numCardsInHand - 1, C2));
        player.print();
    }

}