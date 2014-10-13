opackage com.southamptoncodedojo.minesweeper.players;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;

import java.util.Random;

/**
 * This is an Emo Minesweeper Player.
 *
 * EmoPlayer will seek to end it all as soon as possible
 */
public class EmoPlayer extends Player {

    /**
     * All players must have a name
     * @return Your player's name
     */
    @Override
    public String getName() {
        return "EmoPlayer";
    }

    @Override
    public Coordinate takeTurn(MapState mapState) {
        int size = mapState.getSize();
    	
    	// Find unrevealed squares
    	for (int x=0; x<size; x++) {
        	for (int y=0; y<size; y++) {
        		Coordinate c = new Coordinate(x, y);

        		try {
                        int count = mapState.getCount(c);
					
                        ArrayList<Coordinate> unrevealed = getSurroundingUnrevealed(c, mapState);
					   
                        if (unrevealed.size() == count) {
                            for(Coordinate mine : unrevealed){
                                //Plant a flag on the mine...
                                mapState.flag(mine);
                                //....then jump on it!
                                return mine;
                            }
                        }

				} catch (InvalidCoordinateException e) {
					e.printStackTrace();
				} catch (UnknownCountException e) {
                    //If the square isn't revealed, skip
                    continue;
				}
          	}
    	}
        
        //If we haven't found a mine yet, guess.
        //Maybe we'll get unlucky!
        Random r = new Random();
        return new Coordinate(r.nextInt(mapState.getSize()), r.nextInt(mapState.getSize()));
    }
}
