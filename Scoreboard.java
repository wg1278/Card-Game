import java.util.Arrays;
import java.util.Collections;

/*
 * Scoreboard is an array of (playerID, playerScore) groupings
 * Functionality includes descending order sorting by score, print, and player retrieval 
 */
public class Scoreboard{
	
	// private helper class. Each node is a comparable object holding the player id and score
	private class ScoreNode implements Comparable<ScoreNode> {
		private String id;
		private int score;
		
		
		private ScoreNode(String id, int score){
			this.id = id;
			this.score = score;
		}
		
		private ScoreNode(String id){
			this.id = id;
			this.score = 0;
		}
		
		@Override
		public int compareTo(ScoreNode n){
			if(this == null)
				throw new NullPointerException();
			
			if(this.score > n.score)
				return 1;
			else{
				if(this.score < n.score)
					return -1;
				else{
					if(this.id.compareTo(n.id) > 0)
						return 1;
					else{
						if(this.id.compareTo(n.id) < 0)
							return -1;
						else
							return 0;
					}
				}
			}
		}
		
		
		public boolean equals(ScoreNode other){
		    boolean result;
		    if((other == null) || (getClass() != other.getClass())){
		        result = false;
		    } // end if
		    else{
		        ScoreNode otherScoreNode = (ScoreNode)other;
		        result = this.id.equals(other.id) &&  this.score == other.score;
		    } // end else

		    return result;
		} // end equals
		
		public String toString(){
			return "ID: " + this.id + ", Score: " + this.score;
		}
	}
	
	private int numPlayers;
	private ScoreNode[] players;
	private int capacity;
	
	public Scoreboard(int numPlayers){
		this.numPlayers = numPlayers;
		players = new ScoreNode[this.numPlayers];
		this.capacity = 0;
	}
	
	public void addPlayer(String id, int score){
		if(this.capacity < this.numPlayers){
			this.players[this.capacity] = new ScoreNode(id, score);
			capacity++;
		}
		else
			System.out.println("Scoreboard is full. Player not added!");
	}
	

	// upon updating a score, the scoreboard is resorted in descending order to find
	// highest and 2nd highest score for winning conditions checking.
	public void sort(){
		 Arrays.sort(this.players, Collections.reverseOrder());
	}
	
	// prints sorted / ranked scoreboard and the end of a game round
	public void printScoreboard(){
		System.out.println("\nScoreboard");
		for(ScoreNode s : players)
			System.out.println(s.toString());
	}
	
	// assume sorted upon call. O(1) access to highest
	public int getHighestScore(){
		if(this.players[0] == null)
			return Integer.MIN_VALUE;
		else
			return this.players[0].score;
	}
	
	public String getHighestScorePlayer(){
		if(this.players[0] == null)
			return null;
		else
			return this.players[0].id;
	}
	
	// assume sorted upon all. O(1) access to next highest
	public int getNextHighestScore(){
		if(this.players[1] == null)
			return Integer.MIN_VALUE;
		else
			return this.players[1].score;
	}
	
	public String getNextHighestScorePlayer(){
		if(this.players[1] == null)
			return null;
		else
			return this.players[1].id;
	}
	
	// select id; if exists, increase score by scoreChange
	// sorts (descending) if a score changes
	public void updatePlayerScore(String id, int scoreChange){
		for(int i = 0; i < this.numPlayers; i++){
			if(this.players[i].id.equals(id)){
				int newScore = this.players[i].score + scoreChange;
				if(newScore < 0)
					newScore = 0;
				this.players[i].score = newScore;
				this.sort();
				return;
			}
		}
		System.out.println("ID not found; no update occured!");
	}
	
	
	// example usage of class
	public static void main(String[] args){
		Scoreboard sb = new Scoreboard(4);
		sb.addPlayer("Player 1", 4);
		sb.addPlayer("Player 2", 6);
		sb.addPlayer("Player 3", 9);
		sb.addPlayer("Player 4", 1);
		sb.sort();
		sb.printScoreboard();
		
		sb.updatePlayerScore("Player 1", 10);
		sb.sort();
		sb.printScoreboard();
		
	}
}
