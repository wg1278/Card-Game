import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

/*
 * This is the driver class of the game. The main components are the game manager (this class),
 * the scoreboard, the players, and the deck.
 */
public class Game {

	private Player winner;
	private boolean isWon;
	private int round;
	private Scoreboard scores;
	private int[] cardsDrawn;
	private int numPlayers;
	private int penaltyValue;
	private int maxScoreValue;
	private Player[] players;
	private Deck deck;
	
	public Game(int penaltyValue, int maxScoreValue){
		this.penaltyValue = penaltyValue;
		this.maxScoreValue = maxScoreValue;
	}
	
	// utilizes exceptions to monitor correct input
	public void initializeGame(){
		deck = new Deck();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		System.out.println("Welcome to the card game!");
		
		boolean playersSet = false;
		Integer players = 0;
		do{
			System.out.println("\nPlease enter the integer number of players (2 to 4 players valid only): ");
			try{
				input = br.readLine();
				players = Integer.parseInt(input);
				if(players >= 2 && players <= 4){
					this.numPlayers = players;
					playersSet = true;
				}
				else
					throw new IOException();
			}
			catch (Exception e){
				System.out.println("The input was not formatted properly!");
			}
		}while(playersSet == false);
		
		this.players = new Player[this.numPlayers];
		this.scores = new Scoreboard(this.numPlayers);
		int playerCount = 1;
		do{
			System.out.println("Player " + playerCount + ", please enter your game id.");
			try{
				input = br.readLine().trim();
				if(Arrays.asList(this.players).contains(input))
					throw new IOException();
				
				this.players[playerCount - 1] = new Player(input, (char)playerCount);
				this.scores.addPlayer(input, 0);
				playerCount++;
				//if(playerCount > this.numPlayers)
					//br.close();
			}
			catch (IOException e){
				System.out.println("Player " + playerCount + ", this id has already been chosen!");
			}
		}while(playerCount <= this.numPlayers);
	}
	
	// handles main functionality
	// exceptions track correct input
	// each player is assigned a unique id and unique keyboard key {eg, Player 1 must enter 1)
	public void execute(){
		round = 1;
		cardsDrawn = new int[this.numPlayers];
		isWon = false;
		int count, card;
		String input;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(!isWon){
			deck.shuffle();
			System.out.println("\nRound: " + round);
			count = 1;
			while(count <= numPlayers){
				System.out.println("\nPlayer " + (count) + ", please enter " + (count) + " to draw a card.");
				try{
					input = br.readLine().trim();
					int val = Integer.parseInt(input);
					if(val == count){
						card = deck.drawCard();
						System.out.println("\nPlayer " + (count) + " drew the " + deck.getCardValue(card) + ".");
						this.cardsDrawn[val - 1] = card;
						count++;
					}
					else
						throw new IOException();
				}
				catch(Exception e){
					System.out.println("\nThe user key is invalid!");
				}
			}
			this.resolveScore();
			this.printScoreboard();
			this.determineWinner();
			round++;
		}
		
		try{
			br.close();
		}
		catch(Exception e){
			
		}
		System.out.println("\nThe winner is " + this.winner);
		System.out.println("Thanks for playing!");
		System.out.println("Feel free to check out my github: williamgrant95, where I add games/projects like this!");
	}
	
	// determines highest card and penalties and updates scoreboard accordingly
	public void resolveScore(){
		int highestCard = Integer.MIN_VALUE;
		int maxPlayer = Integer.MIN_VALUE;
		for(int i = 0; i < this.cardsDrawn.length; i++){
			if(this.cardsDrawn[i] > highestCard && this.cardsDrawn[i] < deck.getPenaltyValue()){
				highestCard = this.cardsDrawn[i];
				maxPlayer = i;
			}
			if(this.cardsDrawn[i] >= deck.getPenaltyValue()){
				this.players[i].updateScore(this.penaltyValue);
				this.scores.updatePlayerScore(this.players[i].getID(), this.penaltyValue);
			}
		}
		this.players[maxPlayer].updateScore(this.maxScoreValue);
		this.scores.updatePlayerScore(this.players[maxPlayer].getID(), this.maxScoreValue);
	}
	
	public void printScoreboard(){
		this.scores.printScoreboard();
	}
	
	public void determineWinner(){
		if(this.scores.getHighestScore() >= 21 && (this.scores.getHighestScore() - this.scores.getNextHighestScore()) >= 2){
			this.isWon = true;
			String highestPlayer = this.scores.getHighestScorePlayer();
			for(int i = 0; i < this.players.length; i++){
				if(this.players[i].getID().equals(highestPlayer)){
					this.winner = this.players[i];
					break;
				}
			}
		}
	}
	
	
/*	 * Driver method: = Game main
	 * 
	 * OOP Design: 
	 * - Game class handles functionality
	 * - Scoreboard class updates and outputs highest round scores in sorted order
	 * - Player class maintains IDs and scores for later retrieval
	 * - Deck class implements the modified penalty card deck and allows for shuffling with
	 * 	 replacement
	 * 
	 * Rules:
	 * - This game is built for 2 to 4 players.
	 * - For each round, players draw cards from a shuffled 56 card deck(52 normal cards +
	 *   4 penalty cards). Drawing is done without replacement.
	 * - Penalty cards lose players a point (min score is 0). Of the remaining non-penalty
	 *   players, the highest card adds 2 to his/her score.
	 * - Play stops when highest score has minimum of 21 points and is at least 2 points higher 
	 *   than 2nd highest scorer.
	 *   */
	 
	public static void main(String[] args){
		Game g = new Game(-1, 2);
		g.initializeGame();
		g.execute();
	}
}
