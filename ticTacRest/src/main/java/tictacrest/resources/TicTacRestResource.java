package tictacrest.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

import tictacrest.GameRepository;
import tictacrest.TicTacToeGame;

@Path("/tictacrest")
public class TicTacRestResource {
	private static Logger log = LoggerFactory.getLogger(TicTacRestResource.class);
	
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
	
	@DELETE
	@Timed
	public Response destroyGame(@QueryParam("game-id") Integer gameId){
		TicTacToeGame game = this.getGame(gameId);
		if (game != null){
			this.deleteGame(gameId);
			return Response.ok("{destroyed-game-id:" + gameId + "}", MediaType.APPLICATION_JSON).build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Timed
	public Response makeMove(@QueryParam("game-id") Integer gameId, @QueryParam("mark") String mark, 
			@QueryParam("row") Integer row, @QueryParam("col") Integer column){
		log.info("Proposed move for game id " + gameId + ": Mark " + mark + " at (" + row + "," + column + ")");
		TicTacToeGame game = this.getGame(gameId);
		if (game != null){
			boolean validMove = game.makeMove(mark, row, column);
			if (validMove){
				this.getGames().persistGame(gameId, game);
				StringBuilder json = new StringBuilder();
				json
					.append("{")
						.append("valid-move:").append(validMove).append(",")
						.append("winner:").append(game.getWinner())
					.append("}");
				return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
			}
			else {
				return Response.status(Response.Status.CONFLICT).build();
			}
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	
	public GameRepository getGames() {
		return games;
	}
	public TicTacToeGame getGame(Integer gameId) {
		return games.getGame(gameId);
	}
	public void deleteGame(Integer gameId) {
		games.deleteGame(gameId);
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
