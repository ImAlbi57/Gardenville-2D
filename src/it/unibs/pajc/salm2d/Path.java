package it.unibs.pajc.salm2d;

public class Path {

    private final Coords[] path;
    private int currentStep;
    private Direction d;
    private Coords currentCoords;


    public Path(Coords[] path) {
        currentCoords = path[0];
        this.d = Direction.S;
        this.path = path;
        currentStep = 0;
    }

    public Coords getNextStep(){
        if(currentStep+1 >= path.length)
            return path[0];
        return path[currentStep+1];
    }

    public void stepNext(){
        if(currentCoords.equals(getNextStep())){
            currentStep++;
            if(currentStep == path.length)
                currentStep = 0;
        }

        Coords nextPathCoord = getNextStep();
        Coords newCoords = new Coords(currentCoords);

        int dx = (int) Math.signum(nextPathCoord.getX() - currentCoords.getX());
        int dy = (int) Math.signum(nextPathCoord.getY() - currentCoords.getY());
        this.d = Direction.getDirection(dy<0, dx<0, dy>0, dx>0);

        if(nextPathCoord.getX() != currentCoords.getX())
            newCoords.setX( newCoords.getX() + dx);

        if(nextPathCoord.getY() != currentCoords.getY())
            newCoords.setY( newCoords.getY() + dy);

        this.currentCoords = newCoords;
    }

    public Direction getDirection() {
        return d;
    }

    public Coords getCoords() {
        return currentCoords;
    }
}
