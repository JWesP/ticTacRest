package tictacrest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import com.fasterxml.jackson.annotation.JsonProperty;

@Root
public class TicTacToeGame {
	private static final int NUM_ROWS = 3;
	private static final int NUM_COLS = 3;
	
	public enum SquareState {_, X, O};
	
	@ElementArray
	private SquareState[][] gameState = new SquareState[NUM_ROWS][NUM_COLS];
	
	public TicTacToeGame(){
		for (int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++){
				this.getGameState()[i][j] = SquareState._;
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

}
