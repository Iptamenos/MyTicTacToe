package ai;

import java.util.ArrayList;

public class Board {

    //Immediate move that lead to this board
    private Move lastMove;

    /* Variable containing who played last; whose turn resulted in this board
     * Even a new Board has lastLetterPlayed value; it denotes which player will play first
     */
	private int lastSymbolPlayed;

	private int [][] gameBoard;
	private int winner;

	public Board() {
		lastMove = new Move();
		lastSymbolPlayed = Constants.O;
		gameBoard = new int[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				gameBoard[i][j] = Constants.EMPTY;
			}
		}
		winner = Constants.EMPTY;
	}
	
	public Board(Board board) {
		lastMove = board.lastMove;
		lastSymbolPlayed = board.lastSymbolPlayed;
		gameBoard = new int[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
		winner = Constants.EMPTY;
	}
		
	public Move getLastMove() {
		return lastMove;
	}
	
	public int getLastSymbolPlayed() {
		return lastSymbolPlayed;
	}
	
	public int[][] getGameBoard() {
		return gameBoard;
	}

	public int getWinner() {
		return winner;
	}

	public void setLastMove(Move lastMove) {
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setLastSymbolPlayed(int lastSymbolPlayed) {
		this.lastSymbolPlayed = lastSymbolPlayed;
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

    // Make a move; it places a symbol in the board
	public void makeMove(int row, int col, int symbol) {
		gameBoard[row][col] = symbol;
		lastMove = new Move(row, col);
		lastSymbolPlayed = symbol;
	}

    // Checks whether a move is valid; whether a square is empty
	public boolean isValidMove(int row, int col) {
		if ((row == -1) || (col == -1) || (row > 2) || (col > 2)) {
			return false;
		}
		if (gameBoard[row][col] != Constants.EMPTY) {
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
			for(int col=0; col<3; col++) {
				if(isValidMove(row, col)) {
					Board child = new Board(this);
					child.makeMove(row, col, symbol);
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
		for(int col=0; col<3; col++) {
            sum = gameBoard[0][col] + gameBoard[1][col] + gameBoard[2][col];
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
		for (int col=0; col<3; col++) {
    		if((gameBoard[0][col] == gameBoard[1][col]) && (gameBoard[1][col] == gameBoard[2][col]) && (gameBoard[0][col] != Constants.EMPTY)) {
    			setWinner(gameBoard[0][col]);
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
		if (this.lastSymbolPlayed == Constants.X)
			this.lastSymbolPlayed = Constants.O;
		else if (this.lastSymbolPlayed == Constants.O)
			this.lastSymbolPlayed = Constants.X;
	}
	
	// Makes the specified cell in the border empty.
	public void undoMove(int row, int col, int symbol) {
		this.gameBoard[row][col] = Constants.EMPTY;
		this.lastSymbolPlayed = symbol;
	}
	
    //Prints the board
    /*
	public void print() {
		System.out.println("*********");
		int counter = 1;
		for(int row=0; row<3; row++) {
			System.out.print("* ");
			for(int col=0; col<3; col++) {
				switch (gameBoard[row][col]) {
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
			for (int col=0; col<3; col++) {
				if (gameBoard[row][col] != Constants.EMPTY) {
                    return false;
                }
            }
        }
        // System.out.println("Game board is empty!");
        return true;
    }
    
	// Checking if the board is full.
	// Checking if there is at least one empty tile.
    public static boolean isGameBoardFull(int[][] gameBoard) {
        for (int row=0; row<3; row++) {
			for (int col=0; col<3; col++) {
				if (gameBoard[row][col] == Constants.EMPTY) {
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
			for (int col=0; col<3; col++) {
				if (gameBoard[row][col] == Constants.EMPTY) {
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
			for(int col=0; col<3; col++) {
				switch (gameBoard[row][col]) {
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
