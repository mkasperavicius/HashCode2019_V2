import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.*;


public class ImageSlideshow {

    static ImageSlideshow imgSlide;


    public static void main(String args[]){
        String file = "d_pet_pictures.txt";
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


        System.out.println("Hash size: " + hash.size());
        for(int i = 0; i<imageList.size(); i++){
            Vertex vert = new Vertex(imageList.get(i));
            vertexHashtable.put(vert.id, vert);
            verticies.add(vert);
        }
        System.out.println("Verticies Done");

        int edgeId = 0;
//        for(String s : hash.keySet()) {
//            System.out.println(s + "\n" + "\n" + "\n");
//            System.out.println(hash.get(s).size());
//            while(hash.get(s).size() > 1) {
//                System.out.println(hash.get(s).size());
//                String idFirst = hash.get(s).removeFirst();
//                Vertex first = vertexHashtable.get(idFirst);
//                for(int k = 0; k<10 && k<hash.get(s).size(); k++) {
//                    String idLast = hash.get(s).get(k);
//                    Vertex last = vertexHashtable.get(idLast);
//                    Edge ed = new Edge(edgeId, first, last);
//                    Edge ed2 = new Edge(edgeId, last, first);
//                    if (!edges.contains(ed) && !edges.contains(ed2)) {
//                        edges.add(ed);
//                        edgeId++;
//                        System.out.println("Edge id : " + edgeId);
//                    }
//                }
//            }
//        }

        for(String s : hash.keySet()) {
            System.out.println(s + "\n" + "\n" + "\n");
            System.out.println(hash.get(s).size());
            String idFirst = hash.get(s).getFirst();
            Vertex first = vertexHashtable.get(idFirst);
            ArrayList<Edge> edgesV = new ArrayList<>();
            if(hash.get(s).size()>1) {
                for (String t : first.img.tags) {
                    int i = hash.get(t).indexOf(first.id);
                    hash.get(t).remove(i);
                    for (int k = 0; k < 1000 && (k+i) < hash.get(t).size(); k++) {
                        String idLast = hash.get(t).get(k + i);
                        Vertex last = vertexHashtable.get(idLast);
                        Edge ed = new Edge(edgeId, first, last);
                        Edge ed2 = new Edge(edgeId, last, first);
                        if (!edgesV.contains(ed) && !edgesV.contains(ed2)) {
                            edges.add(ed);
                            edgesV.add(ed);
                            edgeId++;
                            System.out.println("Edge id : " + edgeId);
                        }
                    }
                }
            }
        }
        System.out.println("Edges Done");

        Graph graph = new Graph(verticies, edges);
        Dijkstra dij = new Dijkstra(graph);
        dij.execute(verticies.get(0));
        LinkedList<Vertex> path = dij.getPath(verticies.get(verticies.size()-1));
        System.out.println("Done");

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }





        createSubmissionDij(path);
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
            FileWriter fw = new FileWriter("src/new_submission.txt");
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
            FileWriter fw = new FileWriter("src/new_submission.txt");
            fw.write(nums.size() + "\n");
            while(!nums.isEmpty()){
                fw.write(nums.removeFirst().id + "\n");
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
}
