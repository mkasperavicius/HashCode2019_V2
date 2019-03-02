import java.util.ArrayList;

public class Image implements Comparable<Image> {
    String type;
    int numParameters;
    final String id;
    ArrayList<String> tags;

    public Image(String t, int np, int i){
        type = t;
        numParameters = np;
        id = Integer.toString(i);
        tags = new ArrayList<String>();
    }

    public Image(Image L, Image R) throws horizontalMergeError{
        if(!L.type.equals("V") || !R.type.equals("V")){
            throw new horizontalMergeError(L.id, R.id);
        }

        numParameters = L.numParameters + R.numParameters;
        id = L.id + " " + R.id;
        tags = L.tags;
        this.addTags(R.tags.toArray(new String[R.tags.size()]));
    }

    public void setTags(String [] t){
        for(int i=0; i<t.length; i++){
            tags.add(t[i]);
        }
        numParameters = tags.size();
    }


    public void addTags(String [] t){
        for(int i=0; i<t.length; i++){
            if(!tags.contains(t[i])){
                tags.add(t[i]);
            }
        }
        numParameters = tags.size();
    }

    @Override
    public int compareTo(Image comparestu) {
        int compareage=((Image)comparestu).numParameters;

        return compareage - this.numParameters ;
    }

    public int getScore(Image compareTo){
        int common = 0, s1diff = 0, s2diff = 0;

        ArrayList <String> s1 = this.tags;
        ArrayList <String> s2 = compareTo.tags;

        int score = 0;

        for (String str: s1){
            if (s2.contains(str)){
                common++;
            }
            else{
                s1diff++;
            }
        }
        for(String str: s2){
            if(!s1.contains(str)){
                s2diff++;
            }
        }

        score = Math.min(Math.min(common, s1diff), s2diff);

        return score;
    }
    public int getCommon(Image compareTo){
        int common = 0;
        ArrayList <String> s1 = this.tags;
        ArrayList <String> s2 = compareTo.tags;
        for (String str: s1) {
            if (s2.contains(str)) {
                common++;
            }
        }
        return common;
    }

    public class horizontalMergeError extends Exception {

        public horizontalMergeError(String i, String k) {
            super("Error merging horizontal horizontal image at: " + i + " , " + k);
        }

    }


}
