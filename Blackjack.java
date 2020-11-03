import card.base.Deck;
import java.util.Scanner;
import java.util.ArrayList;
import ai.*;
import java.util.Collections;
import java.util.HashMap;
import card.base.Card;

public class Blackjack {

    /*Notes: Default max rounds for 1,2,3,4,5 players is 8,8,16,25,30
    default starting tokens is 2000. Easy number to work with.
    default minimum bet is 1/100 starting tokens
    default maximum bet is starting tokens/2
    After rounds 8,16,25,30, lowest player is eliminated if there's still more than one
    After round 8, minimum bet x2.5
    After round 16, minimum bet x2 (total x5)

    I rewrote the AI stuff before rewriting the game loop. The awkward use of scoreTable
    and arrays is because I guessed how I might pass this information before
    actually trying to do it game-side.
    */
 
    public static void main(String[] args) {
        int numPlayers, startingChips, maxRounds, min, max, decks, def, comPlayers=0;
        Deck deck;
        card.blackjack.Hand dealerHand;
        ArrayList<Player> players, allPlayers;
        ArrayList<Hand> hands;
        ArrayList<Score> scoreTable;
        Scanner scanner = new Scanner(System.in);

        //Welcome
        System.out.println("Welcome to Scott's blackjack table!");
        System.out.println("(Input validation is not complete in this demo.)");
        System.out.println("Default options are listed in (parentheses), press enter to select default.");

        //Game settings
        numPlayers=Integer.parseInt(getInput(scanner, "Enter number of players, recommended <8: "));
        def = defaultRounds(numPlayers);
        maxRounds=getInt(scanner, "Enter number of rounds ("+def+"): ", def);
        decks=getInt(scanner, "Enter number of decks (6): ",6);
        deck=new Deck(decks);
        startingChips=getInt(scanner, "Enter starting chips (2000): ",2000)        ;
        min=getInt(scanner, "Enter minimum bet ("+startingChips/100+"): ",startingChips/100);
        max=getInt(scanner, "Enter maximum bet ("+startingChips/2+"): ",startingChips/2);

        //Enter players
        players=new ArrayList<Player>(numPlayers);
        System.out.println("Enter player names. Enter 1 for easy COM player, 2 for hard COM player.");
        for(int i=0;i<numPlayers;i++){
            String name=getInput(scanner, "Enter name for player "+(i+1)+": ");
            AI controller;
            switch(name){
                case "1":   controller=new EasyAI();
                            name = "COM" + ++comPlayers;
                            break;
                case "2":   controller=new HardAI();
                            name = "COM" + ++comPlayers;
                            break;
                case "":    name="Player " + (i+1);
                default:    controller=new ConsolePlayer(scanner);
            }
            players.add(new Player(name, startingChips, controller));
            players.get(i).setup(min, max, decks);
        }
        allPlayers=(ArrayList<Player>) players.clone();
        Collections.shuffle(players);

        //Main game loop
        for(int round=1;round<=maxRounds && players.size()>0;round++){
            if(round==8){min=Math.round(min*2.5f);}
            if(round==16){min*=2;}
            hands=new ArrayList<Hand>(players.size()*2);
            System.out.println("** ROUND "+round+"/"+maxRounds+" **");

            //Get wager from each player while building hands array.
            for(Player p : players){
                Hand hand=new Hand(p);
                scoreTable = generateScoreTable(allPlayers, players, hands, p);
                p.message(p.getName()+", your turn to wager.");
                hand.setWager(p.wager(scoreTable, round, maxRounds));
                p.decBalance(hand.getWager());
                hands.add(hand);
                System.out.println(p.getName()+" wagers "+hand.getWager());
            }
            
            //draw dealer hand.
            dealerHand=new card.blackjack.Hand();
            dealerHand.add(deck.draw());
            dealerHand.add(deck.draw());

            //draw initial cards for all hands
            for(Hand h : hands){
                h.add(deck.draw());
                h.add(deck.draw());
            }

            //for loop over hands because a split might shift the positions midgame
            for(int i=0;i<hands.size();i++){
                System.out.println(hands.get(i).getPlayer().getName() + "'s turn:");
                StringBuilder info = new StringBuilder("D: " + dealerHand.display());
                for(Hand h : hands){
                    if(h==hands.get(i)){
                        info.append(" >YOU<");
                    } else {
                        info.append(" [" + h.getPlayer().getName() + " " + h.display() + "]");
                    }
                }
                boolean done=false;
                Hand h=hands.get(i);
                h.reveal();
                Player p=h.getPlayer();
                if(h.score()==21){
                    System.out.println(p.getName()+" was dealt a blackjack!");
                    continue;
                }
                if(h.size()==2){ //Stops us from treating split decks as an extra first turn.
                    switch(p.turn1(h, info.toString())){
                        case 'u':   p.incBalance(h.getWager()-h.getWager()/2);
                                    h.setWager(h.getWager()/2);
                                    System.out.println(p.getName() + " surrenders at " + h.score()+".");
                                    done=true;
                                    break;
                        case 'd':   p.decBalance(h.getWager());
                                    h.doubleWager();
                                    h.add(deck.draw());
                                    System.out.println(p.getName()+" doubles their bet and draws "+Card.display(h.get(2)));                                
                                    done=true;
                                    break;
                        case 'p':   p.decBalance(h.getWager());
                                    hands.add(i+1, new Hand(p));
                                    hands.get(i+1).add(h.remove(1));
                                    hands.get(i+1).setWager(h.getWager());
                                    System.out.println(p.getName()+" splits their hand.");
                                    break;
                        case 's':   done=true;
                                    System.out.println(p.getName()+" stays at "+h.score()+".");
                                    break;
                        case 'h':   System.out.println(p.getName()+" hits.");
                                    break;
                        default:    System.out.println("Error: invalid input. "+p.getName()+" stays.");
                                    done=true;
                                    break;
                    }
                }
                while(!done){
                    h.add(deck.draw());
                    if(h.score()>21){
                        System.out.println(p.getName()+" goes bust!");
                        break;
                    }
                    if (h.score()==21){
                        System.out.println(p.getName()+" stays on 21.");
                        break;
                    }
                    switch(p.turn(h)){
                        case 's':   done=true;
                                    System.out.println(p.getName()+" stays at "+h.score()+".");
                                    break;
                        case 'h':   System.out.println(p.getName()+" hits.");
                                    break;
                        default:    System.out.println("Error: invalid input. "+p.getName()+" stays.");
                                    done=true;
                                    break;
                    }
                }
            }

            //Dealer resolves their hand and bets are resolved accordingly.
            if(dealerHand.score()==21){
                System.out.println("Dealer blackjack!");
                for(Hand h : hands){
                    if(h.score()==21 && h.size()==2){
                        h.getPlayer().incBalance(h.getWager());
                    }
                }
            } else {
                while(Dealer.turn(dealerHand)=='h'){
                    dealerHand.add(deck.draw());
                }
                int dealerScore=dealerHand.score()>21 ? 0 : dealerHand.score();
                for(Hand h : hands){
                    if (h.score()==21 && h.size()==2){h.getPlayer().incBalance(Math.round(h.getWager()*2.5f));}
                    else if (h.score()>dealerScore && h.score()<=21){h.getPlayer().incBalance(h.getWager()*2);}
                    else if (h.score()==dealerScore){h.getPlayer().incBalance(h.getWager());}
                }
            }

            //Display the final state of the table before the next round.
            dealerHand.reveal();
            StringBuilder info = new StringBuilder("D: " + dealerHand.display());
            for(Hand h : hands){
                info.append(" [" + h.getPlayer().getName() + " " + h.display() + "]");
            }
            System.out.println("Final hands for round "+round+":\n"+info.toString());
            ArrayList<Player> copyPlayers=(ArrayList<Player>) players.clone();
            copyPlayers.sort(Collections.reverseOrder());
            info=new StringBuilder("Scores: ");
            for (Player p : copyPlayers){
                info.append("[" + p.getName() + " " + p.getBalance() + "]");
            }
            System.out.println(info.toString());

            //end-of-round processing
            if(round==8){min=Math.round(min*2.5f);}
            if(round==16){min*=2;}
            endOfRound(players, min, round, maxRounds);
            
            if(players.size()>0){
                players.add(players.remove(0));
                for(Player p : players){
                    p.updateLimits(min, max);
                }
                deck.shuffle();
            }
        }
            System.out.println("Game has ended! Final scores:");

        allPlayers.sort(null);
        for(int i=allPlayers.size()-1;i>=0;i--){
            System.out.println(allPlayers.get(i).getBalance() + "\t" + allPlayers.get(i).getName());
        }
        
        scanner.close();
    }

