package card.blackjack;
import java.util.ArrayList;

public class Player {
    private int balance;
    private String name;
    private ArrayList<Hand> hands;

    public Player(String name, int balance){
        this.balance=balance;
        this.name=name;
    }   
    
}
