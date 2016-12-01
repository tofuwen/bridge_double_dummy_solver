package test.basic;

import main.basic.Bid;
import main.basic.Board;
import main.basic.Contract;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by tofuwen on 12/1/16.
 */
public class ContractTest {
    @Test
    public void testGetPoints() {
        Contract contract = new Contract(Board.SPADE, 4, Bid.PASS, Board.NORTH);
        assertTrue(contract.getPoints(9, true) == -100);
        assertTrue(contract.getPoints(7, false) == -150);
        contract = new Contract(Board.SPADE, 6, Bid.DOUBLE, Board.NORTH);
        assertTrue(contract.getPoints(11, true) == -200);
        assertTrue(contract.getPoints(9, true) == -800);
        assertTrue(contract.getPoints(11, false) == -100);
        assertTrue(contract.getPoints(10, false) == -300);
        assertTrue(contract.getPoints(9, false) == -500);
        assertTrue(contract.getPoints(8, false) == -800);
        contract = new Contract(Board.SPADE, 6, Bid.REDOUBLE, Board.NORTH);
        assertTrue(contract.getPoints(11, true) == -400);
        assertTrue(contract.getPoints(9, true) == -1600);
        assertTrue(contract.getPoints(11, false) == -200);
        assertTrue(contract.getPoints(10, false) == -600);
        assertTrue(contract.getPoints(9, false) == -1000);
        assertTrue(contract.getPoints(8, false) == -1600);
        contract = new Contract(Board.SPADE, 1, Bid.PASS, Board.NORTH);
        assertTrue(contract.getPoints(9, true) == 140);
        assertTrue(contract.getPoints(9, false) == 140);
        contract = new Contract(Board.SPADE, 7, Bid.PASS, Board.NORTH);
        assertTrue(contract.getPoints(13, false) == 1510);
        assertTrue(contract.getPoints(13, true) == 2210);
        contract = new Contract(Board.SPADE, 4, Bid.DOUBLE, Board.NORTH);
        assertTrue(contract.getPoints(10, false) == 590);
        contract = new Contract(Board.CLUB, 6, Bid.PASS, Board.NORTH);
        assertTrue(contract.getPoints(12, false) == 920);
        contract = new Contract(Board.HEART, 6, Bid.PASS, Board.NORTH);
        assertTrue(contract.getPoints(12, true) == 1430);
        contract = new Contract(Board.SPADE, 4, Bid.REDOUBLE, Board.NORTH);
        assertTrue(contract.getPoints(10, true) == 1080);
        contract = new Contract(Board.NT, 3, Bid.PASS, Board.NORTH);
        assertTrue(contract.getPoints(9, true) == 600);
        contract = new Contract(Board.SPADE, 2, Bid.DOUBLE, Board.NORTH);
        assertTrue(contract.getPoints(8, true) == 670);
    }
}
