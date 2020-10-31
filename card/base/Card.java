/*
Cards are internally represented as strings
String of "JOKER" is a joker.
The first 1 or 2 characters is the value (A, 2-10, J, Q, K)
The last character is the suit (C, D, H, S)

Since I already had Deck operating with strings, I have to do logic on strings anyway, even if I had Card store them as something else.
I'm gonna follow my gut and make this a non-instantiated class that operates on the Strings the cards are actually represented as...
I feel that for a real application, any code dealing with stuff that game doesn't use, like jokers, should be stripped out.
*/
package card.base;

public class Card {
    protected static final String JOKER="JOKER";

    //This identifies a card's value by a number. It is not assumed to equal game point value.
    //Depending on the specific game, you could override this method or have processing elsewhere take this int then work out the point value.
    public static int value(String card){
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

    public static String suit(String card){
        if(card.equals(JOKER)){return "";}
        if(card.charAt(0)=='1'){return suitFromChar(card.charAt(2));}
        return suitFromChar(card.charAt(1));
    }

    public static String name(String card){
        if(card.equals(JOKER)){return "Joker";}
        return stringValue(card) + " of " + suit(card);
    }

    public static String stringValue(String card){
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

    public static String display(String card){
        if(card.equals(JOKER)){return "JK";}
        char val = card.charAt(0), suit=card.charAt(1);
        if(val=='1'){
            val = '➓';
            suit = card.charAt(2);
            }
        switch(suit){
            case 'C':suit='♧';
                break;
            case 'D':suit='♦';
                break;
            case 'H':suit='♥';
                break;
            case 'S':suit='♤';
                break;
            default:suit='?';
        }
        char temp[]={val, suit};
        return new String(temp);
    }
}
