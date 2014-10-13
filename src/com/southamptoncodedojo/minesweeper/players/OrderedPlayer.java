package com.southamptoncodedojo.minesweeper.players;

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
public class OrderedPlayer extends Player {

    protected Coordinate nextCoordinate = new Coordinate(0,0);
    protected boolean freshStart = true;
    /**
     * All players must have a name
     * @return Your player's name
     */
    @Override
    public String getName() {
        return "Ordered Player";
    }

    /**
     * Players return the coordinate they wish to click on.
     * You can return an already opened coordinate, which will have no affect
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
        Coordinate thisCoordinate = nextCoordinate;
        if(freshStart){
            freshStart = false;
            return nextCoordinate = new Coordinate(mapState.getSize()/2, mapState.getSize()/2);
        }
        Random rnd = new Random();
        
        
        /*
        if (thisCoordinate.getX() == mapState.getSize() -1) {
            nextCoordinate = new Coordinate(0, thisCoordinate.getY() + 1);
        } else {
            nextCoordinate = new Coordinate(thisCoordinate.getX() + 1, thisCoordinate.getY());
        }*/
        int min = 8;
        int badSectorCount =0;
        /*
        while(badSectorCount<3){
        Coordinate answer[] = nextCoordinate.getSurroundingCoordinates(9);
       for(Coordinate ans:answer){
        try {
if(min>mapState.getCount(ans)&&mapState.getCount(ans)>0){
min=mapState.getCount(ans);
nextCoordinate = ans;
}
} catch (InvalidCoordinateException e) {
// TODO Auto-generated catch block
badSectorCount++;
} catch (UnknownCountException e) {
// TODO Auto-generated catch block
}
   
    }
        }*/


        thisCoordinate=new Coordinate(rnd.nextInt(mapState.getSize()), rnd.nextInt(mapState.getSize()));
        return thisCoordinate;
    }

}