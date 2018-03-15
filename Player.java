/*
 * Basic implementation of player object holding a unique id and score
 */
public class Player {
	
	private String id;
	private int playerNumber;
	private int score = 0;
	private char responseKey;
	private int card;
	
	public Player(String id, int playerNumber){
		this.id = id;
		this.playerNumber = playerNumber;
		this.responseKey = (char) playerNumber;
	}
	
	public void updateScore(int i){
		score += i;
	}
	
	public int getScore(){
		return score;
	}
	
	public String getID(){
		return id;
	}
	
	public void card(int card){
		this.card = card;
	}
	
	public String toString(){
		return "Player " + playerNumber + ": {id: " + id + ", score: " + score + "}";
	}
}
