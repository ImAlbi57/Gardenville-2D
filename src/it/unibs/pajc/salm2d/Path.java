package it.unibs.pajc.salm2d;

public class Path {
    private static int delta = 1;

    private Coords[] path;
    private int currentStep;


    public Path(Coords[] path) {
        this.path = path;
        currentStep = 0;
    }

    public Coords getNextStep(){
        if(currentStep+1 >= path.length)
            return path[0];
        return path[currentStep+1];
    }

    public Coords stepNext(Coords c){
        if(c.equals(getNextStep())){
            //System.out.println("NEXT: " + getNextStep() + " Current step: " + path[currentStep] + " Attuale: " + c);
            currentStep++;
            if(currentStep == path.length)
                currentStep = 0;
        }

        Coords nextC = getNextStep();
        Coords toReturn = new Coords(c);

        if(nextC.getX() != c.getX())
            toReturn.setX( toReturn.getX() + (int)(Math.signum(nextC.getX() - c.getX()) * delta));

        if(nextC.getY() != c.getY())
            toReturn.setY( toReturn.getY() + (int)(Math.signum(nextC.getY() - c.getY()) * delta));

        return toReturn;
    }
}
