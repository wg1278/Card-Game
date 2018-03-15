# Card-Game
Object oriented card game involving 2-4 players drawing a high card each round.
 
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
