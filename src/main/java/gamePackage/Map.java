package gamePackage;

public class Map {
    int size;
    char[][] map ;

    Map() {
        map = null;
        size =0;
    }

    public boolean setMapSize(int x, int y){
        if(y > 50 || y < 5||(y<8&& x>4) ) {
            System.out.println("Invalid map Size ");
            return false;
        }
        size = y;
        return true;
    }

    public void generate(){
        this.map = new char[size][size];



    }

    public char getTileType(int x, int y){
       return 's';
    }


    public int getMapSize (){return this.size;}
}
