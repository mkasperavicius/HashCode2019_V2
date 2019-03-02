import java.util.ArrayList;

public class Route {
    private Vertex route[];
    private double distance = 0;

    /**
     * Initialize Route
     *
     * @param individual
     *            A GA individual
     * @param vertices
     *            The cities referenced
     */
    public Route(Individual individual, ArrayList<Vertex> vertices) {
        // Get individual's chromosome
        int chromosome[] = individual.getChromosome();
        // Create route
        this.route = new Vertex[vertices.size()];
        for (int geneIndex = 0; geneIndex < chromosome.length; geneIndex++) {
            this.route[geneIndex] = vertices.get(chromosome[geneIndex]);
        }
    }

    /**
     * Get route distance
     *
     * @return distance The route's distance
     */
    public double getDistance() {
        if (this.distance > 0) {
            return this.distance;
        }

        // Loop over cities in route and calculate route distance
        double totalDistance = 0;
        for (int cityIndex = 0; cityIndex + 1 < this.route.length; cityIndex++) {
            totalDistance += this.route[cityIndex].img.getScore(this.route[cityIndex + 1].img);
        }

        //totalDistance += this.route[this.route.length - 1].img.getScore(this.route[0].img);
        this.distance = totalDistance;

        return totalDistance;
    }
}