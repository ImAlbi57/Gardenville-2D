package it.unibs.pajc.gardenville;

public class CollisionManager {
    private MapManager mm;
    private ClientData cd;
    private SoundManager soundManager = new SoundManager();
    private boolean noKeyDoorCollision = false;
    private boolean chestCollision = false;
    private boolean hutCollision = false;

    public CollisionManager(MapManager mm, ClientData cd) {
        this.cd = cd;
        this.mm = mm;
    }

    public void checkTileCollision() {

        int entityLeftWorldX = cd.getCoords().getX() + ClientData.hitboxArea.x;
        int entityRightWorldX = cd.getCoords().getX() + ClientData.hitboxArea.x + ClientData.hitboxArea.width;
        int entityTopWorldY = cd.getCoords().getY() + ClientData.hitboxArea.y;
        int entityBottomWorldY = cd.getCoords().getY() + ClientData.hitboxArea.y + ClientData.hitboxArea.height;

        int entityLeftCol = entityLeftWorldX / MapManager.tileDim;
        int entityRightCol = entityRightWorldX / MapManager.tileDim;
        int entityTopRow = entityTopWorldY / MapManager.tileDim;
        int entityBottomRow = entityBottomWorldY / MapManager.tileDim;

        Direction d = cd.getDirection();
        if(d != null) {
            cd.resetAvailableMovements();
            if(d.equals(Direction.N) || d.equals(Direction.NE) || d.equals(Direction.NW)) {
                entityTopRow = (entityTopWorldY - cd.getSpeed()) / MapManager.tileDim;
                setCollision(ClientData.MOVEMENT_W, entityLeftCol, entityTopRow, entityRightCol, entityTopRow);
            }
            if(d.equals(Direction.W) || d.equals(Direction.NW) || d.equals(Direction.SW)) {
                entityLeftCol = (entityLeftWorldX - cd.getSpeed()) / MapManager.tileDim;
                setCollision(ClientData.MOVEMENT_A, entityLeftCol, entityTopRow, entityLeftCol, entityBottomRow);
            }
            if(d.equals(Direction.S) || d.equals(Direction.SE) || d.equals(Direction.SW) ){
                entityBottomRow = (entityBottomWorldY + cd.getSpeed()) / MapManager.tileDim;
                setCollision(ClientData.MOVEMENT_S, entityLeftCol, entityBottomRow, entityRightCol, entityBottomRow);
            }
            if (d.equals(Direction.E) || d.equals(Direction.NE) ||d.equals(Direction.SE)) {
                entityRightCol = (entityRightWorldX + cd.getSpeed()) / MapManager.tileDim;
                setCollision(ClientData.MOVEMENT_D, entityRightCol, entityTopRow, entityRightCol, entityBottomRow);
            }
        }
    }

    private void setCollision(int movement, int i1, int j1, int i2, int j2){
        int tileNum1 = mm.mapTileNums[i1][j1][0];
        int tileNum2 = mm.mapTileNums[i2][j2][0];
        if(tileNum1 == 43) keyCollision(mm.mapTileNums, i1, j1);
        if(tileNum2 == 43) keyCollision(mm.mapTileNums, i2, j2);
        if(tileNum1 == 44) doorCollision(mm.mapTileNums, i1, j1);
        if(tileNum2 == 44) doorCollision(mm.mapTileNums, i2, j2);
        if(tileNum1 == 46) chestCollision(mm.mapTileNums, i1, j1);
        if(tileNum2 == 46) chestCollision(mm.mapTileNums, i2, j2);
        if(tileNum1 == 42 || tileNum2 == 42) hutCollision = true;

        boolean canGoThru =
                (!mm.tileList[tileNum1].isCollidable &&
                !mm.tileList[tileNum2].isCollidable) ||
                    (mm.mapTileNums[i1][j1][1] == 1 ||
                    mm.mapTileNums[i2][j2][1] == 1);
        cd.setMovement(movement, canGoThru);

    }

    private void chestCollision(int[][][] map, int i, int j){
        if(map[i][j][1] == 1)
            return;
        chestCollision = true;
        playMusic(SoundManager.WINSOUND);
    }

    private void keyCollision(int[][][] map, int i, int j){
        if(map[i][j][1] == 1)
            return;
        mm.currentNumKeys++;
        map[i][j][1] = 1;
        playMusic(SoundManager.KEYSOUND);
    }

    private void doorCollision(int[][][] map, int i, int j){
        if(map[i][j][1] == 1){
            return;
        }
        if(mm.currentNumKeys <= 0){
            noKeyDoorCollision = true;
            return;
        }
        mm.currentOpenDoor++;
        mm.currentNumKeys--;
        map[i][j][1] = 1;
        playMusic(SoundManager.UNLOCKDOOR);
    }

    public boolean getChestCollision(){
        return chestCollision;
    }

    public boolean getNoKeyCollision() {
        return noKeyDoorCollision;
    }

    public void resetNoKeyCollision() {
        noKeyDoorCollision = false;
    }

    public boolean getHutCollision(){
        return hutCollision;
    }

    public void resetHutCollision() {
        hutCollision = false;
    }

    public void playMusic(int i){
        soundManager.setFile(i);
        soundManager.play();
    }
}