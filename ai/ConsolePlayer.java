package ai;
import java.util.Scanner;
import java.util.Arrays;
import card.blackjack.Hand;

public class ConsolePlayer implements AI {
    int balance, min, max, wager;
    
    public void init(int balance, int min, int max, int decks){
        this.min = min;
        this.max = max;
    }

    public void message(String msg){
        System.out.println(msg);
    }

    public boolean keepPlaying(int balance){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you wish to keep playing? (Y)es or quit: ");
        String input = scanner.nextLine();
        scanner.close();
        if(input.toLowerCase().equals("y")) {return true;}
        return false;
    }

    public void updateLimits(int min, int max){
        this.min = min;
        this.max = max;
    }

    public int wager(int balance, int[] scores, int[] remainingScores, int[] wagers, int curRound, int finalRound){
        this.balance=balance;
        Arrays.sort(remainingScores);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Round " + curRound + "/" + finalRound + ". Opponent balances: ");
        printArray(remainingScores);
        if(wagers.length == 0){
            System.out.print("\nYou are first to wager.");
        } else {
            System.out.print("\nWagers: ");
            printArray(wagers);
        }
        System.out.print(" Enter wager (" + min + "-" + Math.min(max, balance) + "/" + balance + "): ");
        wager=Integer.parseInt(scanner.nextLine());
        scanner.close();
        wager = Math.max(wager, min);
        wager = Math.min(wager, max);
        return wager;
    }

    public char turn1(Hand hand, String info){
        Scanner scanner = new Scanner(System.in);
        System.out.println(info);
        

    }


    public char turn(Hand hand);  

    private void printArray(int[] arr){
        for(int i=arr.length-2; i > 0; i--){
            System.out.print(i + ", ");
        }
        System.out.print(arr[0]);
    }
    
}
