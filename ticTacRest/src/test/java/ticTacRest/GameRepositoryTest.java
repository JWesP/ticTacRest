package ticTacRest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tictacrest.GameRepository;
import tictacrest.TicTacToeGame;

public class GameRepositoryTest {
	
	@Test
	public void testMaxGameId(){
		GameRepository gameRepo0 = new GameRepository();
		TicTacToeGame game = new TicTacToeGame();
		Integer gameId = gameRepo0.getMaxGameId() + 100;
		gameRepo0.addGame(gameId, game);
		
		/*
		 * Make sure the newly added game id is the new max id
		 */
		assertEquals(gameId, gameRepo0.getMaxGameId());
		
		/*
		 * Make sure the new max id persists to a new instantiation
		 */
		GameRepository gameRepo1 = new GameRepository();
		assertEquals(gameId, gameRepo1.getMaxGameId());
		
		gameRepo0.deleteGame(gameId);
	}
	
	@Test
	public void testAddGame(){
		GameRepository gameRepo0 = new GameRepository();
		TicTacToeGame game0 = new TicTacToeGame();
		game0.makeMove("X", 0, 0);
		game0.makeMove("O", 0, 1);
		game0.makeMove("X", 1, 1);
		game0.makeMove("O", 0, 2);
		int gameId = gameRepo0.getMaxGameId() + 1;
		gameRepo0.addGame(gameId, game0);
		
		/*
		 * Make sure the game comes back out of the repository 
		 * the same as it went in
		 */
		TicTacToeGame game1 = gameRepo0.getGame(gameId);
		assertEquals(game0, game1);
		
		/*
		 * Make sure the persisted game is also the same
		 */
		GameRepository gameRepo1 = new GameRepository();
		TicTacToeGame game2 = gameRepo1.getGame(gameId);
		assertEquals(game0, game2);
		
		gameRepo0.deleteGame(gameId);
	}
	
	@Test
	public void testDeleteGame(){
		GameRepository gameRepo0 = new GameRepository();
		TicTacToeGame game0 = new TicTacToeGame();
		int gameId = gameRepo0.getMaxGameId() + 1;
		gameRepo0.addGame(gameId, game0);
		gameRepo0.deleteGame(gameId);
		
		/*
		 * Make sure the game is gone
		 */
		TicTacToeGame game1 = gameRepo0.getGame(gameId);
		assertEquals(null, game1);
		
		/*
		 * Make sure the deletion is also true for a new instantiation of the repo
		 */
		GameRepository gameRepo1 = new GameRepository();
		TicTacToeGame game2 = gameRepo1.getGame(gameId);
		assertEquals(null, game2);
	}

}
