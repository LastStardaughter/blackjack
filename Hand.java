public class Hand extends card.blackjack.Hand {
    private static final long serialVersionUID = 0L;
    private Player player;

    public Hand(Player player){
        this.player=player;
    }

    public Player getPlayer(){
        return player;
    }

}
