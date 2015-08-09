package ticTacRest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tictacrest.TicTacToeGame;
import tictacrest.TicTacToeGame.SquareState;

public class TicTacToeGameTest {
	
	@Test
	public void testValidMove(){
		TicTacToeGame game = new TicTacToeGame();
		
		boolean validMove = game.makeMove("X", 0, 0).getFirst();
		assertEquals(true, validMove);
		assertEquals(game.getGameState()[0][0], TicTacToeGame.SquareState.X);
		
		validMove = game.makeMove("O", 0, 1).getFirst();
		assertEquals(true, validMove);
		assertEquals(game.getGameState()[0][1], TicTacToeGame.SquareState.O);
		
		/*
		 * Also lower-case marks
		 */
		validMove = game.makeMove("x", 1, 0).getFirst();
		assertEquals(true, validMove);
		assertEquals(game.getGameState()[1][0], TicTacToeGame.SquareState.X);
		
		validMove = game.makeMove("o", 1, 1).getFirst();
		assertEquals(true, validMove);
		assertEquals(game.getGameState()[1][1], TicTacToeGame.SquareState.O);
	}
	
	@Test
	public void testInvalidMark(){
		TicTacToeGame game = new TicTacToeGame();
		boolean validMove = game.makeMove("B", 0, 0).getFirst();
		assertEquals(false, validMove);
	}
	
	@Test
	public void testMoveAfterGameWon(){
		TicTacToeGame game = new TicTacToeGame();
		game.makeMove("X", 0, 0);
		game.makeMove("O", 0, 1);
		game.makeMove("X", 1, 1);
		game.makeMove("O", 0, 2);
		game.makeMove("X", 2, 2);
		/*
		 * Player X should now be the winner, so try to make one more move
		 */
		boolean validMove = game.makeMove("O", 1, 0).getFirst();
		assertEquals(false, validMove);
	}
	
	@Test
	public void testInvalidRowCol(){
		TicTacToeGame game = new TicTacToeGame();
		boolean validMove = game.makeMove("X", -1, 0).getFirst();
		assertEquals(false, validMove);
		validMove = game.makeMove("X", 0, 3).getFirst();
		assertEquals(false, validMove);
	}
	
	@Test
	public void testMovingToOccupiedSpace(){
		TicTacToeGame game = new TicTacToeGame();
		game.makeMove("X", 0, 0);
		boolean validMove = game.makeMove("O", 0, 0).getFirst();
		assertEquals(false, validMove);
	}
	
	@Test
	public void testMovingOutOfTurn1(){
		TicTacToeGame game = new TicTacToeGame();
		game.makeMove("X", 0, 0);
		boolean validMove = game.makeMove("X", 1, 0).getFirst();
		assertEquals(false, validMove);
	}
	
	@Test
	public void testMovingOutOfTurn2(){
		TicTacToeGame game = new TicTacToeGame();
		game.makeMove("X", 0, 0);
		game.makeMove("O", 0, 1);
		boolean validMove = game.makeMove("O", 1, 0).getFirst();
		assertEquals(false, validMove);
	}
	
	@Test
	public void testNoWinner(){
		TicTacToeGame game = new TicTacToeGame();
		game.makeMove("X", 0, 0);
		game.makeMove("O", 1, 0);
		game.makeMove("X", 2, 1);
		game.makeMove("O", 1, 1);
		game.makeMove("X", 0, 2);
		SquareState winner = game.getWinner();
		assertEquals(SquareState._, winner);
	}
	
	@Test
	public void testWinnerRow(){
		TicTacToeGame game = new TicTacToeGame();
		game.makeMove("X", 0, 0);
		game.makeMove("O", 1, 0);
		game.makeMove("X", 0, 1);
		game.makeMove("O", 1, 1);
		game.makeMove("X", 0, 2);
		SquareState winner = game.getWinner();
		assertEquals(SquareState.X, winner);
	}
	
	@Test
	public void testWinnerCol(){
		TicTacToeGame game = new TicTacToeGame();
		game.makeMove("X", 0, 0);
		game.makeMove("O", 1, 1);
		game.makeMove("X", 1, 0);
		game.makeMove("O", 1, 2);
		game.makeMove("X", 2, 0);
		SquareState winner = game.getWinner();
		assertEquals(SquareState.X, winner);
	}
	
	@Test
	public void testWinnerDiagonal(){
		TicTacToeGame game = new TicTacToeGame();
		game.makeMove("X", 0, 0);
		game.makeMove("O", 1, 0);
		game.makeMove("X", 1, 1);
		game.makeMove("O", 1, 2);
		game.makeMove("X", 2, 2);
		SquareState winner = game.getWinner();
		assertEquals(SquareState.X, winner);
	}

}
