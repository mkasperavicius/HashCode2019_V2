import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.*;


public class ImageSlideshow {

    static ImageSlideshow imgSlide;
    static int break_point = 100000;

    public static void main(String args[]){
        String file = "b_lovely_landscapes.txt";
        String [] images = readFile(file);

        ArrayList<Picture> pictureList = new ArrayList<>();

        imgSlide = new ImageSlideshow();

        for(int i=0; i<images.length; i++){
            String s = images[i].split(" ")[0];
            int j = Integer.parseInt(images[i].split(" ")[1]);
            Picture img = new Picture(s, j, i);
            String tags[] = images[i].split(" ", 3)[2].split(" ");
            img.setTags(tags);
            pictureList.add(img);
        }

        pictureList = combineVs(pictureList);
        sortAll(pictureList);
    }

    public static void sortAll(ArrayList<Picture> imgs){
        ArrayList<Picture> nums = new ArrayList<>();

        Picture lastOne = null;
        for(int i = 0; i<imgs.size() && i<break_point; i++){
            int max_score = 0;
            System.out.println("I: " + i);
            int sets [] = new int[2];

            Picture img1 = imgs.get(i);
            if(lastOne != null){
                img1 = lastOne;
            }

            for(int j=i+1; j<imgs.size(); j++){
                if(!nums.contains(imgs.get(i)) && !nums.contains(imgs.get(j))){
                    Picture img2 = imgs.get(j);
                    int score = img1.getScore(img2);
                    if(score > max_score){
                        max_score = score;
                        sets[0] = i;
                        sets[1] = j;
                    }
                    if (score==3){
                        break;
                    }
                }
            }

            if(max_score > 0){
                System.out.println(max_score);
                if(lastOne != null){
                    nums.add(imgs.get(sets[0]));
                    nums.add(imgs.get(sets[1]));
                }
                else{
                    nums.add(imgs.get(sets[1]));
                }
                lastOne = imgs.get(sets[1]);
            }
        }

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

    public static ArrayList<Picture> combineVs(ArrayList<Picture> imgs){
        ArrayList<Picture> pictureVS = new ArrayList<Picture>();
        ArrayList<Picture> pictureHS = new ArrayList<Picture>();

        for(Picture i: imgs){
            if(i.type.equals("V")){
                pictureVS.add(i);
            }
            else if(i.type.equals("H")){
                pictureHS.add(i);
            }
        }


        Collections.sort(pictureVS);
        if(pictureVS.size()%2 == 1){
            pictureVS.remove(pictureVS.size() - 1);
        }


        ArrayList<Picture> newPictures = new ArrayList<Picture>();
        while(!pictureVS.isEmpty()){
            try{
                Picture j = new Picture(pictureVS.remove(0), pictureVS.remove(pictureVS.size()-1));
                newPictures.add(j);
            }
            catch (Exception e){
            }
        }

        while(!pictureHS.isEmpty()){
            newPictures.add(pictureHS.remove(0));
        }

        Collections.sort(newPictures);


        return newPictures;
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


}
