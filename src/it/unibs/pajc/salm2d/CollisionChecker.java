package it.unibs.pajc.salm2d;

public class CollisionChecker {
    MapManager mm;
    ClientData cd;

    public CollisionChecker(MapManager mm, ClientData cd) {
        this.cd = cd;
        this.mm = mm;
    }

    public void checkTileCollision(ClientData client) {

        int entityLeftWorldX = cd.getCoords().getX() + client.solidArea.x;
        int entityRightWorldX = cd.getCoords().getX() + client.solidArea.x + client.solidArea.width;
        int entityTopWorldY = cd.getCoords().getY() + client.solidArea.y;
        int entityBottomWorldY = cd.getCoords().getY() + client.solidArea.y + client.solidArea.height;

        int entityLeftCol = entityLeftWorldX / MapManager.tileDim;
        int entityRightCol = entityRightWorldX / MapManager.tileDim;
        int entityTopRow = entityTopWorldY / MapManager.tileDim;
        int entityBottomRow = entityBottomWorldY / MapManager.tileDim;

        int tileNum1, tileNum2;

        Direction d = cd.getDirection();
        if(d != null) {
            cd.resetAvailableMovements();
            if(d.equals(Direction.N) || d.equals(Direction.NE) || d.equals(Direction.NW)) {
                entityTopRow = (entityTopWorldY - cd.getSpeed()) / MapManager.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityTopRow];
                cd.setMovement(ClientData.MOVEMENT_W, !mm.tileList[tileNum1].isCollidable && !mm.tileList[tileNum2].isCollidable);
            }
            if(d.equals(Direction.W) || d.equals(Direction.NW) || d.equals(Direction.SW)) {
                entityLeftCol = (entityLeftWorldX - cd.getSpeed()) / MapManager.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityLeftCol][entityBottomRow];
                cd.setMovement(ClientData.MOVEMENT_A, !mm.tileList[tileNum1].isCollidable && !mm.tileList[tileNum2].isCollidable);
            }
            if(d.equals(Direction.S) || d.equals(Direction.SE) || d.equals(Direction.SW) ){
                entityBottomRow = (entityBottomWorldY + cd.getSpeed()) / MapManager.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityBottomRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityBottomRow];
                cd.setMovement(ClientData.MOVEMENT_S, !mm.tileList[tileNum1].isCollidable && !mm.tileList[tileNum2].isCollidable);
            }
            if (d.equals(Direction.E) || d.equals(Direction.NE) ||d.equals(Direction.SE)) {
                entityRightCol = (entityRightWorldX + cd.getSpeed()) / MapManager.tileDim;
                tileNum1 = mm.mapTileNums[entityRightCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityBottomRow];
                cd.setMovement(ClientData.MOVEMENT_D, !mm.tileList[tileNum1].isCollidable && !mm.tileList[tileNum2].isCollidable);
            }
        }
    }
}