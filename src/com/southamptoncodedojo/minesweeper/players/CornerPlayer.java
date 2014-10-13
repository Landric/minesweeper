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
 * Copy this into your own class to implement your AI.
 *
 * Add your AI to Minesweeper.java to test it
 */
public class CornerPlayer extends Player {

    /**
     * All players must have a name
     * @return Your player's name
     */
    @Override
    public String getName() {
        return "CornerPlayer";
    }

    /**
     * Players return the coordinate they wish to click on.
     * You can return an already opened coordinate, which will have no effect
     *
     * You can use mapState.getCount to get the number of mines surrounding a particular coordinate
     * it will throw a UnknownCountException if the space has not yet been revealed. If you'd rather it return
     * -1 instead of throwing an exception, use getCountOrMinusOne.
     *
     * You can flag spaces which you think contain mines using mapState.flag(coordinate), unflag it with
     * mapState.unflag(coordinate), and check if something is flagged using mapState.isFlagged(coordinate). You can do
     * this as many times as you like per turn
     *
     * @param mapState This contains the known information about the map
     * @return The coordinate you'd like to "click on"
     */
    @Override
    public Coordinate takeTurn(MapState mapState) {
        int size = mapState.getSize();
        int mines = mapState.getNumberOfMines();

        float[][] probabilities = new float[size][size];

        ArrayList<Coordinate> flaggedMines = new ArrayList<Coordinate>();


        // Find unrevealed squares
        for (int x=0; x<size; x++) {
            for (int y=0; y<size; y++) {
                Coordinate c = new Coordinate(x, y);

                try {
                    int count = mapState.getCount(c);

                    ArrayList<Coordinate> unrevealed = getSurroundingUnrevealed(c, mapState);

                    if (unrevealed.size() == count) {
                        for(Coordinate mine : unrevealed){
                            flaggedMines.add(mine);
                            mapState.flag(mine);
                        }
                    }

                } catch (InvalidCoordinateException e) {
                    e.printStackTrace();
                } catch (UnknownCountException e) {
                    // find all the squares around it


                }
            }
        }

        ArrayList<Coordinate> safeSquares = new ArrayList<Coordinate>();

        // Calculate probabilities
        for (int x=0; x<size; x++) {
            for (int y=0; y<size; y++) {
                Coordinate c = new Coordinate(x, y);

                try {
                    int count = mapState.getCount(c);

                    ArrayList<Coordinate> mineList = getSurroundingMines(c, mapState);

                    if (mineList.size() == count) {
                        ArrayList<Coordinate> unrevealed = getSurroundingUnrevealed(c, mapState);

                        for(Coordinate u : unrevealed){
                            if (mineList.contains(u))
                                continue;

                            safeSquares.add(u);
                        }
                    }

                } catch (InvalidCoordinateException e) {
                    e.printStackTrace();
                } catch (UnknownCountException e) {
                    // find all the squares around it


                }
            }
        }

        Random rnd = new Random();
        if (!safeSquares.isEmpty()) {
            return safeSquares.get(rnd.nextInt(safeSquares.size()));
        }

        Random r = new Random();
        Coordinate guess = new Coordinate(0, 0);
        try {
            do {

                guess = new Coordinate(r.nextInt(size), r.nextInt(size));
            } while (flaggedMines.contains(guess) || mapState.isOpen(guess));
        } catch (InvalidCoordinateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return guess;
    	
    	/*
    	
        Random r = new Random();
       
        // Also flag at random... because why not?
        try {
            mapState.flag(new Coordinate(r.nextInt(mapState.getSize()), r.nextInt(mapState.getSize())));
        } catch (InvalidCoordinateException e) {
            // Shouldn't happen...
            e.printStackTrace();coordinate
        }

        return new Coordinate(r.nextInt(mapState.getSize()), r.nextInt(mapState.getSize()));
    	*/
    }

    public ArrayList<Coordinate> getSurroundingUnrevealed(Coordinate c, MapState mapState) {
        ArrayList<Coordinate> unrevealed = new ArrayList<Coordinate>();

        for (int x=-1; x<=1; x++) {
            for (int y=-1; y<=1; y++) {
                if (y == 0 && x == 0)
                    continue;

                Coordinate tc = new Coordinate(c.getX() + x, c.getY() + y);

                try {
                    if (!mapState.isOpen(tc)){
                        unrevealed.add(tc);
                    }
                } catch (InvalidCoordinateException e) {
                    // loldon'tcare
                }
            }
        }

        return unrevealed;
    }

    public ArrayList<Coordinate> getSurroundingMines(Coordinate c, MapState mapState) {
        ArrayList<Coordinate> mines = new ArrayList<Coordinate>();

        for (int x=-1; x<=1; x++) {
            for (int y=-1; y<=1; y++) {
                if (y == 0 && x == 0)
                    continue;

                Coordinate tc = new Coordinate(c.getX() + x, c.getY() + y);

                try {
                    if (mapState.isFlagged(tc)){
                        mines.add(tc);
                    }
                } catch (InvalidCoordinateException e) {
                    // loldon'tcare
                }
            }
        }

        return mines;
    }
}