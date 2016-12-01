package main.algorithm;

import main.basic.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tofuwen on 10/28/16.
 */
public class DoubleDummySolver {

    /**
     * Determine whether the given card represented by its id is in hand or has been played
     * @param hand
     * @param id the card id
     * @return true if the card is in hand or has been played, false otherwise
     */
    private static boolean isInHandOrPlayed(Hand hand, int id, boolean cardPlayed[]) {
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            Card card = hand.getCard(i);
            if (card.getId() == id) {
                return true;
            }
        }
        return cardPlayed[id];
    }

    /**
     * Given two cards, determine whether there are conceptually same given cards that already been played.
     * @param hand
     * @param c1
     * @param c2
     * @param cardPlayed a 52-element array. cardPlayed[id] == "true" means the card with this id has been played
     * @return true if the two cards are equivalent, false otherwise.
     */
    private static boolean isEquivalent(Hand hand, Card c1, Card c2, boolean cardPlayed[]) {
        if (c1.getSuit() != c2.getSuit()) {
            return false;
        }
        int minId = Math.min(c1.getId(), c2.getId());
        int maxId = Math.max(c1.getId(), c2.getId());
        for (int i = minId + 1; i < maxId; ++i) {
            if (!isInHandOrPlayed(hand, i, cardPlayed)) {
                return false;
            }
        }
        return true;
    }

    /**
     * return all valid moves represented by id.
     * @param hand
     * @param firstCardInRound null means this is the first card in round
     * @return a list contains all valid ids represented by id
     */
    private static List<Integer> generateMoves(Hand hand, Card firstCardInRound, boolean cardPlayed[]) {
        List<Integer> answer = new ArrayList<Integer>();
        for (int i = 0; i < Hand.numCardsInHand; ++i) {
            if (hand.canPlay(i, firstCardInRound)) {
                int size = answer.size();
                if (size >= 1) {
                    int lastCardIdOfHand = answer.get(size - 1);
                    Card last = hand.getCard(lastCardIdOfHand);
                    Card cur = hand.getCard(i);
                    if (isEquivalent(hand, cur, last, cardPlayed)) {
                        continue;
                    }
                }
                answer.add(i);
            }
        }
        return answer;
    }

    private static List<Card> listClone(List<Card> original) {
        return new ArrayList<Card>(original);
    }

    /**
     * Determine whether targetTricks can be taken by North / South for double dummy.
     * Wrapper for solve
     * @param hands
     * @param targetTricks
     * @param trump
     * @param lead
     * @returntrue if can; false otherwise
     */
    public static boolean solve(Hand hands[], int targetTricks, int trump, int lead) {
        return solve(hands, 0, targetTricks, trump, lead, Board.numPlayers * Hand.numCardsInHand,
                new ArrayList<Card>(), lead, null, new boolean[Board.numCards]);
    }

    /**
     * Determine whether targetTricks can be taken by North / South for double dummy.
     * @param hands
     * @param takenTricks tricks that already taken for current depth
     * @param targetTricks
     * @param trump
     * @param turn the player id for current move
     * @param depth recursion parameter, determine the base case
     * @param currentRound cards already been played for current round
     * @param startPlayerIdCurrentRound
     * @param firstCardInRound
     * @param cardPlayed a 52-element array. cardPlayed[id] == "true" means the card with this id has been played
     * @return true if can; false otherwise
     */
    public static boolean solve(Hand hands[],
                                int takenTricks,
                                int targetTricks,
                                int trump,
                                int turn,
                                int depth,
                                List<Card> currentRound,
                                int startPlayerIdCurrentRound,
                                Card firstCardInRound,
                                boolean cardPlayed[]) {

        assert (hands.length == Board.numPlayers);
        assert (depth >= 0);
        if (depth % 4 == 0) {
            int remainTricks = depth / 4;
            if (takenTricks >= targetTricks) {
                return true;
            }
            if (remainTricks + takenTricks < targetTricks) {
                return false;
            }
        }
        List<Integer> moves = generateMoves(hands[turn], firstCardInRound, cardPlayed);
        // Hack for the last test case (testRealHand).
        // The declarer can make the 7H when WEST lead spade,
        // but if the WEST lead perfectly, he can only make 6H, as indicated by the solver.
        // if (depth == Board.numPlayers * Hand.numCardsInHand) {
        //     moves = new ArrayList<Integer>(0);
        // }
        if (depth % Board.numPlayers == 1) { // We will finish a round
            for (int id : moves) {
                Card card = hands[turn].getCard(id);
                List<Card> cloneRound = listClone(currentRound);
                cloneRound.add(card);
                int win = Card.winner(cloneRound, startPlayerIdCurrentRound, trump);
                int curTrick = Board.isNorthSouth(win) ? 1 : 0;
                cloneRound.clear();
                assert (hands[turn].play(id));
                cardPlayed[card.getId()] = true;
                boolean okay = solve(hands, takenTricks + curTrick, targetTricks, trump, win,
                        depth - 1, cloneRound, win, null, cardPlayed);
                hands[turn].unplay(id);
                cardPlayed[card.getId()] = false;
                // For North or South, if we find a "good" move, then just return
                if (Board.isNorthSouth(turn)) {
                    if (okay) {
                        return true;
                    }
                } else {  // For East or West, if we find a "bad" move, then just return
                    if (!okay) {
                        return false;
                    }
                }
            }
        } else {
            for (int id : moves) {
                Card card = hands[turn].getCard(id);
                List<Card> cloneRound = listClone(currentRound);
                cloneRound.add(card);
                assert (hands[turn].play(id));
                cardPlayed[card.getId()] = true;
                boolean okay = solve(hands, takenTricks, targetTricks, trump, (turn + 1) % Board.numPlayers, depth - 1,
                        cloneRound, startPlayerIdCurrentRound, firstCardInRound == null ? card : firstCardInRound, cardPlayed);
                hands[turn].unplay(id);
                cardPlayed[card.getId()] = false;
                // For North or South, if we find a "good" move, then just return
                if (Board.isNorthSouth(turn)) {
                    if (okay) {
                        return true;
                    }
                } else { // For East or West, if we find a "bad" move, then just return
                    if (!okay) {
                        return false;
                    }
                }
            }
        }
        if (Board.isNorthSouth(turn)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Determine the maximum number of tricks that North / South can take of double dummy.
     * @param hands
     * @param trump
     * @param lead
     * @return the maximum number of tricks
     */
    public static int bestPossible(Hand hands[], int trump, int lead) {
        int low = 0;
        int high = Hand.numCardsInHand + 1;
        while (high - low > 1) {
            int mid = (low + high) / 2;
            if (solve(hands, mid, trump, lead)) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
