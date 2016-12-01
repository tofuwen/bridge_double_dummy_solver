package main.basic;

import java.util.ArrayList;

/**
 * Created by tofuwen on 11/6/16.
 */
public class Bid {

    // constant for special bid, better if using enumerate
    public static int PASS = 0;
    public static int DOUBLE = 1;
    public static int REDOUBLE = 2;
    public static int SPECIAL_LEVEL = 0;

    private int suit;
    private int level; // when level = SPECIAL_LEVEL, represents special bid: PASS, DOUBLE, REDOUBLE
    private int position = Board.NORTH; // East, South, West, North

    public Bid(int suit, int level) {
        assert (level >= 0 && level <= 7);
        this.suit = suit;
        this.level = level;
    }

    public Bid(int suit, int level, int position) {
        this(suit, level);
        this.position = position;
    }

    public int getLevel() {
        return this.level;
    }

    public int getSuit() {
        return this.suit;
    }

    public int getPosition() {
        return this.position;
    }

    /**
     * Special bid: PASS, DOUBLE, REDOUBLE
     * @return true if this is special bid
     */
    public boolean isSpecialBid() {
        return this.getLevel() == SPECIAL_LEVEL;
    }

    /**
     * @return true if the bid is like 1S, 2NT...
     */
    public boolean isSuitBid() {
        return this.getLevel() > 0;
    }

    /**
     * @param other
     * @return true if this bid is greater than "other" bid
     * "other" bid should be a suit bid.
     * "this" bid should also be a suit bid.
     */
    public boolean isGreaterThan(Bid other) {
        assert (other.isSuitBid());
        assert (this.isSuitBid());
        if (this.level > other.getLevel())
            return true;
        if (this.level < other.getLevel())
            return false;
        return this.suit < other.getSuit(); // In Board declaration, happens to be in inverse order
    }

    /**
     * @param bids
     * @return int representation of last special bid after last suit bid
     */
    public static int getLastSpecialBid(ArrayList<Bid> bids) {
        int size = bids.size();
        for (int i = size - 1; i >= 0; --i) {
            Bid cur = bids.get(i);
            if (cur.isSuitBid())
                return PASS;
            if (cur.isSpecialBid()) {
                if (cur.getSuit() == DOUBLE)
                    return DOUBLE;
                if (cur.getSuit() == REDOUBLE)
                    return REDOUBLE;
            }
        }
        return PASS;
    }

    /**
     * Get the last suit bid from bid list
     * @param bids
     * @return if unsuccessful, return null
     */
    public static Bid getLastSuitBid(ArrayList<Bid> bids) {
        int size = bids.size();
        for (int i = size - 1; i >= 0; --i) {
            if (bids.get(i).isSuitBid())
                return bids.get(i);
        }
        return null;
    }

    /**
     * incorporates checks when adding bid to bid list
     * currently only have simplest check: suit bid should be increasing
     * TODO: add more checks
     * @param bids
     * @param bid
     * @return true if this bid is valid, and add it to the list
     */
    public static boolean addBid(ArrayList<Bid> bids, Bid bid) {
        Bid lastSuitBid = getLastSuitBid(bids);
        if (lastSuitBid != null && bid.isSuitBid() && !bid.isGreaterThan(lastSuitBid))
            return false;
        bids.add(bid);
        return true;
    }

    public static String suitToString(int suit) {
        assert (suit >= -1 && suit <= 3);
        if (suit == Board.NT)
            return "NT";
        if (suit == Board.SPADE)
            return "S";
        if (suit == Board.HEART)
            return "H";
        if (suit == Board.DIAMOND)
            return "D";
        return "C";
    }

    public static String positionToString(int position) {
        assert (position >= 0 && position <= 3);
        if (position == Board.NORTH)
            return "NORTH";
        if (position == Board.EAST)
            return "EAST";
        if (position == Board.SOUTH)
            return "SOUTH";
        return "WEST";
    }

    public void print() {
        String repre = "";
        if (isSpecialBid()) {
            if (suit == PASS)
                repre = "PASS";
            else if (suit == DOUBLE)
                repre = "DOUBLE";
            else
                repre  = "REDOUBLE";
        } else {
            repre += level;
            repre += suitToString(suit);
        }
        System.out.println("Bid: " + repre);
        System.out.println("Position:" + positionToString(position));
        System.out.println();
    }
}
