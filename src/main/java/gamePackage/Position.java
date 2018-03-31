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

    public int[] getCoordinates() {
        return new int[] {x, y};
    }

    public void setCoordinates(int a, int b) {
        x = a;
        y = b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int a) {
        x = a;
    }

    public void setY(int a) {
        y = a;
    }
}
