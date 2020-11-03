import ai.AI;
import ai.Score;
import java.util.ArrayList;

public class Player implements Comparable<Player> {
    private int balance;
    private String name;
    private AI controller;

    public Player(String name, int balance, AI controller){
        this.balance=balance;
        this.name=name;
        this.controller=controller;
    }

    public int getBalance(){return balance;}
    public void incBalance(int a){balance+=a;}
    public void decBalance(int a){balance-=a;}
    public String getName(){return name;}

    public void setup(int min, int max, int decks){controller.init(balance, min, max, decks);}
    public void message(String msg){controller.message(msg);}
    public boolean keepPlaying(){return controller.keepPlaying(balance);}
    public void updateLimits(int min, int max){controller.updateLimits(min, max);}
    public int wager(ArrayList<Score> scoreTable, int curRound, int finalRound){return controller.wager(balance, scoreTable, curRound, finalRound);}
    public char turn1(Hand hand, String info){return controller.turn1(hand, info);}
    public char turn(Hand hand){return controller.turn(hand);}

    public int compareTo(Player o){
        if(o==null){throw new NullPointerException("Cannot compare Player to null.");}
        return balance-o.balance;
    }
    
}
