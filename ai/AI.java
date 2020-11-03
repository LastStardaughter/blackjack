package ai;
import card.blackjack.Hand;
import java.util.ArrayList;

public interface AI {
    public void init(int balance, int min, int max, int decks);
    public void message(String msg);
    public boolean keepPlaying(int balance);
    public void updateLimits(int min, int max);
    /*Originally I thought int[]s would be a good way to pass this data around and coded HardAI and BettingStrategy
    to expect it as such. This was a mistake.*/
    //public int wager(int balance, int[] scores, int[] remainingScores, int[] wagers, int curRound, int finalRound);
    public int wager(int balance, ArrayList<Score> scoreTable, int curRound, int finalRound);
    public char turn1(Hand hand, String info);
    public char turn(Hand hand);    
}

