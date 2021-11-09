package clustering.models;

import java.util.*;

public class ProximityDic {

    private Map<String, Node> pairHash;

    private PriorityQueue<Node> data;

    private class Node implements Comparable<Node> {
        final ClusterEven pair;
        final String hash;
        boolean removed = false;

        Node(ClusterEven p) {
            pair = p;
            hash = hashCodeEven(p);
        }

        @Override
        public int compareTo(Node o) {
            return pair.compareTo(o.pair);
        }

        @Override
        public String toString() {
            return hash;
        }
    }

    public ProximityDic() {
        data = new PriorityQueue<Node>();
        pairHash = new HashMap<String, Node>();
    }

    public List<ClusterEven> toList() {
        List<ClusterEven> clusterEvens = new
                ArrayList<ClusterEven>(data.size());

        for (Node node : data) {
            clusterEvens.add(node.pair);
        }

        return clusterEvens;
    }

    public ClusterEven findByCodeEven(Cluster cluster1,
                                      Cluster cluster2) {
        String code = hashCodeEven(cluster1, cluster2);
        return pairHash.get(code).pair;
    }

    public ClusterEven removeFirst() {
        Node node = data.poll();
        while (node != null && node.removed) {
            node = data.poll();
        }

        if (node == null) {
            return null;
        }

        ClusterEven lien = node.pair;
        pairHash.remove(node.hash);
        return lien;
    }

    public boolean remove(ClusterEven lien) {
        Node nodeToBeRemoved = pairHash.remove(hashCodeEven(lien));

        if (nodeToBeRemoved == null) {
            return false;
        }

        nodeToBeRemoved.removed = true;

        return true;
    }

    public boolean add (ClusterEven lien) {
        Node node = new Node(lien);
        Node existingItem = pairHash.get(node.hash);

        if (existingItem != null) {
            System.err.println("hashCode = " + existingItem.hash +
                    " adding redundant link:" + lien + " (exist:" + existingItem + ")");
            return false;
        } else {
            pairHash.put(node.hash, node);
            data.add(node);
            return true;
        }
    }

    public Double minProximity() {
        Node peekNode = data.peek();

        if (peekNode != null)
            return peekNode.pair.getLinkageDistance();
        else
            return null;
    }

    private String hashCodeEven(ClusterEven clusterEven) {
        return  hashCodeEven(clusterEven.getLeftCluster(),
                clusterEven.getRightCluster());
    }

    private String hashCodeEven(Cluster leftCluster, Cluster rightCluster) {
        return hashCodeEvenNames(leftCluster.getName(), rightCluster.getName());
    }

    private String hashCodeEvenNames(String leftName, String rightName) {
        if (leftName.compareTo(rightName) < 0)
            return leftName + "~~~" + rightName;
        else
            return rightName + "~~~" + leftName;
    }

    @Override
    public String toString() {
        return data.toString();
    }


}
