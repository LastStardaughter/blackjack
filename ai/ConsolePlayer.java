package ai;
import java.util.Scanner;
import java.util.Arrays;
import card.blackjack.Hand;
import card.base.Card;
import java.util.ArrayList;

public class ConsolePlayer implements AI {
    int balance, min, max, wager;
    Scanner scanner;

    public ConsolePlayer(Scanner scanner){
        this.scanner=scanner;
    }
    
    public void init(int balance, int min, int max, int decks){
        this.min = min;
        this.max = max;
    }

    public void message(String msg){
        System.out.println(msg);
    }

    public boolean keepPlaying(int balance){
        System.out.print("Do you wish to keep playing? (Y)es or quit: ");
        String input = scanner.nextLine();
        if(input.toLowerCase().equals("y")) {return true;}
        return false;
    }

    public void updateLimits(int min, int max){
        this.min = min;
        this.max = max;
    }

    //public int wager(int balance, int[] scores, int[] remainingScores, int[] wagers, int curRound, int finalRound){
    public int wager(int balance, ArrayList<Score> scoreTable, int curRound, int finalRound){
        //int[] scores=null, remainingScores=null, wagers=null;
        int[][] arrays = {null, null, null};
        this.balance=balance;
        Score.generateArrays(scoreTable, arrays);
        int[] scores=arrays[0], remainingScores=arrays[1], wagers=arrays[2];
        Arrays.sort(remainingScores);
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
        wager = Math.max(wager, min);
        wager = Math.min(wager, max);
        this.balance-=wager;
        return wager;
    }

    public char turn1(Hand hand, String info){
        boolean[] values={false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        boolean pair = false, canDouble=balance>=hand.getWager();
        for(String card : hand){
            int val=Card.value(card);
            if(values[val]){
                pair = true;
            }
            values[val]=true;
        }
        System.out.println(info);
        System.out.print("Your starting hand is: " + hand.display() + " (" + hand.score() + ") ");
        while(true){
            System.out.print("[S]tay, [H]it, [D]ouble, S[p]lit, S[u]rrender?: ");
            char response=scanner.nextLine().toLowerCase().charAt(0);
            switch(response){
                case 's':
                case 'h':
                case 'u':   return response;
                case 'p':   if(!pair){System.out.println("You cannot split this hand.");}else if(pair && canDouble){
                    return response;
                }
                case 'd':   if(!canDouble){System.out.println("You don't have enough chips.");}else if (canDouble){
                    return response;
                }
                default:
            }
        }
    }


    public char turn(Hand hand){
        System.out.print(hand.display() + " (" + hand.score() + ") ");
        while(true){
            System.out.print("[S]tay or [H]it?: ");
            char response=scanner.nextLine().toLowerCase().charAt(0);
            switch(response){
                case 's':
                case 'h':   return response;
                default:
            }
        }
    }

    private void printArray(int[] arr){
        if(arr.length==0){return;}
        for(int i=arr.length-1; i > 0; i--){
            System.out.print(i + ", ");
        }
        System.out.print(arr[0]);
    }
    
}
