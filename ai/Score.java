//Since this is just a Struct with no purpose but to bundle its member fields together, we just make them public.
package ai;
import java.util.ArrayList;

public class Score implements Comparable<Score>{
    public int score, wager;
    public String name;
    public boolean playing;

    public Score(String name, int score){
        this.score=score;
        this.name=name;
    }

    public Score(String name, int score, boolean playing, int wager){
        this.score=score;
        this.name=name;
        this.playing=playing;
        this.wager=wager;
    }

    public int compareTo(Score that){
        if(that==null){throw new NullPointerException("Cannot compare Score to null.");}
        return this.score-that.score;
    }

    //public static void generateArrays(ArrayList<Score> scoreTable, int[] scores, int[] remainingScores, int[] wagers){
    public static void generateArrays(ArrayList<Score> scoreTable, int[][] arrays){
        arrays[0] = new int[scoreTable.size()];
        int stillPlaying=0, numWagers=0, j=0;
        for(int i=0;i<scoreTable.size();i++){
            arrays[0][i]=scoreTable.get(i).score;
            if(scoreTable.get(i).playing){stillPlaying++;}
            if(scoreTable.get(i).wager!=0){numWagers++;}
        }
        arrays[1]=new int[stillPlaying];
        for(int i=0;j<stillPlaying;i++){
            if(scoreTable.get(i).playing){
                arrays[1][j++]=scoreTable.get(i).score;
            }
        }
        j=0;
        arrays[2]=new int[numWagers];
        for(int i=0;j<numWagers;i++){
            if(scoreTable.get(i).playing){
                arrays[2][j++]=scoreTable.get(i).wager;
            }
        }       
    }
}
