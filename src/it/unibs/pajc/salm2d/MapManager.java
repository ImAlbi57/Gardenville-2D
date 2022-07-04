package it.unibs.pajc.salm2d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class MapManager {

    public static final int numTiles = 45;  //the first 10 are empty (0-9)
    public static final int mapDim = 50;
    public static final int tileDim = 128;

    public final Tile[] tileList;
    public int[][] mapTileNums;

    public SuperObject objElement[] = new SuperObject[10];
    public AssetSetter setter = new AssetSetter(this);

    public MapManager() {
        tileList = new Tile[numTiles];
        mapTileNums = new int[mapDim][mapDim];
        readMapData();
        getImages();
        setter.setObject();
    }

    private void readMapData() {
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/map/worldV3.txt"))));
        try {
            for (int i = 0; i < 50; i++) {
                String[] lineParts = br.readLine().split(" ");
                for (int j = 0; j < 50; j++) {
                    mapTileNums[i][j] = Integer.parseInt(lineParts[j]);
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getImages() {
        try {
            for (int i = 0; i < 10; i++) tileList[i] = new Tile(null, false);
            tileList[10] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/grass00.png"))), false);
            tileList[11] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/grass01.png"))), false);
            tileList[12] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water00.png"))), true);
            tileList[13] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water01.png"))), true);
            tileList[14] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water02.png"))), true);
            tileList[15] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water05.png"))), true);
            tileList[16] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water07.png"))), true);
            tileList[17] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water03.png"))), true);
            tileList[18] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water08.png"))), true);
            tileList[19] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water04.png"))), true);
            tileList[20] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water06.png"))), true);
            tileList[21] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water09.png"))), true);
            tileList[22] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water10.png"))), true);
            tileList[23] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water12.png"))), true);
            tileList[24] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water11.png"))), true);
            tileList[25] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/water13.png"))), true);
            tileList[26] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road00.png"))), false);
            tileList[27] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road01.png"))), false);
            tileList[28] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road04.png"))), false);
            tileList[29] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road06.png"))), false);
            tileList[30] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road02.png"))), false);
            tileList[31] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road07.png"))), false);
            tileList[32] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road03.png"))), false);
            tileList[33] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road05.png"))), false);
            tileList[34] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road08.png"))), false);
            tileList[35] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road09.png"))), false);
            tileList[36] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road11.png"))), false);
            tileList[37] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road10.png"))), false);
            tileList[38] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/road12.png"))), false);
            tileList[39] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/earth.png"))), false);
            tileList[40] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/wall.png"))), true);
            tileList[41] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/tree.png"))), true);
            tileList[42] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/tiles/hut.png"))), true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void drawMap(Graphics2D g2, int startingX, int startingY){
        for (int i = 0; i < mapDim; i++) {
            for (int j = 0; j < mapDim; j++) {
                drawTile(g2, mapTileNums[i][j], startingX + i*tileDim, startingY + j*tileDim);
            }
        }
    }

    private void drawTile(Graphics2D g2, int tileNum, int x, int y) {
        g2.drawImage(tileList[tileNum].getImg(), x, y, this.tileDim, this.tileDim, null);
    }
}
