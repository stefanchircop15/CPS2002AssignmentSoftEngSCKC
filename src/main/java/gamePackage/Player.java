package gamePackage;

import Exceptions.LocationIsOutOfRange;

import java.util.ArrayList;

class Player {
    private Position position, start;

    private ArrayList<Position> visited = new ArrayList<>();

    Player() {
        position = new Position(0 ,0);
        start = new Position(0 , 0);
    }

    //Moves Player one step in one direction, unless out of bounds. Does not consider tile type
    boolean move(char direction) {
        Position toMove;
        //If tile exists in map, mark as visited and attempt to set position
        try {
            if (direction == 'U') {
                toMove = new Position(position.getX(), position.getY() + 1);
                return setPosition(toMove);
            }
            else if (direction == 'D') {
                toMove = new Position(position.getX(), position.getY() - 1);
                return setPosition(toMove);
            }
            else if (direction == 'L') {
                toMove = new Position(position.getX() - 1, position.getY());
                return setPosition(toMove);
            }
            else if (direction == 'R') {
                toMove = new Position(position.getX() + 1, position.getY());
                return setPosition(toMove);
            }
            else return false;
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Places Player at desired position p if valid move
    boolean setPosition(Position p) throws LocationIsOutOfRange {
        int n = Game.getMap().getMapSize();
        char tile = Game.getMap().getTileType(p.getX(), p.getY());

        //If within bounds
        if (p.getX() < n && p.getY() < n) {
            visited.add(new Position(p.getX(), p.getY()));

            //If valid tile type move, else reset to starting position
            if(tile == 'G' || tile == 'T')
                position.setCoordinates(p.getX(), p.getY());
            else
                position.setCoordinates(start.getX(), start.getY());
        }

        return true;
    }

    //Sets starting point for player
    void setStart(Position p) throws LocationIsOutOfRange {
        if(setPosition(p))
            start = p;
    }

    //Returns visited list
    ArrayList<Position> getVisited() {
        return visited;
    }

    //Adds x to visited
    void addVisited(Position x) {
        visited.add(x);
    }

    //Returns current position
    Position getPosition() {
        return position;
    }

    //Returns starting position
    Position getStart() {
        return start;
    }
}
