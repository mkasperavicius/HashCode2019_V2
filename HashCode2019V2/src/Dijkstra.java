import java.util.*;

public class Dijkstra {
    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;

    public Dijkstra(Graph graph) {
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Vertex source) {
        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        predecessors = new HashMap<Vertex, Vertex>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertex node = getMaximum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMaximumDistance(node);
        }
    }

    private void findMaximumDistance(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getMaxDist(target) > getMaxDist(node) + getDistance(node, target)) {
                distance.put(target, getMaxDist(node) + getDistance(node, target));
                predecessors.put(target, node);
                System.out.println(node.id);
                unSettledNodes.add(target);
                if(getDistance(node, target) >= node.img.numParameters/2.5){
                    break;
                }
            }
        }
    }

    private int getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.prev.equals(node) && edge.next.equals(target)) {
                return edge.weight;
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.prev.equals(node)
                    && !isSettled(edge.next)) {
                neighbors.add(edge.next);
            }
        }
        return neighbors;
    }

    private Vertex getMaximum(Set<Vertex> vertexes) {
        Vertex maximum = null;
        for (Vertex vertex : vertexes) {
            if (maximum == null) {
                maximum = vertex;
            } else {
                if (getMaxDist(vertex) > getMaxDist(maximum)) {
                    maximum = vertex;
                }
            }
        }
        return maximum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    private int getMaxDist(Vertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MIN_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
            System.out.println(step.id);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }


}
