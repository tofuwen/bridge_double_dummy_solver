package main.basic;

import java.util.ArrayList;

/**
 * Created by tofuwen on 11/6/16.
 */
public class Contract {

    private int suit = Board.NT;
    private int level = 1;
    private int special = Bid.PASS; // PASS, DOUBLE, REDOUBLE
    private int declarer = Board.NORTH;
    private boolean isAllPass = false;

    public Contract(boolean isAllPass) {
        this.isAllPass = isAllPass;
    }

    public Contract(int suit, int level, int special, int declarer) {
        this.suit = suit;
        this.level = level;
        this.special = special;
        this.declarer = declarer;
    }

    public int getSuit() {
        return this.suit;
    }

    public int getDeclarer() {
        return this.declarer;
    }

    public int getLevel() {
        return this.level;
    }

    public int getSpecial() {
        return this.special;
    }

    public boolean getIsAllPass() {
        return this.isAllPass;
    }

    /**
     * Determine the declarer given bidding information
     * @param bids list of bid
     * @param lastSuitBid the trump suit
     * @return the declarer
     */
    private static int calDeclarer(ArrayList<Bid> bids, Bid lastSuitBid) {
        int team = lastSuitBid.getPosition();
        int suit = lastSuitBid.getSuit();
        int size = bids.size();
        for (Bid bid : bids) {
            if (bid.getSuit() == suit && Board.isSameTeam(team, bid.getPosition()))
                return bid.getPosition();
        }
        assert (false); // this should never happen
        return Board.NORTH;
    }

    /**
     * Determine the contract given bidding list
     * @param bids list of bids
     * @return the contract
     */
    public static Contract calContract(ArrayList<Bid> bids) {
        Bid lastSuitBid = Bid.getLastSuitBid(bids);
        if (lastSuitBid == null) { // all the bid is pass
            return new Contract(true);
        }
        int special = Bid.getLastSpecialBid(bids);
        return new Contract(lastSuitBid.getSuit(), lastSuitBid.getLevel(), special, calDeclarer(bids, lastSuitBid));
    }

    /**
     * Return the points of this contract for the declarer team
     * @param tricks the tricks that the declarer got
     * @param isVulnerable
     * @return the points
     */
    public int getPoints(int tricks, boolean isVulnerable) {
        int difference = tricks - level - 6;
        if (difference < 0) { // down
            if (isVulnerable) {
                switch (special) {
                    case 0: return 100 * difference; // pass
                    case 1: return 300 * difference + 100; // double
                    case 2: return 600 * difference + 200; // redouble
                    default: System.out.println("You should never see this!"); System.exit(1);
                }
            } else {
                switch (special) {
                    case 0: return 50 * difference; // pass
                    case 1: return difference <= -3 ? 300 * difference + 400 : 200 * difference + 100; // double
                    case 2: return difference <= -3 ? 600 * difference + 800 : 400 * difference + 200; // redouble
                    default: System.out.println("You should never see this!"); System.exit(1);
                }
            }
        }
        boolean isMinor = suit == Board.CLUB || suit == Board.DIAMOND;
        int baseEach = isMinor ? 20 : 30;
        int base = baseEach * level;
        int doubleBonus = 0;
        int overTrickEach = baseEach;
        if (suit == Board.NT) {
            base += 10;
        }
        if (special == Bid.DOUBLE) {
            base *= 2;
            doubleBonus = 50;
            overTrickEach = isVulnerable ? 200 : 100;
        }
        if (special == Bid.REDOUBLE) {
            base *= 4;
            doubleBonus = 100;
            overTrickEach = isVulnerable ? 400 : 200;
        }
        boolean isGame = base >= 100;
        boolean isSmallSlam = level == 6;
        boolean isGrandSlam = level == 7;
        int bonus = 50;
        if (isGame) {
            bonus = isVulnerable ? 500 : 300;
        }
        if (isSmallSlam) {
            bonus += isVulnerable ? 750 : 500;
        }
        if (isGrandSlam) {
            bonus += isVulnerable ? 1500 : 1000;
        }
        int overTricks = overTrickEach * difference;
        return base + bonus + doubleBonus + overTricks;
    }
}
