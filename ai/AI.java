package ai;
import card.blackjack.Hand;

public interface AI {
    public void init(int balance, int min, int max, int decks);
    public void message(String msg);
    public boolean keepPlaying(int balance);
    public void updateLimits(int min, int max);
    public int wager(int balance, int[] scores, int[] remainingScores, int[] wagers, int curRound, int finalRound);
    public char turn1(Hand hand, String info);
    public char turn(Hand hand);    
}

