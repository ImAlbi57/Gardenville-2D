package it.unibs.pajc.salm2d;

public class Path {
    private Coords[] path;
    private int currentStep;


    public Path(Coords[] path) {
        this.path = path;
        currentStep = 0;
    }

    public void addStep(Coords c){
        //path.add(c);
    }

    public Coords stepNext(){
        if(++currentStep >= path.length)
            currentStep = 0;
        return path[currentStep];
    }
}
