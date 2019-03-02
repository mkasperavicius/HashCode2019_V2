import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.*;



public class ImageSlideshow {

    static ImageSlideshow imgSlide;
    static int maxGenerations = 10;


    public static void main(String args[]){
        String file = "e_shiny_selfies.txt";
        String [] images = readFile(file);

        ArrayList<Image> imageList = new ArrayList<>();
        ArrayList<Image> fList;

        imgSlide = new ImageSlideshow();

        for(int i=0; i<images.length; i++){
            String s = images[i].split(" ")[0];
            int j = Integer.parseInt(images[i].split(" ")[1]);
            Image img = new Image(s, j, i);
            String tags[] = images[i].split(" ", 3)[2].split(" ");
            img.setTags(tags);
            imageList.add(img);
        }

        imageList = combineVs(imageList);
        //CosineSim cos = new CosineSim();
        //fList = cos.sortAll(imageList);




        Hashtable<String, LinkedList<String>> hash = new Hashtable<>();
        mapTags(hash,imageList);

        ArrayList<Vertex> verticies = new ArrayList<>();
        Hashtable<String, Vertex> vertexHashtable = new Hashtable<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Vertex vertLast = new Vertex(imageList.get(750-1));
        Vertex vertFirst = new Vertex(imageList.get(0));


        System.out.println("Hash size: " + hash.size());
        for(int i = 0; i<imageList.size(); i++){
            Vertex vert = new Vertex(imageList.get(i));
            vertexHashtable.put(vert.id, vert);
            verticies.add(vert);
            //vertLast = vert;
        }
        System.out.println("Image list size: " + imageList.size());
        System.out.println("Vertex hashtable size: " + vertexHashtable.size());
        System.out.println("Vertex arraylist size: " + verticies.size());
        System.out.println("Verticies Done");

        GeneticAlgorithm ga = new GeneticAlgorithm(1000, 0.01, 0.7, 2, 5);

        Population population = ga.initPopulation(verticies.size());

        // Evaluate population
        ga.evalPopulation(population, verticies);

        Route startRoute = new Route(population.getFittest(0), verticies);
        System.out.println("Start Distance: " + startRoute.getDistance());

        // Keep track of current generation
        int generation = 1;
        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
            // Print fittest individual from population
            Route route = new Route(population.getFittest(0), verticies);
            System.out.println("G"+generation+" Best distance: " + route.getDistance());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population);

            // Evaluate population
            ga.evalPopulation(population, verticies);

            // Increment the current generation
            generation++;
        }

        System.out.println("Stopped after " + maxGenerations + " generations.");
        Route route = new Route(population.getFittest(0), verticies);
        System.out.println("Best distance: " + route.getDistance());


        int edgeId = 0;

//        for(String s : hash.keySet()) {
//            System.out.println(s + "\n");
//            System.out.println(hash.get(s).size());
//            if(hash.get(s).size() > 0) {
//                String idFirst = hash.get(s).getFirst();
//                Vertex first = vertexHashtable.get(idFirst);
//                ArrayList<Edge> edgesV = new ArrayList<>();
//                if (hash.get(s).size() > 1) {
//                    for (String t : first.img.tags) {
//                        int i = hash.get(t).indexOf(first.id);
//                        hash.get(t).remove(i);
//                        for (int k = 0; k < 10 && (k + i) < hash.get(t).size(); k++) {
//                            String idLast = hash.get(t).get(k + i);
//                            Vertex last = vertexHashtable.get(idLast);
//                            Edge ed = new Edge(edgeId, first, last);
//                            Edge ed2 = new Edge(edgeId, last, first);
//                            if (!edgesV.contains(ed) && !edgesV.contains(ed2)) {
//                                edges.add(ed);
//                                edgesV.add(ed);
//                                edgeId++;
//                                System.out.println("Edge id : " + edgeId);
//                            }
//                        }
//                    }
//                }
//            }
//        }

