package it.unibs.pajc.salm2d;

public class AssetSetter {
    MapManager mm;

    public AssetSetter(MapManager mm){
        this.mm = mm;
    }

    public void setObject(){
        mm.objElement[0] = new KeyObj();
        mm.objElement[0].worldX = 23 * mm.tileDim;
        mm.objElement[0].worldY = 7 * mm.tileDim;

        mm.objElement[1] = new KeyObj();
        mm.objElement[1].worldX = 23 * mm.tileDim;
        mm.objElement[1].worldY = 40 * mm.tileDim;
    }

}
