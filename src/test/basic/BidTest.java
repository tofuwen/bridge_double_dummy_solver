package test.basic;

import main.basic.Bid;
import main.basic.Board;
import main.basic.Hand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by tofuwen on 11/6/16.
 */
public class BidTest {
    @Test
    public void testGreater() {
        Bid bid = new Bid(Board.SPADE, 3, Board.NORTH);
        Bid other1 = new Bid(Board.HEART, 3, Board.SOUTH);
        Bid other2 = new Bid(Board.CLUB, 4, Board.SOUTH);
        assertTrue(bid.isGreaterThan(other1));
        assertFalse(bid.isGreaterThan(other2));
    }

    @Test
    public void testAddBid() {
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
        int size = bids.size();
        for (int i = 0; i < size; ++i) {
            // Here, it's okay for the position is not correct.
            bids.get(i).print();
        }
    }
}
