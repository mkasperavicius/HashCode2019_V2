public class Edge {
    final int id;
    final Vertex prev;
    final Vertex next;
    final double weight;

    public Edge(int id, Vertex prev, Vertex next) {
        this.id = id;
        this.prev = prev;
        this.next = next;
        //could also be prev.img.getCommon(next.img);
        double score = prev.img.getScore(next.img);
        if(score == 0){
            score = 10;
        }
        else{
            score = 1/Math.pow(score,2);
        }
        this.weight = score;
    }

    @Override
    public String toString() {
        return prev + " " + next;
    }
}