    static String getInput(Scanner scanner, String msg){
        System.out.print(msg);
        return scanner.nextLine();
    }

    static int defaultRounds(int players){
        switch(players){
            case 1: 
            case 2: return 8;
            case 3: return 16;
            case 4: return 25;
            case 5:
            default: return 30;
        }
    }

    static int getInt(Scanner scanner, String msg, int def){
        System.out.print(msg);
        String input=scanner.nextLine();
        if (input.equals("")){return def;}
        return Integer.parseInt(input);
    }

    static ArrayList<Score> generateScoreTable(ArrayList<Player> allPlayers, ArrayList<Player> curPlayers, ArrayList<Hand> hands, Player curPlayer){
        HashMap<Player, Score> map=new HashMap<Player, Score>();
        for(Player p : allPlayers){
            map.put(p,new Score(p.getName(), p.getBalance(), false, 0));
        }
        for(Player p : curPlayers){
            map.get(p).playing=true;
        }
        for(Hand h : hands){
            map.get(h.getPlayer()).wager=h.getWager();
        }
        map.remove(curPlayer);
        return new ArrayList<Score>(map.values());
    }

    static void endOfRound(ArrayList<Player> players, int min, int round, int lastRound){
        ArrayList<Integer> remove=new ArrayList<Integer>();
        int lowestBalance=Integer.MAX_VALUE, lastPlace=0, removed=0;
        boolean tie=true;
        int numPlayers=players.size();

        for(int i=0;i<numPlayers;i++){
            if(players.get(i).getBalance()<min){
                remove.add(i);
                continue;
            }
            if(players.get(i).getBalance()==lowestBalance){
                tie=true;
            }
            if(players.get(i).getBalance()<lowestBalance){
                lowestBalance=players.get(i).getBalance();
                tie=false;
                lastPlace=i;
            }
        }
        
        if((round == 8 || round == 16 || round == 25 || round == 30) && (!tie && remove.size()<players.size()-1)){remove.add(lastPlace);}

        //System.out.println("Players to remove: " + remove);
        if (remove.size()>0){
            int i=0;
            while (i<players.size()){
                if(remove.contains(i+removed)){//System.out.print("Removing index "+i+": ");
                    System.out.println(players.get(i).getName()+" has been eliminated with score "+players.get(i).getBalance());
                    players.remove(i);
                    removed++;
                } else {i++;}
            }
        }        
        return;
    }
}