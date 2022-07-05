package it.unibs.pajc.salm2d;

public class CollisionChecker {
    MapManager mm;
    ClientData cd;

    public CollisionChecker(MapManager mm, ClientData cd) {
        this.cd = cd;
        this.mm = mm;
    }
    //Metodo che mi ha fatto smadonnare
    public int checkObjectCollision(ClientData client){
        int index = 999;

        for(int i = 43; i < 44; i++){
            if(mm.tileList[i] != null){
                client.solidArea.x = client.getCoords().getX() + client.solidArea.x;
                client.solidArea.y = client.getCoords().getY() + client.solidArea.y;
                mm.tileList[i].solidArea.x = - mm.tileList[i].getX() - mm.tileList[i].solidArea.x;
                mm.tileList[i].solidArea.y =  mm.tileList[i].getY() + mm.tileList[i].solidArea.y;

                System.out.println("player X --- " + client.getCoords().getX());
                System.out.println("player Y --- " + client.getCoords().getY());
                System.out.println("item X --- " + mm.tileList[i].solidArea.x);
                System.out.println("item Y --- " + mm.tileList[i].solidArea.y);

                //Direction d = client.getDirection();
                //if(d != null){
                //    //client.resetAvailableMovements();
                //    if(d.equals(Direction.N) || d.equals(Direction.NE) || d.equals(Direction.NW)) {
                //        client.solidArea.y -= client.getSpeed();
                //        if(client.solidArea.intersects(mm.tileList[i].solidArea)){
                //            System.out.println("up collision");
                //        }
                //    }
                //    if(d.equals(Direction.W) || d.equals(Direction.NW) || d.equals(Direction.SW)) {
                //        client.solidArea.x -= client.getSpeed();
                //        if(client.solidArea.intersects(mm.tileList[i].solidArea)){
                //            System.out.println("left collision");
                //        }
                //    }
                //    if(d.equals(Direction.S) || d.equals(Direction.SE) || d.equals(Direction.SW) ){
                //        client.solidArea.y += client.getSpeed();
                //        if(client.solidArea.intersects(mm.tileList[i].solidArea)){
                //            System.out.println("down collision");
                //        }
                //    }
                //    if (d.equals(Direction.E) || d.equals(Direction.NE) ||d.equals(Direction.SE)) {
                //        client.solidArea.x += client.getSpeed();
                //        if(client.solidArea.intersects(mm.tileList[i].solidArea)){
                //            System.out.println("right collision");
                //        }
                //    }
                //}
                client.solidArea.x = client.solidAreaDefaultX;
                client.solidArea.y = client.solidAreaDefaultY;
                mm.tileList[i].solidArea.x = mm.tileList[i].solidAreaDefaultX;
                mm.tileList[i].solidArea.y = mm.tileList[i].solidAreaDefaultY;

            }
        }
        return index;
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