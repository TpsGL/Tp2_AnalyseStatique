package clustering.models;

public class Proximity implements Comparable<Proximity>, Cloneable {

    private Double proximity;

    private Double weight;

    public Proximity() {this(0.0);}

    public Proximity(Double proximity) {this(proximity, 1.0);}

    public Proximity(Double proximity, Double weight) {
        this.proximity = proximity;
        this.weight = weight;
    }

    public Double getProximity() {return proximity; }

    public void setProximity(Double proximity) {
        this.proximity = proximity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isNull() { return proximity == null || proximity.isNaN() ;}

    @Override
    public int compareTo(Proximity proximity) {
        return proximity == null ? 1 : getProximity().compareTo(proximity.getProximity());
    }


}
