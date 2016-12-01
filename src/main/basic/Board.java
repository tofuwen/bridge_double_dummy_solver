package main.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tofuwen on 10/28/16.
 */
public class Board {

    public static final int numPlayers = 4;
    public static final int numCards = 52;

    // constant for player postion
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    // constant for suit
    public static final int SPADE = 0;
    public static final int HEART = 1;
    public static final int DIAMOND = 2;
    public static final int CLUB = 3;
    public static final int NT = -1;

    // constant for vulnerability
    public static final int NONE = 0;
    public static final int North_South = 1;
    public static final int East_West = 2;
    public static final int ALL = 3;

    private Player [] players = null;
    private int trumpSuit = NT;
    private int boardNumber = 1; // represents board number
    private int dealer = NORTH;
    private int vulnerability = NONE;
    private Contract contract = null;
    private ArrayList<Bid> bidList = new ArrayList<Bid>();
    private int lead = NORTH;

    private int turn = NORTH; // player id for current turn
    private int tricksNorthSouth = 0;
    private int tricksEastWest = 0;
    private int cardsPlayed = 0;
    private List<Card> currentRoundCards = new ArrayList<Card>();
    private int startPlayerId = 0; // start player id for current round


    /**
     * Construct the board with default trump NT, with default board number.
     */
    public Board() {
        this(NT, 1);
    }

    /**
     * Construct the board with given trump, with default board number.
     * @param trumpSuit
     */
    public Board(int trumpSuit) {
        this(trumpSuit, 1);
    }

    /**
     * Construct the board with given trump, with given board number.
     * @param trumpSuit
     * @param boardNumber
     */
    public Board(int trumpSuit, int boardNumber) {
        assert (boardNumber >= 1 && boardNumber <= 32);
        assert (trumpSuit >= -1 && trumpSuit <= 3);
        this.boardNumber = boardNumber;
        this.trumpSuit = trumpSuit;
        this.dealer = this.calDealer(boardNumber);
        this.vulnerability = this.calVulnerability(boardNumber);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < numCards; ++i) {
            list.add(i);
        }
        // shuffle the board
        Collections.shuffle(list);
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; ++i) {
            int [] cardIds = new int[Hand.numCardsInHand];
            for (int j = 0; j < Hand.numCardsInHand; ++j) {
                cardIds[j] = list.get(i * Hand.numCardsInHand + j);
            }
            players[i] = new Player("Player" + i, cardIds);
        }
    }

    /**
     * Construct board using given cards
     * Only for testing
     * @param cards 4 * 13 int array represent the cards
     */
    public Board(int[][] cards, int trumpSuit) {
        assert (trumpSuit >= -1 && trumpSuit <= 3);
        this.trumpSuit = trumpSuit;
        assert (cards.length == numPlayers);
        for (int i = 0; i < numPlayers; ++i) {
            assert (cards[i].length == Hand.numCardsInHand);
        }
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; ++i) {
            int [] cardIds = cards[i];
            players[i] = new Player("Player" + i, cardIds);
        }
    }

    public int getTurn() {
        return this.turn;
    }

    public int getTricksNorthSouth() {
        return this.tricksNorthSouth;
    }

    public int getTricksEastWest() {
        return this.tricksEastWest;
    }

    public Player getPlayer(int playerId) {
        return players[playerId];
    }

    public Contract getContract() {
        return this.contract;
    }

    public int getLead() {
        return this.lead;
    }

    public int getTrumpSuit() {
        return this.trumpSuit;
    }

    public int getVulnerability() {
        return this.vulnerability;
    }

    public int getDealer() {
        return this.dealer;
    }

    public int getBoardNumber() {
        return this.boardNumber;
    }

    public ArrayList<Bid> getBidList() {
        return bidList;
    }

    public void print() {
        for (int i = 0; i < numPlayers; ++i) {
            players[i].print();
        }
    }

    public static boolean isNorthSouth(int playerId) {
        return playerId == NORTH || playerId == SOUTH;
    }

    public static boolean isSameTeam(int position, int otherPosition) {
        if (position == WEST || position == EAST)
            return otherPosition == WEST || otherPosition == EAST;
        return otherPosition == NORTH || otherPosition == SOUTH;
    }

    /**
     * Increment turn.
     * @param isLast true if this card being played is the last card in a round
     */
    public void incrementTurn(boolean isLast) {
        ++cardsPlayed;
        if (!isLast) {
            ++turn;
            turn %= numPlayers;
        } else {
            turn = Card.winner(currentRoundCards, startPlayerId, trumpSuit);
            if (turn == NORTH || turn == SOUTH) {
                tricksNorthSouth++;
            } else {
                tricksEastWest++;
            }
        }
    }

    /**
     * Play one card for this board.
     * @param id the card id (0-12) of the card that current player want to play
     * @return true if we can play this card successfully.
     *         false otherwise.
     */
    public boolean play(int id) {
        Player currentPlayer = this.getPlayer(turn);
        Card currentCard = currentPlayer.getCard(id);
        // start a new round
        if (cardsPlayed % numPlayers == 0) {
            if (!currentPlayer.play(id)) {
                // we cannot legally play this card.
                return false;
            }
            startPlayerId = turn;
            currentRoundCards.clear();
            currentRoundCards.add(currentCard);
            incrementTurn(false);
            return true;
        }
        Card firstOfRound = currentRoundCards.get(0);
        if (!currentPlayer.play(id, firstOfRound)) {
            // we cannot legally play this card.
            return false;
        }
        currentRoundCards.add(currentCard);
        // finish a round
        if (cardsPlayed % numPlayers == numPlayers - 1) {
            incrementTurn(true);
        } else {
            incrementTurn(false);
        }
        return true;
    }

    /**
     * Given the board number, determine the vulnerability
     * @param boardNumber
     * @return vulnerability
     */
    private int calVulnerability(int boardNumber) {
        boardNumber %= 16;
        if (boardNumber == 1 || boardNumber == 8 || boardNumber == 11 || boardNumber == 14) {
            return NONE;
        }
        if (boardNumber == 2 || boardNumber == 5 || boardNumber == 12 || boardNumber == 15) {
            return North_South;
        }
        if (boardNumber == 3 || boardNumber == 6 || boardNumber == 9 || boardNumber == 16) {
            return East_West;
        }
        return ALL;
    }

    /**
     * Given the boardNumber, determine the dealer
     * @param boardNumber
     * @return the dealer
     */
    private int calDealer(int boardNumber) {
        if (boardNumber % 4 == 1)
            return NORTH;
        if (boardNumber % 4 == 2)
            return EAST;
        if (boardNumber % 4 == 3)
            return SOUTH;
        return WEST;
    }

    private int nextBidPos(int curBidPos) {
        return (curBidPos + 1) % Board.numPlayers;
    }

    /**
     * You should only call this once.
     * Note that "bids" may not contain correct information for "position" field.
     * It may only contain "suit", "level" information.
     * The position field should be determined here.
     * @param bids
     */
    public void doBidding(ArrayList<Bid> bids) {
        int size = bids.size();
        int curBidPos = dealer;
        for (int i = 0; i < size; ++i) {
            Bid bid = bids.get(i);
            bidList.add(new Bid(bid.getSuit(), bid.getLevel(), curBidPos));
            curBidPos = nextBidPos(curBidPos);
        }
        contract = Contract.calContract(bidList);
        if (contract.getIsAllPass()) {
            System.out.println("All Pass!");
            System.out.println("Should play next board!");
        }
        trumpSuit = contract.getSuit();
        lead = (contract.getDeclarer() + 1) % numPlayers;
        turn = lead;
    }
}
