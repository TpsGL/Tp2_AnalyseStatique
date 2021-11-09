package clustering.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cluster {

    private String name;

    private Cluster parent;

    private List<Cluster> fils;

    private List<String> feuilleNames;

    private Proximity proximity = new Proximity();

    public Cluster(String name) {
        this.name = name;
        feuilleNames = new ArrayList<String>();
    }

    public Proximity getProximity() {return proximity;}

    public Double getWeightValue() {return proximity.getWeight();}

    public Double getProximityValue() {return proximity.getProximity();}

    public void setProximity(Proximity proximity) {
        this.proximity = proximity;
    }

    public List<Cluster> getFils() {
        if ( fils == null ) {
            fils = new ArrayList<Cluster>();
        }
        return fils;
    }

    public void addFilName(String fname) {feuilleNames.add(fname);}

    public void addFilsNames(List<String> fnames) {feuilleNames.addAll(fnames);}

    public List<String> getFilsNames() {
        return feuilleNames;
    }

    public void setFils(List<Cluster> fils) {
        this.fils = fils;
    }

    public Cluster getParent() {
        return parent;
    }

    public void setParent(Cluster parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void addFil(Cluster cluster) {
        getFils().add(cluster);
    }

    public boolean contains(Cluster cluster) {
        return getFils().contains(cluster);
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "name='" + name + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cluster cluster = (Cluster) o;
        return Objects.equals(name, cluster.name) && Objects.equals(parent, cluster.parent) && Objects.equals(fils, cluster.fils) && Objects.equals(feuilleNames, cluster.feuilleNames) && Objects.equals(proximity, cluster.proximity);
    }

    @Override
    public int hashCode() {
        return (name == null) ? 0 : name.hashCode();
    }

    public boolean isFeuille() {
        return getFils().size() == 0;
    }

    public int countFeuilles() {
        return countFeuilles(this, 0);
    }

    public int countFeuilles(Cluster node, int count) {
        if (node.isFeuille()) count++;

        for (Cluster cluster: node.getFils())
        {
            count += cluster.countFeuilles();
        }
        return count;
    }

    public void consoleLog(int indent) {

        for (int i = 0; i < indent; i++) {
            System.out.println(" ");
        }

        String name = getName() + (isFeuille() ? " (leaf)" : "") + (proximity != null ? " proximity: " + proximity : "");
        System.out.println(name);

        for(Cluster cluster: getFils()) {
            cluster.consoleLog(indent + 1);
        }
    }

    public double getTotalProximity() {

        Double proximity = getProximity() == null ? 0 : getProximity().getProximity();

        if (getFils().size() > 0) {

            proximity += fils.get(0).getTotalProximity();

        }

        return  proximity;
    }

}
