package test.algorithm;

import main.algorithm.DoubleDummySolver;
import main.basic.Board;
import main.basic.Card;
import main.basic.Hand;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by tofuwen on 10/28/16.
 */
public class DoubleDummySolverTest {

    @Test
    /**
     * Simple test for basic.
     */
    public void testSimpleSolver1() {
        Card[] cards = new Card[Hand.numCardsInHand];
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            cards[i] = new Card(i);
        }
        Hand [] hands = new Hand[4];
        hands[0] = new Hand(cards);
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            cards[i] = new Card(i + 13);
        }
        hands[1] = new Hand(cards);
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            cards[i] = new Card(i + 26);
        }
        hands[2] = new Hand(cards);
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            cards[i] = new Card(i + 39);
        }
        hands[3] = new Hand(cards);
        assertTrue(DoubleDummySolver.solve(hands, Hand.numCardsInHand, Board.SPADE, Board.WEST));
        assertTrue(DoubleDummySolver.bestPossible(hands, Board.SPADE, Board.WEST) == Hand.numCardsInHand);
    }

    @Test
    /**
     * This test is only valid when Hand.numCardsInHand = 3.
     * Test Finesse without bridge to finesse again
     */
    public void testSimpleSolver2() {
        if (Hand.numCardsInHand != 3) {
            System.out.println("Invalid number of cards in Hand.");
            return;
        }
        Hand [] hands = new Hand[4];
        // SA SQ SJ
        Card[] cardsNorth = new Card[Hand.numCardsInHand];
        cardsNorth[0] = new Card(Board.SPADE, 14);
        cardsNorth[1] = new Card(Board.SPADE, 12);
        cardsNorth[2] = new Card(Board.SPADE, 11);
        hands[Board.NORTH] = new Hand(cardsNorth);
        // S9 S8 S7
        Card[] cardsEast = new Card[Hand.numCardsInHand];
        cardsEast[0] = new Card(Board.SPADE, 7);
        cardsEast[1] = new Card(Board.SPADE, 8);
        cardsEast[2] = new Card(Board.SPADE, 9);
        hands[Board.EAST] = new Hand(cardsEast);
        // S6 S5 S4
        Card[] cardsSouth = new Card[Hand.numCardsInHand];
        cardsSouth[0] = new Card(Board.SPADE, 4);
        cardsSouth[1] = new Card(Board.SPADE, 5);
        cardsSouth[2] = new Card(Board.SPADE, 6);
        hands[Board.SOUTH] = new Hand(cardsSouth);
        // SK ST S3
        Card[] cardsWest = new Card[Hand.numCardsInHand];
        cardsWest[0] = new Card(Board.SPADE, 13);
        cardsWest[1] = new Card(Board.SPADE, 3);
        cardsWest[2] = new Card(Board.SPADE, 10);
        hands[Board.WEST] = new Hand(cardsWest);
        for (int i = 0; i < Board.numPlayers; ++i) {
            hands[i].print();
        }
        assertFalse(DoubleDummySolver.solve(hands, 3, Board.SPADE, Board.WEST));
        assertTrue(DoubleDummySolver.solve(hands, 2, Board.SPADE, Board.WEST));
        assertTrue(DoubleDummySolver.bestPossible(hands, Board.SPADE, Board.WEST) == 2);
    }

    @Test
    /**
     * This test is only valid when Hand.numCardsInHand = 4.
     * Test Finesse with bridge that can be forced in the first trick
     */
    public void testSimpleSolver3() {
        if (Hand.numCardsInHand != 4) {
            System.out.println("Invalid number of cards in Hand.");
            return;
        }
        Hand [] hands = new Hand[4];
        // SA SQ SJ C2
        Card[] cardsNorth = new Card[Hand.numCardsInHand];
        cardsNorth[0] = new Card(Board.SPADE, 14);
        cardsNorth[1] = new Card(Board.SPADE, 12);
        cardsNorth[2] = new Card(Board.SPADE, 11);
        cardsNorth[3] = new Card(Board.CLUB, 2);
        hands[Board.NORTH] = new Hand(cardsNorth);
        // S9 S8 S7 C5
        Card[] cardsEast = new Card[Hand.numCardsInHand];
        cardsEast[0] = new Card(Board.SPADE, 7);
        cardsEast[1] = new Card(Board.SPADE, 8);
        cardsEast[2] = new Card(Board.SPADE, 9);
        cardsEast[3] = new Card(Board.CLUB, 5);
        hands[Board.EAST] = new Hand(cardsEast);
        // S6 S5 S4 CA
        Card[] cardsSouth = new Card[Hand.numCardsInHand];
        cardsSouth[0] = new Card(Board.SPADE, 4);
        cardsSouth[1] = new Card(Board.SPADE, 5);
        cardsSouth[2] = new Card(Board.SPADE, 6);
        cardsSouth[3] = new Card(Board.CLUB, 14);
        hands[Board.SOUTH] = new Hand(cardsSouth);
        // SK ST S3 C3
        Card[] cardsWest = new Card[Hand.numCardsInHand];
        cardsWest[0] = new Card(Board.SPADE, 13);
        cardsWest[1] = new Card(Board.SPADE, 3);
        cardsWest[2] = new Card(Board.SPADE, 10);
        cardsWest[3] = new Card(Board.CLUB, 3);
        hands[Board.WEST] = new Hand(cardsWest);
        for (int i = 0; i < Board.numPlayers; ++i) {
            hands[i].print();
        }
        assertFalse(DoubleDummySolver.solve(hands, 4, Board.SPADE, Board.WEST));
        assertTrue(DoubleDummySolver.solve(hands, 3, Board.SPADE, Board.WEST));
        assertTrue(DoubleDummySolver.bestPossible(hands, Board.SPADE, Board.WEST) == 3);
    }

    @Test
    /**
     * This test is only valid when Hand.numCardsInHand = 5.
     * Test Finesse with bridge
     */
    public void testSimpleSolver4() {
        if (Hand.numCardsInHand != 5) {
            System.out.println("Invalid number of cards in Hand.");
            return;
        }
        Hand [] hands = new Hand[4];
        // SA SQ SJ C2 C4
        Card[] cardsNorth = new Card[Hand.numCardsInHand];
        cardsNorth[0] = new Card(Board.SPADE, 14);
        cardsNorth[1] = new Card(Board.SPADE, 12);
        cardsNorth[2] = new Card(Board.SPADE, 11);
        cardsNorth[3] = new Card(Board.CLUB, 2);
        cardsNorth[4] = new Card(Board.CLUB, 4);
        hands[Board.NORTH] = new Hand(cardsNorth);
        // S9 S8 S7 C5 C6
        Card[] cardsEast = new Card[Hand.numCardsInHand];
        cardsEast[0] = new Card(Board.SPADE, 7);
        cardsEast[1] = new Card(Board.SPADE, 8);
        cardsEast[2] = new Card(Board.SPADE, 9);
        cardsEast[3] = new Card(Board.CLUB, 5);
        cardsEast[4] = new Card(Board.CLUB, 6);
        hands[Board.EAST] = new Hand(cardsEast);
        // S6 S5 S4 CA CK
        Card[] cardsSouth = new Card[Hand.numCardsInHand];
        cardsSouth[0] = new Card(Board.SPADE, 4);
        cardsSouth[1] = new Card(Board.SPADE, 5);
        cardsSouth[2] = new Card(Board.SPADE, 6);
        cardsSouth[3] = new Card(Board.CLUB, 14);
        cardsSouth[4] = new Card(Board.CLUB, 13);
        hands[Board.SOUTH] = new Hand(cardsSouth);
        // SK ST S3 C3 CJ
        Card[] cardsWest = new Card[Hand.numCardsInHand];
        cardsWest[0] = new Card(Board.SPADE, 13);
        cardsWest[1] = new Card(Board.SPADE, 3);
        cardsWest[2] = new Card(Board.SPADE, 10);
        cardsWest[3] = new Card(Board.CLUB, 3);
        cardsWest[4] = new Card(Board.CLUB, 11);
        hands[Board.WEST] = new Hand(cardsWest);
        for (int i = 0; i < Board.numPlayers; ++i) {
            hands[i].print();
        }
        assertTrue(DoubleDummySolver.solve(hands, 5, Board.SPADE, Board.WEST));
        assertTrue(DoubleDummySolver.bestPossible(hands, Board.SPADE, Board.WEST) == 5);
    }

    @Test
    /**
     * This test is only valid when Hand.numCardsInHand = 4.
     * Test squeeze
     */
    public void testSimpleSqueeze() {
        if (Hand.numCardsInHand != 4) {
            System.out.println("Invalid number of cards in Hand.");
            return;
        }
        Hand [] hands = new Hand[4];
        // SA S2 H2 CA
        Card[] cardsNorth = new Card[Hand.numCardsInHand];
        cardsNorth[0] = new Card(Board.SPADE, 14);
        cardsNorth[1] = new Card(Board.SPADE, 2);
        cardsNorth[2] = new Card(Board.HEART, 2);
        cardsNorth[3] = new Card(Board.CLUB, 14);
        hands[Board.NORTH] = new Hand(cardsNorth);
        // S9 S8 H7 H6
        Card[] cardsEast = new Card[Hand.numCardsInHand];
        cardsEast[0] = new Card(Board.HEART, 7);
        cardsEast[1] = new Card(Board.SPADE, 8);
        cardsEast[2] = new Card(Board.SPADE, 9);
        cardsEast[3] = new Card(Board.HEART, 6);
        hands[Board.EAST] = new Hand(cardsEast);
        // S3 HA H3 C4
        Card[] cardsSouth = new Card[Hand.numCardsInHand];
        cardsSouth[0] = new Card(Board.SPADE, 3);
        cardsSouth[1] = new Card(Board.HEART, 14);
        cardsSouth[2] = new Card(Board.HEART, 3);
        cardsSouth[3] = new Card(Board.CLUB, 4);
        hands[Board.SOUTH] = new Hand(cardsSouth);
        // S5 H5 C6 C5
        Card[] cardsWest = new Card[Hand.numCardsInHand];
        cardsWest[0] = new Card(Board.SPADE, 5);
        cardsWest[1] = new Card(Board.HEART, 3);
        cardsWest[2] = new Card(Board.CLUB, 6);
        cardsWest[3] = new Card(Board.CLUB, 5);
        hands[Board.WEST] = new Hand(cardsWest);
        for (int i = 0; i < Board.numPlayers; ++i) {
            hands[i].print();
        }
        assertTrue(DoubleDummySolver.solve(hands, 4, Board.NT, Board.SOUTH));
        assertTrue(DoubleDummySolver.bestPossible(hands, Board.NT, Board.SOUTH) == 4);
    }

    @Test
    /**
     * This test is only valid when Hand.numCardsInHand = 4.
     * Test ruffing finesse
     */
    public void testRuffingFinesse() {
        if (Hand.numCardsInHand != 4) {
            System.out.println("Invalid number of cards in Hand.");
            return;
        }
        Hand [] hands = new Hand[4];
        // SA SQ SJ HA
        Card[] cardsNorth = new Card[Hand.numCardsInHand];
        cardsNorth[0] = new Card(Board.SPADE, 14);
        cardsNorth[1] = new Card(Board.SPADE, 12);
        cardsNorth[2] = new Card(Board.SPADE, 11);
        cardsNorth[3] = new Card(Board.HEART, 14);
        hands[Board.NORTH] = new Hand(cardsNorth);
        // SK S8 S7 H6
        Card[] cardsEast = new Card[Hand.numCardsInHand];
        cardsEast[0] = new Card(Board.SPADE, 7);
        cardsEast[1] = new Card(Board.SPADE, 8);
        cardsEast[2] = new Card(Board.SPADE, 13);
        cardsEast[3] = new Card(Board.HEART, 6);
        hands[Board.EAST] = new Hand(cardsEast);
        // S3 H4 H5 C4
        Card[] cardsSouth = new Card[Hand.numCardsInHand];
        cardsSouth[0] = new Card(Board.SPADE, 3);
        cardsSouth[1] = new Card(Board.HEART, 4);
        cardsSouth[2] = new Card(Board.HEART, 5);
        cardsSouth[3] = new Card(Board.CLUB, 4);
        hands[Board.SOUTH] = new Hand(cardsSouth);
        // D2 D3 D4 D5
        Card[] cardsWest = new Card[Hand.numCardsInHand];
        cardsWest[0] = new Card(Board.DIAMOND, 5);
        cardsWest[1] = new Card(Board.DIAMOND, 3);
        cardsWest[2] = new Card(Board.DIAMOND, 2);
        cardsWest[3] = new Card(Board.DIAMOND, 4);
        hands[Board.WEST] = new Hand(cardsWest);
        for (int i = 0; i < Board.numPlayers; ++i) {
            hands[i].print();
        }
        assertTrue(DoubleDummySolver.solve(hands, 4, Board.CLUB, Board.SOUTH));
        assertTrue(DoubleDummySolver.bestPossible(hands, Board.CLUB, Board.SOUTH) == 4);
    }

    @Test
    public void testRealHand() {
        // The declarer can make the 7H when WEST lead spade,
        // but if the WEST lead perfectly, he can only make 6H, as indicated by the solver.
        if (Hand.numCardsInHand != 13) {
            System.out.println("Invalid number of cards in Hand.");
            return;
        }
        Hand [] hands = new Hand[4];
        // S AK76543
        // H 32
        // D AK2
        // C 3
        Card[] cardsNorth = new Card[Hand.numCardsInHand];
        cardsNorth[0] = new Card(Board.SPADE, 14);
        cardsNorth[1] = new Card(Board.SPADE, 13);
        cardsNorth[2] = new Card(Board.SPADE, 7);
        cardsNorth[3] = new Card(Board.SPADE, 6);
        cardsNorth[4] = new Card(Board.SPADE, 5);
        cardsNorth[5] = new Card(Board.SPADE, 4);
        cardsNorth[6] = new Card(Board.SPADE, 3);
        cardsNorth[7] = new Card(Board.HEART, 3);
        cardsNorth[8] = new Card(Board.HEART, 2);
        cardsNorth[9] = new Card(Board.DIAMOND, 14);
        cardsNorth[10] = new Card(Board.DIAMOND, 13);
        cardsNorth[11] = new Card(Board.DIAMOND, 2);
        cardsNorth[12] = new Card(Board.CLUB, 3);
        hands[Board.NORTH] = new Hand(cardsNorth);
        // S 2
        // H QT87
        // D 876
        // C 87654
        Card[] cardsEast = new Card[Hand.numCardsInHand];
        cardsEast[0] = new Card(Board.SPADE, 2);
        cardsEast[1] = new Card(Board.HEART, 12);
        cardsEast[2] = new Card(Board.HEART, 10);
        cardsEast[3] = new Card(Board.HEART, 8);
        cardsEast[4] = new Card(Board.HEART, 7);
        cardsEast[5] = new Card(Board.DIAMOND, 8);
        cardsEast[6] = new Card(Board.DIAMOND, 7);
        cardsEast[7] = new Card(Board.DIAMOND, 6);
        cardsEast[8] = new Card(Board.CLUB, 8);
        cardsEast[9] = new Card(Board.CLUB, 7);
        cardsEast[10] = new Card(Board.CLUB, 6);
        cardsEast[11] = new Card(Board.CLUB, 5);
        cardsEast[12] = new Card(Board.CLUB, 4);
        hands[Board.EAST] = new Hand(cardsEast);
        // S ---
        // H AKJ965
        // D 543
        // C AQJ9
        Card[] cardsSouth = new Card[Hand.numCardsInHand];
        cardsSouth[0] = new Card(Board.HEART, 14);
        cardsSouth[1] = new Card(Board.HEART, 13);
        cardsSouth[2] = new Card(Board.HEART, 11);
        cardsSouth[3] = new Card(Board.HEART, 9);
        cardsSouth[4] = new Card(Board.HEART, 6);
        cardsSouth[5] = new Card(Board.HEART, 5);
        cardsSouth[6] = new Card(Board.DIAMOND, 5);
        cardsSouth[7] = new Card(Board.DIAMOND, 4);
        cardsSouth[8] = new Card(Board.DIAMOND, 3);
        cardsSouth[9] = new Card(Board.CLUB, 14);
        cardsSouth[10] = new Card(Board.CLUB, 12);
        cardsSouth[11] = new Card(Board.CLUB, 11);
        cardsSouth[12] = new Card(Board.CLUB, 9);
        hands[Board.SOUTH] = new Hand(cardsSouth);
        // S QJT98
        // H 4
        // D QJT9
        // C KT2
        Card[] cardsWest = new Card[Hand.numCardsInHand];
        cardsWest[0] = new Card(Board.SPADE, 12);
        cardsWest[1] = new Card(Board.SPADE, 11);
        cardsWest[2] = new Card(Board.SPADE, 10);
        cardsWest[3] = new Card(Board.SPADE, 9);
        cardsWest[4] = new Card(Board.SPADE, 8);
        cardsWest[5] = new Card(Board.HEART, 4);
        cardsWest[6] = new Card(Board.DIAMOND, 12);
        cardsWest[7] = new Card(Board.DIAMOND, 11);
        cardsWest[8] = new Card(Board.DIAMOND, 10);
        cardsWest[9] = new Card(Board.DIAMOND, 9);
        cardsWest[10] = new Card(Board.CLUB, 13);
        cardsWest[11] = new Card(Board.CLUB, 10);
        cardsWest[12] = new Card(Board.CLUB, 2);
        hands[Board.WEST] = new Hand(cardsWest);
        for (int i = 0; i < Board.numPlayers; ++i) {
            hands[i].print();
        }
        assertTrue(DoubleDummySolver.bestPossible(hands, Board.HEART, Board.WEST) == 12);
        // assertTrue(DoubleDummySolver.solve(hands, 12, Board.HEART, Board.WEST));
        // Only true when leading SPADE. We did a little hack in the code. See "solve" in DoubleDummySolver.
        // assertTrue(DoubleDummySolver.solve(hands, 13, Board.HEART, Board.WEST));
        // assertTrue(DoubleDummySolver.bestPossible(hands, Board.HEART, Board.WEST) == 13);
    }

}