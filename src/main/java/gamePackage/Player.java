package gamePackage;

class Player {
    Position position;

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
        position.setPosition(p.getX(), p.getY());
        return true;
    }
}