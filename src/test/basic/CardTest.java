package test.basic;

import main.basic.Board;
import main.basic.Card;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tofuwen on 10/28/16.
 */
public class CardTest {

    @Test
    public void testCreateCard() {
        Card c1 = new Card(0);
        assertTrue(c1.getSuitString().equals("S"));
        assertTrue(c1.getNumber() == 2);
        Card c2 = new Card(51);
        assertTrue(c2.getSuitString().equals("C"));
        assertTrue(c2.getNumber() == 14);
        Card c3 = new Card(14);
        assertTrue(c3.getSuitString().equals("H"));
        assertTrue(c3.getNumber() == 3);
        Card c4 = new Card(Board.SPADE, 14);
        assertTrue(c4.getSuitString().equals("S"));
        assertTrue(c4.getNumber() == 14);
    }

    @Test
    public void testWinner() {
        Card c1 = new Card(0); // S2
        Card c2 = new Card(51); // CA
        Card c3 = new Card(14); // H3
        Card c4 = new Card(5); // S7
        List<Card> list = Arrays.asList(c1, c2, c3, c4);
        assertTrue(Card.winner(list, 0, 0) == 3); // start: 0; trump: spade
        assertTrue(Card.winner(list, 1, -1) == 0); // start: 1; trump: NT
        assertTrue(Card.winner(list, 2, 1) == 0); // start: 2; trump: H
    }

}