package main.basic;

import java.util.List;

/**
 * Created by tofuwen on 10/28/16.
 */
public class Card {

    // 0 - 51
    // S2 - SA H2 - HA D2 - DA C2 - CA
    private int id;
    // 0 - S; 1 - H; 2 - D; 3 - C
    private int suit;
    private int number;

    public Card(int suit, int number) {
        this(suit * 13 + number - 2);
        assert (number >= 2 && number <= 14);
    }

    public Card(int id) {
        assert (id >= 0 && id < Board.numCards);
        this.id = id;
        this.suit = id / 13;
        this.number = id % 13 + 2;
    }

    public int getId() {
        return this.id;
    }

    public int getSuit() {
        return this.suit;
    }

    public int getNumber() {
        return this.number;
    }

    public String getSuitString() {
        String [] suitArray = {"S", "H", "D", "C"};
        return suitArray[this.suit];
    }

    public String getNumberString() {
        if (this.number < 10) {
            return Integer.toString(this.getNumber());
        }
        String [] numberArray = {"T", "J", "Q", "K", "A"};
        return numberArray[this.number - 10];
    }

    public void print(boolean isPlayed) {
        String isPlayedString = isPlayed ? "Played" : "Not Played";
        System.out.println(this.getSuitString() + this.getNumberString() + " " + isPlayedString);
    }

    public boolean equals(Card other) {
        return other.getNumber() == this.getNumber() && other.getSuit() == this.getSuit();
    }

    /**
     * Given a round, find the winner of this round
     * @param list list of cards represent the cards of this round in order.
     *             The first element is the card played by the start player of this round.
     * @param startPlayerId
     * @param trumpSuit
     * @return the winner player id
     */
    public static int winner(List<Card> list, int startPlayerId, int trumpSuit) {
        int size = list.size();
        assert(size == Board.numPlayers);
        Card winnerCard = list.get(0);
        for (int i = 1; i < size; ++i) {
            Card card = list.get(i);
            if (winnerCard.getSuit() == card.getSuit() && card.getNumber() > winnerCard.getNumber()) {
                winnerCard = card;
            }
            if (winnerCard.getSuit() != card.getSuit() && card.getSuit() == trumpSuit) {
                winnerCard = card;
            }
        }
        for (int i = 0; i < size; ++i) {
            if (list.get(i).equals(winnerCard)) {
                return (i + startPlayerId) % size;
            }
        }
        assert (false); // You should never see this.
        return 0;
    }

}
