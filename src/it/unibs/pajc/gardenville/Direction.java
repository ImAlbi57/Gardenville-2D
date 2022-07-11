package it.unibs.pajc.gardenville;

public enum Direction {
    N, S, E, W, NE, NW, SE, SW;

    public static Direction getDirection(boolean up, boolean left, boolean down, boolean right) {
        return updateDirection(null, up, left, down, right);

    }

    public static Direction updateDirection(Direction oldDirection, boolean up, boolean left, boolean down, boolean right) {
        boolean north = false, south = false, est = false, west = false;

        if(up && !down) north = true;
        if(down && !up) south = true;
        if(left && !right) west = true;
        if(right && !left) est = true;

        if(north && west) return NW;
        if(north && est) return NE;
        if(south && west) return SW;
        if(south && est) return SE;

        if(north) return N;
        if(south) return S;
        if(est) return E;
        if(west) return W;

        //default
        if(oldDirection == null)
            return S;
        return oldDirection;
    }
}
