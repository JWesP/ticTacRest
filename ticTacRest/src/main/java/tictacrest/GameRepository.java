package tictacrest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameRepository {
	private static Logger log = LoggerFactory.getLogger(GameRepository.class);
	
	private final static String SAVED_GAMES_DIR_NAME = "saved";
	private final static String SAVED_GAME_FILE_PREFIX = "tictactoegame-";
	private final static String XML_EXT = ".xml";
	
	private HashMap<Integer, TicTacToeGame> games = new HashMap<Integer, TicTacToeGame>();
	private Integer maxGameId = -1;
	
	public GameRepository(){
		/*
		 * If the saved game directory doesn't exist, create it
		 */
		File savedGamesDir = new File(SAVED_GAMES_DIR_NAME + File.separator);
		if (!savedGamesDir.exists()){
			log.info("Saved game directory doesn't exit.  Creating...");
			savedGamesDir.mkdir();
		}
		/*
		 * Otherwise, iterate through the saved game files and load them into 
		 * the HashMap for faster access.
		 */
		else {
			log.info("Found saved game directory.  Loading games...");
			List<String> savedGameFileNames = Arrays.asList(savedGamesDir.list());
			for (String savedGameFileName : savedGameFileNames){
				log.info("Loading file " + savedGameFileName + "...");
				Serializer serializer = new Persister();
				File source = new File(SAVED_GAMES_DIR_NAME + File.separator + savedGameFileName);
				TicTacToeGame thisGame = null;
				try {
					thisGame = serializer.read(TicTacToeGame.class, source);
				} catch (Exception e) {
					log.error("Catching Exception in constructor attempting to deserialize " + savedGameFileName, e);
				}
				String gameIdStr = savedGameFileName.substring(SAVED_GAME_FILE_PREFIX.length(), 
						savedGameFileName.length() - XML_EXT.length());
				if (gameIdStr != null && thisGame != null){
					Integer gameId = Integer.parseInt(gameIdStr);
					this.getGames().put(gameId, thisGame);
					if (gameId > this.getMaxGameId()){
						this.setMaxGameId(gameId);
					}
				}
			}
		}
	}
	
	public void addGame(Integer gameId, TicTacToeGame game){
		this.getGames().put(gameId, game);
		if (gameId > this.getMaxGameId()){
			this.setMaxGameId(gameId);
		}
		Serializer serializer = new Persister();
		File result = new File(SAVED_GAMES_DIR_NAME + File.separator + SAVED_GAME_FILE_PREFIX + gameId + XML_EXT);
		try {
			serializer.write(game, result);
		} catch (Exception e) {
			log.error("Catching Exception in method addGame attempting to serialize game id " + gameId, e);
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
