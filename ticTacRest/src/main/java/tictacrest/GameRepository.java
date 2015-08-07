package tictacrest;

import java.util.HashMap;

public class GameRepository {
	private HashMap<Integer, TicTacToeGame> games = new HashMap<Integer, TicTacToeGame>();
	private Integer maxGameId = -1;
	
	public GameRepository(){
		
	}
	
	public void addGame(Integer gameId, TicTacToeGame game){
		this.getGames().put(gameId, game);
		if (gameId > this.getMaxGameId()){
			this.setMaxGameId(gameId);
		}
	}
	
	public TicTacToeGame getGame(Integer gameId){
		return this.getGames().get(gameId);
	}

	public HashMap<Integer, TicTacToeGame> getGames() {
		return games;
	}
	public void setGames(HashMap<Integer, TicTacToeGame> games) {
		this.games = games;
	}
	public Integer getMaxGameId() {
		return maxGameId;
	}
	public void setMaxGameId(Integer maxGameId) {
		this.maxGameId = maxGameId;
	}

}
