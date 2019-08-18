package ai;

import java.util.ArrayList;

public class Board {
	
    // Immediate move that led to this board.
    private Move lastMove;

    // Variable containing who plays now.
	private int turn;
	private int [][] gameBoard;
	private int winner;
	
	public Board() {
		this.lastMove = new Move();
		this.turn = Constants.X;
		this.gameBoard = new int[3][3];
		this.winner = Constants.EMPTY;
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				gameBoard[i][j] = Constants.EMPTY;
			}
		}
	}
	
	public Board(Board board) {
		this.lastMove = board.getLastMove();
		this.turn = board.getTurn();
		this.gameBoard = new int[3][3];
		this.winner = board.getWinner();
		
		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++) {
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
	}
		
	public Move getLastMove() {
		return lastMove;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public int[][] getGameBoard() {
		return gameBoard;
	}

	public int getWinner() {
		return winner;
	}

	public void setLastMove(Move lastMove) {
		this.lastMove = lastMove;
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public void setGameBoard(int[][] gameBoard) {
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				this.gameBoard[i][j] = gameBoard[i][j];
			}
		}
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}
	
    // Make a move; it places a letter in the board
	public void makeMove(int row, int col, int turnLetter) {
		this.gameBoard[row][col] = turnLetter;
		this.lastMove = new Move(row, col);
		changeTurn();
	}

	public void changeTurn() {
		if (this.turn == Constants.X)
			this.turn = Constants.O;
		else if (this.turn == Constants.O)
			this.turn = Constants.X;
	}
	
    // Checks whether a move is valid; whether a square is empty
	public boolean isValidMove(int row, int col) {
		if ((row == -1) || (col == -1) || (row > 2) || (col > 2)) {
			return false;
		}
		if(gameBoard[row][col] != Constants.EMPTY) {
			return false;
		}
		return true;
	}

    /* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
	public ArrayList<Board> getChildren(int letter) {
		ArrayList<Board> children = new ArrayList<Board>();
		for(int row=0; row<3; row++) {
			for(int col=0; col<3; col++) {
				if(isValidMove(row, col)) {
					Board child = new Board(this);
					child.makeMove(row, col, letter);
					children.add(child);
				}
			}
		}
		return children;
	}

	/*
     * The heuristic we use to evaluate is
     * the number our almost complete tic-tac-toes (having 2 letter in a row, column or diagonal)
     * minus the number of the opponent's almost complete tic-tac-toes
     * Special case: if a complete tic-tac-toe is present it counts as ten
     */
	public int evaluate() {
		int Xlines = 0;
		int Olines = 0;
        int sum;

        // Checking rows
		for(int row=0; row<3; row++) {
            sum = gameBoard[row][0] + gameBoard[row][1] + gameBoard[row][2];
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
     * A state is terminal if there is a tic-tac-toe
     * or no empty tiles are available.
     */
    public boolean isTerminal() {
        // Checking if there is a horizontal tic-tac-toe
		for (int row=0; row<3; row++) {
    		if ((gameBoard[row][0] == gameBoard[row][1]) 
    				&& (gameBoard[row][1] == gameBoard[row][2]) 
    				&& (gameBoard[row][0] != Constants.EMPTY)) {
    			setWinner(gameBoard[row][0]);
                return true;
			}
		}

        // Checking if there is a vertical tic-tac-toe
		for (int col=0; col<3; col++) {
    		if ((gameBoard[0][col] == gameBoard[1][col]) 
    				&& (gameBoard[1][col] == gameBoard[2][col]) 
    				&& (gameBoard[0][col] != Constants.EMPTY)) {
    			setWinner(gameBoard[0][col]);
                return true;
			}
		}

        // Checking if there is a diagonal tic-tac-toe
        if ((gameBoard[0][0] == gameBoard[1][1]) 
        		&& (gameBoard[1][1] == gameBoard[2][2]) 
        		&& (gameBoard[1][1] != Constants.EMPTY)) {
			setWinner(gameBoard[0][0]);
            return true;
        }
        if ((gameBoard[0][2] == gameBoard[1][1]) 
        		&& (gameBoard[1][1] == gameBoard[2][0]) 
        		&& (gameBoard[1][1] != Constants.EMPTY)) {
			setWinner(gameBoard[0][2]);
            return true;
        }

		setWinner(Constants.EMPTY);
        return Board.isGameBoardFull(this.gameBoard);
        
    }
    

	// Makes the specified cell in the border empty.
	public void undoMove(int row, int col, int symbol) {
		this.gameBoard[row][col] = Constants.EMPTY;
		// change turn
		if (symbol == Constants.O) {
			this.turn = Constants.X;
		} else if (symbol == Constants.X) {
			this.turn = Constants.O;
		}
	}
	
	
	// Checking if there is at least one empty cell.
    public static boolean isGameBoardFull(int[][] gameBoard) {
        for (int row=0; row<3; row++) {
			for (int col=0; col<3; col++) {
				if (gameBoard[row][col] == Constants.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
	// Checking if the board is empty.
    public static boolean isGameBoardEmpty(int[][] gameBoard) {
        for (int row=0; row<3; row++) {
			for (int col=0; col<3; col++) {
				if (gameBoard[row][col] != Constants.EMPTY) {
                    return false;
                }
            }
        }
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

    // Prints the board, using 1, 2 and 0
    /*
	public static void printBoard(int[][] gameBoard) {
		for (int i=0; i<3; i++) {
			System.out.print("|");
			for (int j=0; j<3; j++) {
				System.out.print(this.gameBoard[i][j] + "|");
			}
			System.out.println();
		}
		System.out.println("**********");
	}
	*/
	
	
}
