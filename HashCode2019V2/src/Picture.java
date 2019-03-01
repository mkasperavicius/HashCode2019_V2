import java.util.ArrayList;

public class Picture implements Comparable<Picture> {
    String type;
    int numParameters;
    final String id;
    ArrayList<String> tags;

    public Picture(String t, int np, int i){
        type = t;
        numParameters = np;
        id = Integer.toString(i);
        tags = new ArrayList<String>();
    }

    public Picture(Picture L, Picture R) throws horizontalMergeError{
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

//        public void setID(String i){
//            id = i;
//        }

    public void addTags(String [] t){
        for(int i=0; i<t.length; i++){
            if(!tags.contains(t[i])){
                tags.add(t[i]);
            }
        }
        numParameters = tags.size();
    }

    @Override
    public int compareTo(Picture comparestu) {
        int compareage=((Picture)comparestu).numParameters;

        return compareage - this.numParameters ;
    }

    public int getScore(Picture compareTo){
        ArrayList<String> common = new ArrayList<>();
        ArrayList<String> s1diff = new ArrayList<>();
        ArrayList<String> s2diff = new ArrayList<>();

        ArrayList <String> s1 = this.tags;
        ArrayList <String> s2 = compareTo.tags;

        int score = 0;

        for (String str: s1){
            if (s2.contains(str)){
                common.add(str);
            }
            else{
                s1diff.add(str);
            }
        }
        for(String str: s2){
            if(!s1.contains(str)){
                s2diff.add(str);
            }
        }

        score = Math.min(Math.min(common.size(), s1diff.size()), s2diff.size());

        return score;
    }

    public class horizontalMergeError extends Exception {

        public horizontalMergeError(String i, String k) {
            super("Error merging horizontal horizontal image at: " + i + " , " + k);
        }

    }


}
