package gamePackage;

import Exceptions.LocationIsOutOfRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map {
    int size;
    char[][] map ;
    Position treasureLocation;

    Map() {
        this.treasureLocation = new Position();
        this.size =0;
        this.map = new char[5][5];
    }

    public boolean setMapSize(int noOfPlayers, int mapSize){
        if(mapSize > 50 || mapSize < 5||(mapSize<8&& noOfPlayers>4)||noOfPlayers<2 || noOfPlayers>8 ) {
            System.out.println("Invalid map Size ");
            return false;
        }
        size = mapSize;
        return true;
    }

    public void generate(){
        int nextY=0;int nextX=0;
       List listOfLocationLeadinToTreasure =  new ArrayList();
       createEmptyMap();

       //setTreasurePosition();

        for(int i = 0; i < getMapSize()/2; i++ ){

            nextY = (int)(Math.random()*2);
            nextX = (int)(Math.random()*2);



        }









    }
    public void setTreasurePosition(Position treasure)throws LocationIsOutOfRange{
        if (treasure.getX()<0 || treasure.getX() > this.getMapSize()-1 || treasure.getY()<0 || treasure.getY() > this.getMapSize()-1)
            throw new LocationIsOutOfRange("Treasure Location is invalid." );
        this.treasureLocation.setPosition(treasure.getX(),treasure.getY());
        setTileType(treasureLocation.getX(),treasureLocation.getY(),'T');
    }

    public char getTileType(int x, int y) throws LocationIsOutOfRange {
        if (x<0 || x > this.getMapSize()-1 || y<0 || y > this.getMapSize()-1)
            throw new LocationIsOutOfRange("Location is out of reach." );
        return this.map[x][y];
    }

    public void setTileType(int x, int y, char input){
        this.map[x][y] = input;
    }

    public void createEmptyMap() {
        this.map = new char[size][size];
    }


    public int getMapSize (){return this.size;}
}
