package com.southamptoncodedojo.minesweeper.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;

public class UltraSuperPlayer extends Player{

    private boolean first=true;
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Ultra Super Player";
    }

    @Override
    public Coordinate takeTurn(MapState mapState) {
        // TODO Auto-generated method stub
        Coordinate coor;
        Random r=new Random();
        if(first){
            first=false;
            return  new Coordinate(r.nextInt(mapState.getSize()-1),mapState.getSize()-1);
        }
        identifyMines(mapState);
        Coordinate returned=clickMine(mapState);
        if(returned!=null)
            return returned;
        Coordinate open=new Coordinate(r.nextInt(mapState.getSize()-1),mapState.getSize()-1);
        try {
            while((mapState.isFlagged(open)) || (mapState.isOpen(open))){
                System.out.println("R");
                open=new Coordinate(r.nextInt(mapState.getSize()-1),mapState.getSize()-1);
            }
        } catch (InvalidCoordinateException e) {
            // TODO Auto-generated catch block
        }
        return open;
    }

    private Coordinate clickMine(MapState mapState) {
        // TODO Auto-generated method stub
        int closed,flagged;
        boolean oneFlag;
        List<Coordinate> closedCoord,flaggedCoord;
        for(int x=0;x<mapState.getSize();x++){
            for(int y=0;y<mapState.getSize();y++){
                try {
                    Coordinate coordinate=new Coordinate(x, y);
                    Coordinate[] coors=new Coordinate(x,y).getSurroundingCoordinates(mapState.getSize());
                    oneFlag=false;
                    closed=0;
                    flagged=0;
                    closedCoord=new ArrayList();
                    flaggedCoord=new ArrayList();
                    for(Coordinate coord: coors){
                        if(!mapState.isOpen(coord)) {
                            closed+=1;
                            closedCoord.add(coord);
                            if(mapState.isFlagged(coord)){
                                flaggedCoord.add(coord);
                                flagged+=1;
                                oneFlag=true;
                            }
                        }
                    }
                    if(oneFlag && flagged==mapState.getCount(coordinate)){
                        for(Coordinate coord1: closedCoord){
                            if(!flaggedCoord.contains(coord1))
                                return coord1;
                        }
                    }

                } catch (InvalidCoordinateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnknownCountException e) {
                    // TODO Auto-generated catch block

                }
            }
        }
        return null;
    }

    private void identifyMines(MapState mapState) {
        // TODO Auto-generated method stub
        int closed;
        List<Coordinate> closedCoord;
        for(int x=0;x<mapState.getSize();x++){
            for(int y=0;y<mapState.getSize();y++){
                try {
                    Coordinate coordinate=new Coordinate(x, y);
                    Coordinate[] coors=new Coordinate(x,y).getSurroundingCoordinates(mapState.getSize());

                    closed=0;
                    closedCoord=new ArrayList();
                    for(Coordinate coord: coors){
                        if(!mapState.isOpen(coord)) {
                            closed+=1;
                            closedCoord.add(coord);
                        }
                    }
                    if(closed==mapState.getCount(coordinate)){
                        for(Coordinate coord: closedCoord){
                            mapState.flag(coord);
                        }
                    }

                } catch (InvalidCoordinateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnknownCountException e) {
                    // TODO Auto-generated catch block

                }
            }
        }
    }
}
