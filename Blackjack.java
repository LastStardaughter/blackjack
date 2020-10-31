import card.blackjack.*;
import card.base.Card;
import card.base.Deck;
import java.util.Scanner;

public class Blackjack {

    //Notes: Default max rounds for 1,2,3,4,5 players is 8,8,16,25,30
    //Max players is 7
    //default starting tokens is 200. Easy number to work with.
    //default minimum bet is 1/100 starting tokens
    //default maximum bet is starting tokens/2
    //After rounds 8,16,25,30, lowest player is eliminated if there's still more than one
    //After round 8, minimum bet x2.5
    //After round 16, minimum bet x2 (total x5)
 
    public static void main(String[] args) {
        int numPlayers = 1, games=0;
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean done;

        while(true){
            done=false;
            System.out.println("Game " + ++games);
            //WIP: Some comments are todo entries, not explanations
            //Prompt for number of players
            //Create game with 1 player and dealer. Player has some default amount of tokens available to bet.
            Deck deck=new Deck();
            Hand playerHand=new Hand();
            System.out.print("Place your bet (int): ");
            playerHand.wager=Integer.parseInt(scanner.nextLine());
            Hand dealerHand=new Hand();
            //All players and the dealer are dealt their initial cards.
            playerHand.add(deck.draw());
            playerHand.add(deck.draw());
            dealerHand.add(deck.draw());
            dealerHand.add(deck.draw());
            //Iterate through list of players
            //For each player, show cards and prompt for first-turn action
            System.out.println("Dealer card: " + Card.name(dealerHand.get(1)));
            System.out.println("Your cards: " + Card.name(playerHand.get(0)) + ", " + Card.name(playerHand.get(1)) + ". Score: " + playerHand.score());
            System.out.print("(S)tay, (H)it, (D)ouble, or (Q)uit? ");
            input = scanner.nextLine().toLowerCase();
            if(input.equals("q")){return;}
            if(input.equals("h")){
                playerHand.add(deck.draw());
                System.out.println("You drew a " + Card.name(playerHand.get(playerHand.size()-1)) + ". Score: " + playerHand.score());
            }
            if(input.equals("s")){done=true;}
            if(input.equals("d")){
                playerHand.wager*=2;
                playerHand.add(deck.draw());
                System.out.println("You drew a " + Card.name(playerHand.get(playerHand.size()-1)) + ". Score: " + playerHand.score());
                done=true;
            }
            if(playerHand.score()>=21){
                System.out.println("Bust!");
                done=true;
            }
            //If they didn't stay or double, keep asking hit/stay until they go bust
            while(!done){
                System.out.print("(S)tay or (H)it?");
                input = scanner.nextLine().toLowerCase();
                if(input.equals("s")){done=true;}
                if(input.equals("h")){
                    playerHand.add(deck.draw());
                    System.out.println("You drew a " + Card.name(playerHand.get(playerHand.size()-1)) + ". Score: " + playerHand.score());
                }
                if(playerHand.score()>21){
                    System.out.println("Bust!");
                    done=true;
                }
            }            
            //If the player split on the first turn, repeat process for their second hand
            //Players don't win or lose, HANDS win or lose, although the rewards go to the players
            //Dealer should still hit on "soft" 17...
            while(dealerHand.score()<17){
                dealerHand.add(deck.draw());
            }
            System.out.print("Dealer's final hand: ");
            for(String card : dealerHand){
                System.out.print(":"+Card.name(card)+":");
            }
            System.out.println("Score: " + dealerHand.score());
            if(playerHand.score()>21){
                System.out.println("You lose!");
                continue;
            }
            if(playerHand.score() > dealerHand.score() || dealerHand.score()>21){
                System.out.println("You win! Gain " + playerHand.wager*2 + " chips!");
                continue;
            }
            if(playerHand.score() == dealerHand.score()){
                System.out.println("Tie.");
                continue;
            }
            System.out.println("You lose!");
        }
    }
}