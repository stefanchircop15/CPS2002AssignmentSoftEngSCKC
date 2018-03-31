package gamePackage;

import Exceptions.LocationIsOutOfRange;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class Player {
    private Position position;

    private List<Position> visited = new ArrayList<>();

    Player() {
        position = new Position();
    }

    //Moves Player one step in one direction, unless out of bounds. Does not consider tile type
    boolean move(char direction) {
        Position toMove;
        //If tile exists in map, mark as visited and attempt to set position
        try {
            if (direction == 'U' && Game.map.getMapSize() != position.getY() + 1) {
                toMove = new Position(position.getX(), position.getY() + 1);
                visited.add(toMove);
                return setPosition(toMove);
            }
            else if (direction == 'D' && position.getY() != 0) {
                toMove = new Position(position.getX(), position.getY() - 1);
                visited.add(toMove);
                return setPosition(toMove);
            }
            else if (direction == 'L' && position.getX() != 0) {
                toMove = new Position(position.getX() - 1, position.getY());
                visited.add(toMove);
                return setPosition(toMove);
            }
            else if (direction == 'R' && Game.map.getMapSize() != position.getX() + 1) {
                toMove = new Position(position.getX() + 1, position.getY());
                visited.add(toMove);
                return setPosition(toMove);
            }
            else return false;
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Places Player at desired position p if valid
    boolean setPosition(Position p) throws LocationIsOutOfRange {
        int n = Game.map.getMapSize();
        char tile = Game.map.getTileType(p.getX(), p.getY());

        if (p.getX() < n && p.getY() < n && (tile == 'G' || tile == 'T')){
            position.setCoordinates(p.getX(), p.getY());
            visited.add(position);
            return true;
        }

        return false;
    }

    List<Position> getVisited() {
        return visited;
    }

    Position getPosition() {
        return position;
    }
}