//        for(String s : hash.keySet()) {
//            System.out.println("\n" + s);
//            System.out.println(hash.get(s).size());
//            if(hash.get(s).size() > 0) {
//                String idFirst = hash.get(s).getFirst();
//                Vertex first = vertexHashtable.get(idFirst);
//                ArrayList<Edge> edgesV = new ArrayList<>();
//                if (hash.get(s).size() > 1) {
//                    for (String t : first.img.tags) {
//                        int i = hash.get(t).indexOf(first.id);
//                        hash.get(t).remove(i);
//                        for (int k = 0; k < hash.get(t).size(); k++) {
//                            String idLast = hash.get(t).get(k);
//                            Vertex last = vertexHashtable.get(idLast);
//                            Edge ed = new Edge(edgeId, first, last);
//                            Edge ed2 = new Edge(edgeId, last, first);
//                            if (k != i && !edges.contains(ed) && !edges.contains(ed2)) {
//                                edges.add(ed);
//                                //edgesV.add(ed);
//                                edgeId++;
//                                System.out.println("Edge id : " + edgeId);
//                            }
//                        }
//                    }
//                }
//            }
//        }
        System.out.println("Edges Done");

        Graph graph = new Graph(verticies, edges);
        //Dijkstra dij = new Dijkstra(graph);

        System.out.println("First" + vertFirst.id);
        //System.out.println("Last" + vertLast.id);


//        dij.execute(verticies.get(verticies.indexOf(vertFirst)));
//        LinkedList<Vertex> path = dij.getPath(verticies.get(verticies.indexOf(vertLast)));
        System.out.println("Done");

        //getEdge(path)







        //createSubmissionDij(path);
    }

    public static ArrayList<Image> combineVs(ArrayList<Image> imgs){
        ArrayList<Image> imageVS = new ArrayList<Image>();
        ArrayList<Image> imageHS = new ArrayList<Image>();

        for(Image i: imgs){
            if(i.type.equals("V")){
                imageVS.add(i);
            }
            else if(i.type.equals("H")){
                imageHS.add(i);
            }
        }

        Collections.sort(imageVS);
        if(imageVS.size()%2 == 1){
            imageVS.remove(imageVS.size() - 1);
        }


        ArrayList<Image> newImages = new ArrayList<Image>();
        while(!imageVS.isEmpty()){
            try{
                Image j = new Image(imageVS.remove(0), imageVS.remove(imageVS.size()-1));
                newImages.add(j);
            }
            catch (Exception e){
            }
        }

        while(!imageHS.isEmpty()){
            newImages.add(imageHS.remove(0));
        }

        Collections.sort(newImages);


        return newImages;
    }

    public static String[] readFile(String fileLocation) {
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/" + fileLocation));

            String line;
            int count = Integer.parseInt(br.readLine());

            String [] photos = new String[count];
            int index = 0;

            while((line = br.readLine()) != null){
                photos[index] = line;
                index ++;
            }
            return photos;

        } catch (Exception e){
        }
        return new String[4];
    }

    public static void createSubmissionCosine(ArrayList<Image> nums){
        try{
            FileWriter fw = new FileWriter("src/new_submission_2.txt");
            fw.write(nums.size() + "\n");
            for(int i=0; i<nums.size(); i++){
                fw.write(nums.get(i).id + "\n");
            }
            fw.close();
        } catch(Exception e){
        }
    }

    public static void createSubmissionDij(LinkedList<Vertex> nums){
        try{
            FileWriter fw = new FileWriter("src/new_submission.txt_2");
            fw.write(nums.size() + "\n");
            for(Vertex v : nums ){
                fw.write(v.id + "\n");
            }
            fw.close();
        } catch(Exception e){
        }
    }


    public static void mapTags(Hashtable<String, LinkedList<String>> hash,  ArrayList<Image> imgs){
        for(int i = 0; i < imgs.size(); i++){
            ArrayList <String> s = imgs.get(i).tags;
            for (String str: s) {
                if (!hash.containsKey(str)) {
                    LinkedList <String> ids = new LinkedList<>();
                    ids.add(imgs.get(i).id);
                    hash.put(str, ids);
                }
                else{
                    hash.get(str).add(imgs.get(i).id);
                }
            }
        }
    }
    public static Edge getEdge(Vertex node, Vertex target, ArrayList<Edge> edges) {
        for (Edge edge : edges) {
            if (edge.prev.equals(node)
                    && edge.next.equals(target)) {
                return edge;
            }
        }
        throw new RuntimeException("Should not happen");
    }
}
