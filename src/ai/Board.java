package ai;

import java.util.ArrayList;

import utility.Constants;

public class Board {

	
    // Immediate move that led to this board.
    private Move lastMove;

    /* Variable containing who played last; whose turn resulted in this board.
     * Even a new Board has lastLetterPlayed value; it denotes which player will play first.
     */
	private int lastPlayer;

	private int [][] gameBoard;
	private int winner;

	public Board() {
		this.lastMove = new Move();
		this.lastPlayer = Constants.O;
		this.gameBoard = new int[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				this.gameBoard[i][j] = Constants.EMPTY;
			}
		}
		this.winner = Constants.EMPTY;
	}
	
	public Board(Board board) {
		this.lastMove = new Move(board.lastMove);
		this.lastPlayer = board.lastPlayer;
		this.gameBoard = new int[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				this.gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
		this.winner = Constants.EMPTY;
	}
		
	public Move getLastMove() {
		return lastMove;
	}
	
	public int getLastPlayer() {
		return lastPlayer;
	}
	
	public int[][] getGameBoard() {
		return gameBoard;
	}

	public int getWinner() {
		return winner;
	}

	public void setLastMove(Move lastMove) {
		this.lastMove = new Move(lastMove);
	}
	
	public void setLastPlayer(int lastPlayer) {
		this.lastPlayer = lastPlayer;
	}
	
	public void setGameBoard(int[][] gameBoard) {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				this.gameBoard[i][j] = gameBoard[i][j];
			}
		}
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

    // Make a move; it places a symbol on the board
	public void makeMove(int row, int column, int player) {
		this.gameBoard[row][column] = player;
		this.lastMove = new Move(row, column);
		this.lastPlayer = player;
	}

    // Checks whether a move is valid; whether a square is empty
	public boolean isValidMove(int row, int column) {
		if ((row == -1) || (column == -1) || (row > 2) || (column > 2)) {
			return false;
		}
		if (this.gameBoard[row][column] != Constants.EMPTY) {
			return false;
		}
		return true;
	}

    /* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
	public ArrayList<Board> getChildren(int symbol) {
		ArrayList<Board> children = new ArrayList<Board>();
		for(int row=0; row<3; row++) {
			for(int column=0; column<3; column++) {
				if(isValidMove(row, column)) {
					Board child = new Board(this);
					child.makeMove(row, column, symbol);
					children.add(child);
				}
			}
		}
		return children;
	}

	/*
     * The heuristic we use to evaluate is
     * the number our almost complete TicTacToes (having 2 symbols in a row, column or diagonal)
     * minus the number of the opponent's almost complete TicTacToes.
     * Special case: if a complete TicTacToe is present it counts as ten.
     */
	public int evaluate() {
		int Xlines = 0;
		int Olines = 0;
        int sum;
        
        // Checking rows
		for (int row=0; row<3; row++) {
            sum = gameBoard[row][0] + gameBoard[row][1] + gameBoard[row][2];
            if (sum == 3) {
                Xlines = Xlines + 10;
			}
            else if (sum == 2) {
                Xlines++;
			}
            else if (sum == -3) {
                Olines = Olines + 10;
			}
            else if (sum == -2) {
                Olines++;
			}
		}

        // Checking columns
		for(int column=0; column<3; column++) {
            sum = gameBoard[0][column] + gameBoard[1][column] + gameBoard[2][column];
            if(sum == 3) {
                Xlines = Xlines + 10;
			}
            else if(sum == 2) {
                Xlines++;
			}
            else if(sum == -3) {
                Olines = Olines + 10;
			}
            else if(sum == -2) {
                Olines++;
			}
		}

        // Checking  diagonals
        sum = gameBoard[0][0] + gameBoard[1][1] + gameBoard[2][2];
        if(sum == 3) {
            Xlines = Xlines + 10;
        }
        else if(sum == 2) {
            Xlines++;
        }
        else if(sum == -3) {
            Olines = Olines + 10;
        }
        else if(sum == -2) {
            Olines++;
        }
        sum = gameBoard[0][2] + gameBoard[1][1] + gameBoard[2][0];
        if(sum == 3) {
            Xlines = Xlines + 10;
        }
        else if(sum == 2) {
            Xlines++;
        }
        else if(sum == -3) {
            Olines = Olines + 10;
        }
        else if(sum == -2) {
            Olines++;
        }

		return Xlines - Olines;
	}

    /*
     * A state is terminal if there is a TicTacToe
     * or no empty tiles are available
     */
    public boolean isTerminal() {
        // Checking if there is a horizontal TicTacToe
		for(int row=0; row<3; row++) {
    		if ((gameBoard[row][0] == gameBoard[row][1]) && (gameBoard[row][1] == gameBoard[row][2]) && (gameBoard[row][0] != Constants.EMPTY)) {
    			setWinner(gameBoard[row][0]);
                return true;
			}
		}

        // Checking if there is a vertical TicTacToe
		for (int column=0; column<3; column++) {
    		if((gameBoard[0][column] == gameBoard[1][column]) && (gameBoard[1][column] == gameBoard[2][column]) && (gameBoard[0][column] != Constants.EMPTY)) {
    			setWinner(gameBoard[0][column]);
                return true;
			}
		}

        // Checking if there is a diagonal TicTacToe
        if ((gameBoard[0][0] == gameBoard[1][1]) && (gameBoard[1][1] == gameBoard[2][2]) && (gameBoard[1][1] != Constants.EMPTY)) {
			setWinner(gameBoard[0][0]);
            return true;
        }
        if ((gameBoard[0][2] == gameBoard[1][1]) && (gameBoard[1][1] == gameBoard[2][0]) && (gameBoard[1][1] != Constants.EMPTY)) {
			setWinner(gameBoard[0][2]);
            return true;
        }

        // Checking if there is at least one empty tile
        return(Board.isGameBoardFull(gameBoard));
    }

	public void changeLastSymbolPlayed() {
		if (this.lastPlayer == Constants.X)
			this.lastPlayer = Constants.O;
		else if (this.lastPlayer == Constants.O)
			this.lastPlayer = Constants.X;
	}
	
    //Prints the board
    /*
	public void print() {
		System.out.println("*********");
		int counter = 1;
		for(int row=0; row<3; row++) {
			System.out.print("* ");
			for(int column=0; column<3; column++) {
				switch (gameBoard[row][column]) {
					case Constants.X:
						System.out.print("X ");
						break;
					case Constants.O:
						System.out.print("O ");
						break;
					case Constants.EMPTY:
						System.out.print(counter + " ");
						break;
					default:
						break;
				}
				counter++;
			}
			System.out.println("*");
		}
		System.out.println("*********");
	}
	*/
	
	// Checking if the board is empty.
    public static boolean isGameBoardEmpty(int[][] gameBoard) {
        for (int row=0; row<3; row++) {
			for (int column=0; column<3; column++) {
				if (gameBoard[row][column] != Constants.EMPTY) {
                    return false;
                }
            }
        }
        // System.out.println("Game board is empty!");
        return true;
    }
    
	// Checking if the board is full,
	// by checking if there is at least one empty tile.
    public static boolean isGameBoardFull(int[][] gameBoard) {
        for (int row=0; row<3; row++) {
			for (int column=0; column<3; column++) {
				if (gameBoard[row][column] == Constants.EMPTY) {
                    return false;
                }
            }
        }
    	// System.out.println("Game board is full!");
        return true;
    }
    
    
	public static int getNumberOfEmptyCells(int[][] gameBoard) {
		int number_of_empty_cells = 0;
		for (int row=0; row<3; row++) {
			for (int column=0; column<3; column++) {
				if (gameBoard[row][column] == Constants.EMPTY) {
					number_of_empty_cells++;
                }
            }
        }
        return number_of_empty_cells;
	}
	
	
    // Prints the board, using "X", "O" and 1-9 for ids
	public static void printBoard(int[][] gameBoard) {
		System.out.println("*********");
		int counter = 1;
		for(int row=0; row<3; row++) {
			System.out.print("* ");
			for(int column=0; column<3; column++) {
				switch (gameBoard[row][column]) {
					case Constants.X:
						System.out.print("X ");
						break;
					case Constants.O:
						System.out.print("O ");
						break;
					case Constants.EMPTY:
						System.out.print(counter + " ");
						break;
					default:
						break;
				}
				counter++;
			}
			System.out.println("*");
		}
		System.out.println("*********");
	}
	
}
