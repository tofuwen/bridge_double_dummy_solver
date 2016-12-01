package test.basic;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIDeclaration;
import main.basic.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by tofuwen on 10/28/16.
 */
public class BoardTest {

    @Test
    public void testCreateBoard() {
        Board board = new Board();
        board.print();
        boolean [] isUsed = new boolean[Board.numCards];
        Arrays.fill(isUsed, false);
        for (int i = 0; i < Board.numPlayers; ++i) {
            for (int j = 0; j < Hand.numCardsInHand; ++j) {
                int cardId = board.getPlayer(i).getCard(j).getId();
                isUsed[cardId] = true;
            }
        }
        for (int i = 0; i< Board.numPlayers; ++i) {
            assertTrue(isUsed[i]);
        }
    }

    @Test
    public void testCreateBoardGivenCards() {
        int [][] cards = {
                {1, 38, 23, 9, 31, 0, 27, 12, 25, 22, 26, 24, 28},
                {11, 2, 39, 4, 8, 40, 42, 48, 15, 10, 20, 16, 33},
                {18, 46, 45, 14, 3, 21, 35, 19, 13, 6, 50, 29, 47},
                {34, 49, 7, 43, 41, 30, 51, 5, 17, 36, 32, 37, 44},
        };
        Board board = new Board(cards, Board.NT);
        board.print();
        boolean [] isUsed = new boolean[Board.numCards];
        Arrays.fill(isUsed, false);
        for (int i = 0; i < Board.numPlayers; ++i) {
            for (int j = 0; j < Hand.numCardsInHand; ++j) {
                int cardId = board.getPlayer(i).getCard(j).getId();
                isUsed[cardId] = true;
            }
        }
        for (int i = 0; i< Board.numPlayers; ++i) {
            assertTrue(isUsed[i]);
        }
    }

    @Test
    public void testPlay() {
        int [][] cards = {
                {1, 38, 23, 9, 31, 0, 27, 12, 25, 22, 26, 24, 28},
                {11, 2, 39, 4, 8, 40, 42, 48, 15, 10, 20, 16, 33},
                {18, 46, 45, 14, 3, 21, 35, 19, 13, 6, 50, 29, 47},
                {34, 49, 7, 43, 41, 30, 51, 5, 17, 36, 32, 37, 44},
        };
        Board board = new Board(cards, Board.NT);
        for (int i = 0; i < Board.numPlayers; i++) {
            assertTrue(board.play(0));
        }
        assertTrue(board.getTurn() == 3);
        assertTrue(board.getTricksEastWest() == 1);
        assertTrue(board.getTricksNorthSouth() == 0);
        for (int i = 0; i < Board.numPlayers; i++) {
            assertTrue(board.play(12 - i));
        }
        assertTrue(board.getTurn() == 3);
        assertTrue(board.getTricksEastWest() == 2);
        assertTrue(board.getTricksNorthSouth() == 0);
        board.print();
    }

    @Test
    public void testCreateBoardWithBoardNumber() {
        Board board = new Board(Board.DIAMOND, 17);
        assertTrue(board.getBoardNumber() == 17);
        assertTrue(board.getDealer() == Board.NORTH);
        assertTrue(board.getVulnerability() == Board.NONE);

        board = new Board(Board.DIAMOND, 22);
        assertTrue(board.getBoardNumber() == 22);
        assertTrue(board.getDealer() == Board.EAST);
        assertTrue(board.getVulnerability() == Board.East_West);

        board = new Board(Board.DIAMOND, 31);
        assertTrue(board.getBoardNumber() == 31);
        assertTrue(board.getDealer() == Board.SOUTH);
        assertTrue(board.getVulnerability() == Board.North_South);

        board = new Board(Board.DIAMOND, 12);
        assertTrue(board.getBoardNumber() == 12);
        assertTrue(board.getDealer() == Board.WEST);
        assertTrue(board.getVulnerability() == Board.North_South);
    }

    @Test
    public void testDoBidding() {
        Board board = new Board(Board.DIAMOND, 17);
        ArrayList<Bid> bids = new ArrayList<Bid>();
        Bid bid = new Bid(Board.SPADE, 1);
        Bid other1 = new Bid(Board.HEART, 3);
        Bid notOkay = new Bid(Board.CLUB, 2);
        Bid other2 = new Bid(Board.CLUB, 4);
        Bid pass = new Bid(Bid.PASS, Bid.SPECIAL_LEVEL);
        Bid Double = new Bid(Bid.DOUBLE, Bid.SPECIAL_LEVEL);
        assertTrue(Bid.addBid(bids, pass));
        assertTrue(Bid.addBid(bids, bid));
        assertTrue(Bid.addBid(bids, Double));
        assertTrue(Bid.addBid(bids, other1));
        assertFalse(Bid.addBid(bids, notOkay));
        assertTrue(Bid.addBid(bids, pass));
        assertTrue(Bid.addBid(bids, other2));
        assertTrue(Bid.addBid(bids, pass));
        assertTrue(Bid.addBid(bids, pass));
        assertTrue(Bid.addBid(bids, pass));
        board.doBidding(bids);
        ArrayList<Bid> bidList = board.getBidList();
        int size = bidList.size();
        for (int i = 0; i < size; ++i) {
            // Here, the position of bidding should be correct.
            bidList.get(i).print();
        }
        Contract contract = board.getContract();
        assertTrue(contract.getDeclarer() == Board.EAST);
        assertTrue(contract.getLevel() == 4);
        assertTrue(contract.getSuit() == Board.CLUB);
        assertTrue(contract.getSpecial() == Bid.PASS);
        assertTrue(board.getLead() == Board.SOUTH);
        assertTrue(board.getTrumpSuit() == Board.CLUB);
    }

    @Test
    public void testDoBidding2() {
        Board board = new Board(Board.DIAMOND, 17);
        ArrayList<Bid> bids = new ArrayList<Bid>();
        Bid bid = new Bid(Board.SPADE, 1);
        Bid other1 = new Bid(Board.CLUB, 3);
        Bid notOkay = new Bid(Board.CLUB, 2);
        Bid other2 = new Bid(Board.CLUB, 4);
        Bid pass = new Bid(Bid.PASS, Bid.SPECIAL_LEVEL);
        Bid Double = new Bid(Bid.DOUBLE, Bid.SPECIAL_LEVEL);
        assertTrue(Bid.addBid(bids, pass));
        assertTrue(Bid.addBid(bids, bid));
        assertTrue(Bid.addBid(bids, Double));
        assertTrue(Bid.addBid(bids, other1));
        assertFalse(Bid.addBid(bids, notOkay));
        assertTrue(Bid.addBid(bids, pass));
        assertTrue(Bid.addBid(bids, other2));
        assertTrue(Bid.addBid(bids, Double));
        assertTrue(Bid.addBid(bids, pass));
        assertTrue(Bid.addBid(bids, pass));
        assertTrue(Bid.addBid(bids, pass));
        board.doBidding(bids);
        ArrayList<Bid> bidList = board.getBidList();
        int size = bidList.size();
        for (int i = 0; i < size; ++i) {
            // Here, the position of bidding should be correct.
            bidList.get(i).print();
        }
        Contract contract = board.getContract();
        assertTrue(contract.getDeclarer() == Board.WEST);
        assertTrue(contract.getLevel() == 4);
        assertTrue(contract.getSuit() == Board.CLUB);
        assertTrue(contract.getSpecial() == Bid.DOUBLE);
        assertTrue(board.getLead() == Board.NORTH);
        assertTrue(board.getTrumpSuit() == Board.CLUB);
    }
}