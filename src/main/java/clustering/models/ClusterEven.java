package clustering.models;

public class ClusterEven implements Comparable<ClusterEven> {


    private Cluster leftCluster;
    private Cluster rightCluster;
    private Double linkageDistance;

    public ClusterEven() { }

    public ClusterEven(Cluster leftCluster,
                       Cluster rightCluster,
                       Double linkageDistance) {
        this.leftCluster = leftCluster;
        this.rightCluster = rightCluster;
        this.linkageDistance = linkageDistance;
    }

    public Cluster getOtherCluster(Cluster c) {
        return leftCluster == c ? rightCluster : leftCluster;
    }


    public Cluster getLeftCluster() { return leftCluster; }

    public void setLeftCluster(Cluster leftCluster) {
        this.leftCluster = leftCluster;
    }

    public Cluster getRightCluster() {
        return rightCluster;
    }

    public void setRightCluster(Cluster rCluster) {
        this.rightCluster = rCluster;
    }

    public Double getLinkageDistance() {
        return linkageDistance;
    }

    public void setLinkageDistance(Double distance) {
        this.linkageDistance = distance;
    }

    public ClusterEven reverse() {
        return new ClusterEven(getRightCluster(), getLeftCluster(), getLinkageDistance());
    }

    @Override
    public int compareTo(ClusterEven o) {
        int result;
        if (o == null || o.getLinkageDistance() == null) {
            result = -1;
        } else if (getLinkageDistance() == null) {
            result = 1;
        } else {
            result = getLinkageDistance().compareTo(o.getLinkageDistance());
        }

        return result;
    }

    public Cluster agglomerate(int clusterIdx) {
        return agglomerate("clstr#" + clusterIdx);
    }


    public Cluster agglomerate(String name) {
        Cluster cluster = new Cluster(name);

        cluster.setProximity(new Proximity(getLinkageDistance()));
        //New clusters will track their children's leaf names; i.e. each cluster knows what part of the original data it contains
        cluster.addFilsNames(leftCluster.getFilsNames());
        cluster.addFilsNames(rightCluster.getFilsNames());
        cluster.addFil(leftCluster);
        cluster.addFil(rightCluster);
        leftCluster.setParent(cluster);
        rightCluster.setParent(cluster);

        Double lWeight = leftCluster.getWeightValue();
        Double rWeight = rightCluster.getWeightValue();
        double weight = lWeight + rWeight;
        cluster.getProximity().setWeight(weight);

        return cluster;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (leftCluster != null) {
            sb.append(leftCluster.getName());
        }
        if (rightCluster != null) {
            if (sb.length() > 0) {
                sb.append(" + ");
            }
            sb.append(rightCluster.getName());
        }
        sb.append(" : ").append(linkageDistance);
        return sb.toString();
    }

}
