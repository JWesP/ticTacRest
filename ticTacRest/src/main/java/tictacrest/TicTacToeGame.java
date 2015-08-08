package tictacrest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

@Root
public class TicTacToeGame {
	private static Logger log = LoggerFactory.getLogger(TicTacToeGame.class);
	
	private static final int NUM_ROWS = 3;
	private static final int NUM_COLS = 3;
	
	public enum SquareState {_, X, O};
	
	@ElementArray
	private SquareState[][] gameState = new SquareState[NUM_ROWS][NUM_COLS];
	
	@Element
	private SquareState movedFirst = SquareState._;
	
	@Element
	private SquareState winner = SquareState._;
	
	public TicTacToeGame(){
		for (int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++){
				this.getGameState()[i][j] = SquareState._;
			}
		}
	}
	
	public Pair<Boolean, String> makeMove(String markStr, Integer row, Integer column) {
		/*
		 * Make sure the game hasn't already been won
		 */
		if (!this.getWinner().equals(SquareState._)){
			return new Pair<Boolean, String>(false, "This game was won by player " + this.getWinner() + 
					". No further moves allowed");
		}
		/*
		 * Make sure the mark is an X or an O
		 */
		SquareState mark = null;
		if (!markStr.toUpperCase().equals(SquareState.X.toString()) && 
				!markStr.toUpperCase().equals(SquareState.O.toString())){
			return new Pair<Boolean, String>(false, "Mark is not an X or an O");
		}
		else {
			mark = SquareState.valueOf(markStr.toUpperCase());
		}
		/*
		 * Make sure the row/col specified are valid
		 */
		if (!(row > -1 && row < NUM_ROWS) || !(column > -1 && column < NUM_COLS)){
			return new Pair<Boolean, String>(false, "Row and column must be values 0-" + (NUM_ROWS - 1));
		}
		/*
		 * Make sure the proposed space on the board is empty
		 */
		if (!this.getGameState()[row][column].equals(SquareState._)){
			return new Pair<Boolean, String>(false, "Specified game space is not empty");
		}
		/*
		 * Make sure the player isn't going out of turn
		 */
		if (!this.validTurn(mark)){
			return new Pair<Boolean, String>(false, "Player " + mark + " attempting to go out of turn");
		}
		/*
		 * Make the move
		 */
		this.getGameState()[row][column] = mark;
		/*
		 * Check to see if there is a winner
		 */
		this.checkWinner();
		return new Pair<Boolean, String>(true, null);
	}

	private void checkWinner() {
		/*
		 * Check diagonals
		 */
		SquareState middleSquare = this.getSquareState(1, 1);
		if (!middleSquare.equals(SquareState._) && 
				(this.getSquareState(0, 0).equals(middleSquare) &&
				this.getSquareState(2, 2).equals(middleSquare)) ||
				(this.getSquareState(0, 2).equals(middleSquare) &&
				this.getSquareState(2, 0).equals(middleSquare))){
			this.setWinner(middleSquare);
			return;
		}
		/*
		 * Check rows
		 */
		for (int i = 0; i < NUM_ROWS; i++){
			SquareState rightSquare = this.getSquareState(i, 0);
			if (!rightSquare.equals(SquareState._) && 
					this.getSquareState(i, 1).equals(middleSquare) &&
					this.getSquareState(i, 2).equals(middleSquare)){
				this.setWinner(rightSquare);
				return;
			}
		}
		/*
		 * Check columns
		 */
		for (int j = 0; j < NUM_COLS; j++){
			SquareState topSquare = this.getSquareState(0, j);
			if (!topSquare.equals(SquareState._) && 
					this.getSquareState(1, j).equals(topSquare) &&
					this.getSquareState(2, j).equals(topSquare)){
				this.setWinner(topSquare);
				return;
			}
		}
	}

	private boolean validTurn(SquareState mark) {
		Integer numXs = 0;
		Integer numOs = 0;
		for (int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++){
				if (this.getGameState()[i][j].equals(SquareState.X)){
					numXs++;
				}
				else if (this.getGameState()[i][j].equals(SquareState.O)){
					numOs++;
				}
			}
		}
		if (numXs == numOs){
			if (numXs == 0){
				this.setMovedFirst(mark);
				return true;
			}
			else {
				return this.getMovedFirst().equals(mark);
			}
		}
		else {
			if (Math.abs(numXs - numOs) > 1){
				return false;
			}
			else {
				if (numXs > numOs){
					return this.getMovedFirst().equals(SquareState.X) && mark.equals(SquareState.O);
				}
				else {
					return this.getMovedFirst().equals(SquareState.O) && mark.equals(SquareState.X);
				}
			}
		}
	}

	@JsonProperty
	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{game-state:[");
		for (int i = 0; i < NUM_ROWS; i++){
			json
				.append("[")
					.append(this.getGameState()[i][0]).append(",")
					.append(this.getGameState()[i][1]).append(",")
					.append(this.getGameState()[i][2])
				.append("]");
			if (i < NUM_ROWS - 1){
				json.append(",");
			}
		}
		json.append("]}");
		return json.toString();
	}

	public SquareState[][] getGameState() {
		return gameState;
	}
	public void setGameState(SquareState[][] gameState) {
		this.gameState = gameState;
	}
	public SquareState getSquareState(int row, int col){
		return this.gameState[row][col];
	}
	public SquareState getMovedFirst() {
		return movedFirst;
	}
	public void setMovedFirst(SquareState movedFirst) {
		this.movedFirst = movedFirst;
	}
	public SquareState getWinner() {
		return winner;
	}
	public void setWinner(SquareState winner) {
		this.winner = winner;
	}

}
