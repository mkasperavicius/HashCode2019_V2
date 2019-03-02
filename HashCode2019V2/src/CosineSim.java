import java.util.ArrayList;

public class CosineSim {
    static int break_point;
    static double finalScore;

    public CosineSim(){
        break_point = 100000;
        finalScore = 0;
    }

    public ArrayList<Image> sortAll(ArrayList<Image> imgs){
        ArrayList<Image> nums = new ArrayList<>();

        Image lastOne = null;
        for(int i = 0; i<imgs.size() && i<break_point; i++){
            int max_score = 0;
            System.out.println("I: " + i);
            int sets [] = new int[2];

            Image img1 = imgs.get(i);
            if(lastOne != null){
                img1 = lastOne;
            }

            for(int j=i+1; j< 10000/*imgs.size()*/; j++){
                if(!nums.contains(imgs.get(i)) && !nums.contains(imgs.get(j))){
                    Image img2 = imgs.get(j);
                    int score = img1.getScore(img2);
                    if(score > max_score){
                        max_score = score;
                        sets[0] = i;
                        sets[1] = j;
                    }
                    if (score >= img1.numParameters / 2){
                        break;
                    }
                    /*if (score==3){
                        break;
                    }*/
                }
            }

            if(max_score > 0){
                System.out.println(max_score);
                finalScore += max_score;
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
        System.out.println("Final Score is: " + finalScore);

        return nums;
    }
}
