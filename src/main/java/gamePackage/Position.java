package gamePackage;

//Position Class idea
class Position {
    private int x, y;

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
