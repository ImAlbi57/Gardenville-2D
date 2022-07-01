//package it.unibs.pajc.salm2d;
//
//import java.awt.event.KeyEvent;
//
//public class CollisionChecker {
//
//    PaintArea pa;
//    MapManager mm;
//
//    public CollisionChecker(MapManager mm) {
//        this.mm = mm;
//    }
//
//    public void checkTile(ClientData client){
//
//        int entityLeftWorldX =  client.getCoords().getX() + client.solidArea.x;
//        int entityLeftWorldY =  client.getCoords().getY() + client.solidArea.y + client.solidArea.width;
//        int entityTopWorldY = client.getCoords().getY() + client.solidArea.y;
//        int entityBottomWorldY = client.getCoords().getY() + client.solidArea.y + client.solidArea.height;
//
//        int entityLeftCol = entityLeftWorldY/mm.tileDim;
//        int entityRightCol = entityLeftWorldX/mm.tileDim;
//        int entityTopRow = entityTopWorldY/mm.tileDim;
//        int entityBottomRow = entityBottomWorldY/mm.tileDim;
//
//        int tileNum1, tileNum2;
//
//        //switch(d){
//        //    case N:
//        //        entityTopRow = (entityTopWorldY - 5)/mm.tileDim;
//        //        tileNum1 = mm.mapTileNums[entityLeftCol][entityTopRow];
//        //        tileNum2 = mm.mapTileNums[entityRightCol][entityTopRow];
//        //        if(mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable){
//        //            client.collisionOn = true;
//        //        }
//        //        break;
//        //    case S:
//        //        break;
//        //    case W:
//        //        break;
//        //    case E:
//        //        break;
//        //}
//
//    }
//}
