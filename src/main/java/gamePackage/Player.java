package gamePackage;

class Player {
    private Position position;

    Player() {

    }

    void move(char direction) {
        if(direction == 'U')
            position.setY(position.getY() + 1);
        if(direction == 'D')
            position.setY(position.getY() - 1);
        if(direction == 'L')
            position.setX(position.getX() - 1);
        if(direction == 'R')
            position.setX(position.getX() + 1);
    }

    boolean setPosition(Position p) {
        if(Game.map.getMapSize() < p.getX() || Game.map.getMapSize() < p.getY())
            return false;

        position.setPosition(p.getX(), p.getY());
        return true;
    }
}