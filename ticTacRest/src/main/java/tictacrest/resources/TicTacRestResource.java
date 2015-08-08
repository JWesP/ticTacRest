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
import tictacrest.Pair;
import tictacrest.TicTacToeGame;
import tictacrest.core.CustomErrorStatus;

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
		if (gameId == null){
			CustomErrorStatus errorStatus = new CustomErrorStatus(
					Response.Status.Family.CLIENT_ERROR, 
					400,
					"BAD REQUEST - Must provide valid game-id parameter");
			return Response.status(errorStatus).build();
		}
		TicTacToeGame game = this.getGame(gameId);
		if (game != null){
			return Response.ok(game.toJson(), MediaType.APPLICATION_JSON).build();
		}
		else {
			CustomErrorStatus errorStatus = new CustomErrorStatus(
					Response.Status.Family.CLIENT_ERROR, 
					400,
					"BAD REQUEST - No game id " + gameId + "found");
			return Response.status(errorStatus).build();
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
		if (gameId == null){
			CustomErrorStatus errorStatus = new CustomErrorStatus(
					Response.Status.Family.CLIENT_ERROR, 
					400,
					"BAD REQUEST - Must provide valid game-id parameter");
			return Response.status(errorStatus).build();
		}
		TicTacToeGame game = this.getGame(gameId);
		if (game != null){
			this.deleteGame(gameId);
			return Response.ok("{destroyed-game-id:" + gameId + "}", MediaType.APPLICATION_JSON).build();
		}
		else {
			CustomErrorStatus errorStatus = new CustomErrorStatus(
					Response.Status.Family.CLIENT_ERROR, 
					400,
					"BAD REQUEST - No game id " + gameId + "found");
			return Response.status(errorStatus).build();
		}
	}
	
	@PUT
	@Timed
	public Response makeMove(@QueryParam("game-id") Integer gameId, @QueryParam("mark") String mark, 
			@QueryParam("row") Integer row, @QueryParam("col") Integer column){
		if (gameId == null || mark == null || row == null || column == null){
			CustomErrorStatus errorStatus = new CustomErrorStatus(
					Response.Status.Family.CLIENT_ERROR, 
					400,
					"BAD REQUEST - Must provide valid game-id, mark, row, and col parameters");
			return Response.status(errorStatus).build();
		}
		log.info("Proposed move for game id " + gameId + ": Mark " + mark + " at (" + row + "," + column + ")");
		TicTacToeGame game = this.getGame(gameId);
		if (game != null){
			Pair<Boolean, String> validMoveReason = game.makeMove(mark, row, column);
			boolean validMove = validMoveReason.getFirst();
			String invalidReason = validMoveReason.getSecond();
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
				CustomErrorStatus errorStatus = new CustomErrorStatus(
						Response.Status.Family.CLIENT_ERROR, 
						400,
						"BAD REQUEST - " + invalidReason);
				return Response.status(errorStatus).build();
			}
		}
		else {
			CustomErrorStatus errorStatus = new CustomErrorStatus(
					Response.Status.Family.CLIENT_ERROR, 
					400,
					"BAD REQUEST - No game id " + gameId + "found");
			return Response.status(errorStatus).build();
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
