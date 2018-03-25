package gamePackage;

//Position Class idea
public class Position {
    private int x, y;
    Position() {

    }
    Position(int newX , int newY) {
        x =newX;
        y = newY;
    }

    public int[] getPosition() {
        return new int[] {x, y};
    }

    public void setPosition(int a, int b) {
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
