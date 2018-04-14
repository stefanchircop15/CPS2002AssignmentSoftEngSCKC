package gamePackage;

//Position Class idea
class Position {
    private int x, y;

    Position() {
        x = 0;
        y = 0;
    }

    Position(int[] coords) {
        if(coords.length != 2) {
            x = 0;
            y = 0;
        }
        else {
            x = coords[0];
            y = coords[1];
        }
    }

    Position(int newX , int newY) {
        x = newX;
        y = newY;
    }

    //Returns coordinates as an int array
    int[] getCoordinates() {
        return new int[] {x, y};
    }

    //Sets x, y
    void setCoordinates(int a, int b) {
        x = a;
        y = b;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int a) {
        x = a;
    }

    void setY(int a) {
        y = a;
    }
}
