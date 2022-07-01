/*package it.unibs.pajc.salm2d;

public class CollisionChecker {

    MapManager mm;

    public CollisionCheckerTest(MapManager mm) {
        this.mm = mm;
    }

    public void checkTileCollision(ClientData client) {

        int entityLeftWorldX = wr + client.solidArea.x;
        int entityRightWorldX = wr + client.solidArea.x + client.solidArea.width;
        int entityTopWorldY = hr + client.solidArea.y;
        int entityBottomWorldY = hr + client.solidArea.y + client.solidArea.height;

        int entityLeftCol = entityLeftWorldX / mm.tileDim;
        int entityRightCol = entityRightWorldX / mm.tileDim;
        int entityTopRow = entityTopWorldY / mm.tileDim;
        int entityBottomRow = entityBottomWorldY / mm.tileDim;

        int tileNum1, tileNum2;


        if(d != null) {
            statusMovement[0] = true; // W
            statusMovement[1] = true; // A
            statusMovement[2] = true; // S
            statusMovement[3] = true; // D
            if(d.equals(Direction.N) || d.equals(Direction.NE) || d.equals(Direction.NW)) {
                entityTopRow = (entityTopWorldY - delta) / mm.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityTopRow];
                if (mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable) {
                    client.collisionOn = true;
                    statusMovement[0] = false;
                } else {
                    statusMovement[0] = true;
                }
            }
            if(d.equals(Direction.W) || d.equals(Direction.NW) || d.equals(Direction.SW)) {
                entityLeftCol = (entityLeftWorldX - delta) / mm.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityLeftCol][entityBottomRow];
                if (mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable) {
                    client.collisionOn = true;
                    statusMovement[1] = false;
                }
                else{
                    statusMovement[1] = true;
                }
            }
            if(d.equals(Direction.S) || d.equals(Direction.SE) || d.equals(Direction.SW) ){
                entityBottomRow = (entityBottomWorldY + delta) / mm.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityBottomRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityBottomRow];
                if (mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable) {
                    client.collisionOn = true;
                    statusMovement[2] = false;
                }
                else{
                    statusMovement[2] = true;
                }
            }
            if (d.equals(Direction.E) || d.equals(Direction.NE) ||d.equals(Direction.SE)) {
                entityRightCol = (entityRightWorldX + delta) / mm.tileDim;
                tileNum1 = mm.mapTileNums[entityRightCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityBottomRow];
                if (mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable) {
                    client.collisionOn = true;
                    statusMovement[3] = false;
                }
                else{
                    statusMovement[3] = true;
                }
            }
        }
    }
}*/