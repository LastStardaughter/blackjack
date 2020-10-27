//Cards are internally represented as strings
//String of "JOKER" is a joker.
//The first 1 or 2 characters is the value (A, 2-10, J, Q, K)
//The last character is the suit (C, D, H, S)

//Representing cards as an instantiated class, it feels off somehow, even if they are physical objects.
//Something inside me wants to make this a static class that operates on the Strings the cards are actually represented as...
package card.base;

public class Card {
    protected static final String JOKER="JOKER";
    private String card;
    
    public Card(String card){
        this.card=card;
    }

    //This identifies a card's value by a number. It is not assumed to equal game point value.
    //Depending on the specific game, you could override this method or have processing elsewhere take this int then work out the point value.
    public int value(){
        if (card.equals(JOKER)){
            return -1;
        }
        switch(card.charAt(0)){
            case 'A':return 1;
            case 'J':return 11;
            case 'Q':return 12;
            case 'K':return 13;
            case '1':return 10;
            default:return Integer.parseInt(card.substring(0,1));
        }
    }

    public String suit(){
        if(card.equals(JOKER)){return "";}
        if(card.charAt(0)=='1'){return suitFromChar(card.charAt(2));}
        return suitFromChar(card.charAt(1));
    }

    public String name(){
        if(card.equals(JOKER)){return "Joker";}
        return stringValue() + " of " + suit();
    }

    public String stringValue(){
        if (card.equals(JOKER)){return "Joker";}
        switch(card.charAt(0)){
            case 'A':return "Ace";
            case 'J':return "Jack";
            case 'Q':return "Queen";
            case 'K':return "King";
            case '1':return "10";
            default:return card.substring(0,1);
        }
    }

    private static String suitFromChar(char a){
        switch(a){
            case 'C':return "Clubs";
            case 'D':return "Diamonds";
            case 'H':return "Hearts";
            case 'S':return "Spades";
            default:return "Unknown";
        }
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Card)) { 
            return false; 
        } 
        Card that = (Card) o;
        return this.card.equals(that.card);
    }

    @Override
    public int hashCode(){
        char s, v=card.charAt(0);
        if(v=='1'){s=card.charAt(2);} else {s = card.charAt(1);}
        return (((int) s << 16) | ((int) v));
    }
}
