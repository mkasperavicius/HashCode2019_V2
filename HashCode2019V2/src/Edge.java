public class Edge {
    final int id;
    final Vertex prev;
    final Vertex next;
    final int weight;

    public Edge(int id, Vertex prev, Vertex next) {
        this.id = id;
        this.prev = prev;
        this.next = next;
        //could also be prev.img.getCommon(next.img);
        this.weight = prev.img.getScore(next.img);
    }

    @Override
    public String toString() {
        return prev + " " + next;
    }
}
