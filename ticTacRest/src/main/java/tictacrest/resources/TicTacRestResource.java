package tictacrest.resources;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;

import tictacrest.GameRepository;
import tictacrest.TicTacToeGame;
import tictacrest.core.Message;

@Path("/tictacrest")
public class TicTacRestResource {
	private GameRepository games = new GameRepository();
	private final AtomicLong counter = new AtomicLong();
	
	public TicTacRestResource(){
		
	}
	
	@GET
    @Timed
	public Response getGameStatus(@QueryParam("game-id") Integer gameId){
		TicTacToeGame game = this.getGame(gameId);
		if (game != null){
			return Response.ok(game.toJson(), MediaType.APPLICATION_JSON).build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Timed
	public Response createGame(){
		Integer newGameId = this.getGames().getMaxGameId() + 1;
		TicTacToeGame newGame = new TicTacToeGame();
		this.getGames().addGame(newGameId, newGame);
		return Response.ok("{game-id:" + newGameId + "}", MediaType.APPLICATION_JSON).build();
	}
	
	
	public GameRepository getGames() {
		return games;
	}
	public TicTacToeGame getGame(Integer gameId) {
		return games.getGame(gameId);
	}
	public void setGames(GameRepository games) {
		this.games = games;
	}
	public AtomicLong getCounter() {
		return counter;
	}
	public long incrementAndGetCounter() {
		return counter.incrementAndGet();
	}
	
}
