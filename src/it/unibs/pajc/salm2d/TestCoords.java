package it.unibs.pajc.salm2d;

public class TestCoords {
    public static void main(String[] args) {
        //Coords c = new Coords(10, 2);
        //System.out.println(c);
        //Coords c2 = new Coords();
        //c2.update(c.toString());
        //System.out.println(c2);

        MapManager mm = new MapManager();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                System.out.print(mm.mapTileNums[i][j] + " ");
            }
            System.out.println();
        }
    }
}