package clustering.process;

import clustering.models.Cluster;
import clustering.linkage.LinkageStrategy;

public interface ClusteringProcess {

    public Cluster performClustering(double[][] distances, String[] clusterNames,
                                     LinkageStrategy linkageStrategy);
}
