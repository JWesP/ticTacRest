RESTful Tic-Tac-Toe API

This is my first Maven project, so I believe it should build like any other 
Maven project, but if there's anything more you need in order to build that 
I haven't committed to the repository, just let me know.

I'm also including the latest build of my executable JAR file, named 
ticTacRest-0.0.1-SNAPSHOT.jar, which can be executed as follows...

	java -jar ticTacRest-0.0.1-SNAPSHOT.jar server

The API is as follows (assuming that the server is running on your local 
environment and you're using cURL)...

	To create a new game:
		
		curl -v -X POST "http://localhost:8080/tictacrest"
		
		Response: {game-id:0}
		
	To get the current state of a game board:
	
		curl -v -X GET "http://localhost:8080/tictacrest?game-id=0"
			
			This example would get the state for a game with id 0.
		
		Response: {game-state:[[_,_,_],[_,_,_],[_,_,_]]}
		
			The game state is an array of arrays.  Each of the three inner 
			arrays represents a row of the game board.  And the spaces within 
			each row are represented as either X, O, or _ (empty).
		
	To make a move:
	
		curl -v -X PUT "http://localhost:8080/tictacrest?game-id=0&mark=X&row=1&col=1"
		
			The example would attempt to mark an X in the middle square of 
			game id 0.  Rows are numbered 0, 1, 2 from top to bottom and 
			columns are numbered 0, 1, 2 from left to right.  So the upper 
			left square is row=0 col=0 and the bottom right square is row=2 
			col=2.
	
		Response: {valid-move:true,winner:_}
		
			The response indicates whether the move is valid or not.  If the 
			move was valid, it was applied to the game board.  The response 
			also indicates whether the game has been won or not by indicating 
			either X, O, or _ (no winner).
			
	To delete a game:
	
		curl -v -X DELETE "http://localhost:8080/tictacrest?game-id=0"
		
			This example deletes game id 0.
		
		Response: {destroyed-game-id:0}
		
			The response confirms that game id 0 was deleted.
			
	Errors:
	
		Any invalid call to the server will return an HTTP error code and 
		should include some explanatory information.  For example, attempting 
		to move to an invalid square results in the following...
		
		curl -v -X PUT "http://localhost:8080/tictacrest?game-id=1&mark=X&row=100&col=0"
		
		< HTTP/1.1 400 BAD REQUEST - Row and column must be values 0-2
		< Date: Sun, 09 Aug 2015 17:34:49 GMT
		< Content-Length: 0
		<
