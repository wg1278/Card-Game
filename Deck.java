import java.util.Random;

/*
 * Deck implements the 56 card penalty deck. Functionality includes shuffling, card drawing without
 * replacement, and card value lookup
 * 
 * The deck design is an int array with length == number of cards. The int card value is stored
 * at each index.
 * 
 * The suit ordering is Spades > Hearts > Diamonds > Clubs
 * 
 * Given card numbers [1 to N]
 * The rank int value is (card - 1) / 4.
 * The suit int value is (card - 1) % 4.
 * 
 * Eg. 2 of clubs is int value 0, and A of spades is int value 51. 
 * The penalty cards are valued 52 through 55
 */
public class Deck {
	private int numCards = 56;
	protected int cardsRemaining;
	protected int cards[];
	protected String ranks[] = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A", "P"};
	protected String suits[] = {"Clubs", "Diamonds", "Hearts", "Spades"};
	protected Random rng = new Random();
	
	public Deck(){
		cards = new int[this.numCards];
		this.initializeDeck();
	}
	
	protected void initializeDeck(){
		for(int i = 0; i < this.numCards; i++)	
			cards[i] = i;
		this.cardsRemaining = this.numCards;
	}
	
	
	// cards are accessed from rank and suits arrays.
	public String getCardValue(int value){
		
		if(value < 0 || value > numCards){
			System.out.println("Invalid card value.");
			return null;
		}
		
		String rank, suit;
		rank = this.ranks[(value)/4];
		if(rank.equals("P"))
				return "Penalty Card";
		suit = this.suits[(value)%4];
		return "" + rank + " of " + suit;
	}
	
	// minimum penalty card value - e.g. returns 52 because 52 through 55 are penalty cards
	public int getPenaltyValue(){
		return (this.ranks.length - 1)*4;
	}
	
	// based on Knuth shuffle method (shuffles full deck)
	public void shuffle(){
		this.cardsRemaining = this.numCards;
		int swapIdx, tmp;
		for(int i = 0; i < cards.length; i++){
			swapIdx = rng.nextInt(cards.length);
			tmp = this.cards[swapIdx];
			this.cards[swapIdx] = this.cards[i];
			this.cards[i] = tmp;
		}
	}
	
	// draws card without replacement. CardsRemaining collects all used cards are 
	// front of array and samples from the remaining back cards.
	public Integer drawCard(){
		if(this.cardsRemaining > 0){
			int startIdx = this.numCards - this.cardsRemaining;
			int randIdx = rng.nextInt(this.cardsRemaining) + startIdx;
			
			int card = this.cards[randIdx];
			this.cards[randIdx] = this.cards[startIdx];
			this.cards[startIdx] = card;
			this.cardsRemaining--;
			return card;
		}
		System.out.println("Empty Deck!");
		return null;
	}
	
	// example usage of class
	public static void main(String[] args){
		Deck d = new Deck();
		d.shuffle();
		for(int card : d.cards)
			System.out.println(d.getCardValue(card));
	}
}
