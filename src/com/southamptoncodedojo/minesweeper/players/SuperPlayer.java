package com.southamptoncodedojo.minesweeper.players;

import java.util.ArrayList;
import java.util.Random;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;

/**
 * This is a demo Minesweeper Player.
 *
 * This will start at 0,0 and move in order through all squares
 */
public class SuperPlayer extends Player {
    /**
     * All players must have a name
     *
     * @return Your player's name
     */

    @Override
    public String getName() {
        return "Super Player";
    }

    /**
     * Players return the coordinate they wish to click on. You can return an
     * already opened coordinate, which will have no affect
     *
     * You can use mapState.getCount to get the number of mines surrounding a
     * particular coordinate it will throw a UnknownCountException if the space
     * has not yet been revealed. If you'd rather it return -1 instead of
     * throwing an exception, use getCountOrMinusOne.
     *
     * You can flag spaces which you think contain mines using
     * mapState.flag(coordinate), unflag it with mapState.unflag(coordinate),
     * and check if something is flagged using mapState.isFlagged(coordinate).
     * You can do this as many times as you like per turn
     *
     * @param mapState
     *            This contains the known information about the map
     * @return The coordinate you'd like to "click on"
     */

    boolean firstDone = false;
    Random r = new Random();

    @Override
    public Coordinate takeTurn(MapState mapState) {

		/*
		 * if (!firstDone) { firstDone = true; return new
		 * Coordinate(r.nextInt(mapState.getSize()),
		 * r.nextInt(mapState.getSize())); }
		 */

        try {
            for (int i = 0; i < mapState.getSize(); i++) {
                for (int j = 0; j < mapState.getSize(); j++) {
                    Coordinate temp = new Coordinate(i, j);

                    if (mapState.isOpen(temp)) {
                        int count = mapState.getCount(temp);
                        if (count == numClearSquares(mapState, temp)) {
                            for (Coordinate c : getUnopened(mapState, temp)) {
                                mapState.flag(c);
                            }
                        }
                    }
                }
            }
        } catch (InvalidCoordinateException e) {} catch (UnknownCountException e) {}

        try {
            for (int i = 0; i < mapState.getSize(); i++) {
                for (int j = 0; j < mapState.getSize(); j++) {
                    Coordinate temp = new Coordinate(i, j);
                    if (mapState.isOpen(temp)) {
                        int num = getNumFlagged(mapState, temp);
                        if (mapState.getCount(temp) == num) {
                            for (Coordinate checked : getUnopened(mapState, temp)) {
                                if (!mapState.isFlagged(checked)) { return checked; }
                            }
                        }
                    }
                }
            }
        } catch (InvalidCoordinateException e) {} catch (UnknownCountException e) {}

        ArrayList<Coordinate> opened = new ArrayList<Coordinate>();

        try {
            for (int i = 0; i < mapState.getSize(); i++) {
                for (int j = 0; j < mapState.getSize(); j++) {
                    Coordinate temp = new Coordinate(i, j);
                    if (!mapState.isOpen(temp) && !mapState.isFlagged(temp)) {
                        opened.add(temp);
                    }
                }
            }
        } catch (InvalidCoordinateException e) {}

        Coordinate returned = opened.get(r.nextInt(opened.size()));
        System.out.println("Random");

		/*
		 * Coordinate coordinate = new Coordinate(r.nextInt(mapState.getSize()),
		 * r.nextInt(mapState.getSize())); try { System.out.println("Random");
		 * while (!mapState.isOpen(coordinate)) { System.out.println("Looping");
		 * coordinate = new Coordinate(r.nextInt(mapState.getSize()),
		 * r.nextInt(mapState.getSize())); } } catch (InvalidCoordinateException
		 * e) { // e.printStackTrace(); }
		 */

        return returned;
    }

    public ArrayList<Coordinate> getUnopened(MapState mapState, Coordinate c) {
        ArrayList<Coordinate> list = new ArrayList<Coordinate>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                try {
                    Coordinate c2 = new Coordinate(c.getX() + i, c.getY() + j);
                    if (!mapState.isOpen(c2)) {
                        list.add(c2);
                    }
                } catch (InvalidCoordinateException e) {}
            }
        }
        return list;
    }

    public int getNumFlagged(MapState mapState, Coordinate c) {
        int count = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                try {
                    Coordinate c2 = new Coordinate(c.getX() + i, c.getY() + j);
                    if (mapState.isFlagged(c2)) {
                        count++;
                    }
                } catch (InvalidCoordinateException e) {}
            }
        }
        return count;
    }

    public ArrayList<Coordinate> getFlagged(MapState mapState, Coordinate c) {
        ArrayList<Coordinate> list = new ArrayList<Coordinate>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                try {
                    Coordinate c2 = new Coordinate(c.getX() + i, c.getY() + j);
                    if (mapState.isFlagged(c2)) {
                        list.add(c2);
                    }
                } catch (InvalidCoordinateException e) {}
            }
        }
        return list;
    }

    public int numClearSquares(MapState mapState, Coordinate c) {
        int x = c.getX();
        int y = c.getY();

        int count = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int newX = x + i;
                int newY = y + j;

                try {
                    if (!mapState.isOpen(new Coordinate(newX, newY))) {
                        count += 1;
                    }
                } catch (InvalidCoordinateException e) {}
            }
        }
        return count;
    }
}