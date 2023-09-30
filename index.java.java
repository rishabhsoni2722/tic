import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        char[][] board = {
                { ' ', ' ', ' ' },
                { ' ', ' ', ' ' },
                { ' ', ' ', ' ' }
        };

        int currentPlayer = 1;
        boolean gameWon = false;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard(board);

            if (currentPlayer == 1) {
                humanMove(board, scanner);
            } else {
                // AI's turn (player 2)
                int[] bestMove = minimax(board, 2);
                board[bestMove[0]][bestMove[1]] = 'O';
            }

            if (checkWin(board, 'X')) {
                printBoard(board);
                System.out.println("Congratulations! You win!");
                break;
            } else if (checkWin(board, 'O')) {
                printBoard(board);
                System.out.println("AI wins!");
                break;
            } else if (isBoardFull(board)) {
                printBoard(board);
                System.out.println("It's a draw!");
                break;
            }

            currentPlayer = 3 - currentPlayer; // Switch players
        }

        scanner.close();
    }

    public static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    public static void humanMove(char[][] board, Scanner scanner) {
        while (true) {
            System.out.print("Enter your move (row and column, e.g., 1 2): ");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = 'X';
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    public static boolean checkWin(char[][] board, char player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] minimax(char[][] board, int player) {
        int[] bestMove = new int[] { -1, -1 };
        int bestScore = (player == 2) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = (player == 2) ? 'O' : 'X';
                    int score = minimaxHelper(board, 3 - player, (player == 2));
                    board[i][j] = ' ';

                    if ((player == 2 && score > bestScore) || (player == 1 && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    public static int minimaxHelper(char[][] board, int currentPlayer, boolean isMaximizingPlayer) {
        if (checkWin(board, 'X')) {
            return -1;
        }
        if (checkWin(board, 'O')) {
            return 1;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        int bestScore = (isMaximizingPlayer) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = (currentPlayer == 2) ? 'O' : 'X';
                    int score = minimaxHelper(board, 3 - currentPlayer, !isMaximizingPlayer);
                    board[i][j] = ' ';

                    if (isMaximizingPlayer) {
                        bestScore = Math.max(score, bestScore);
                    } else {
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }

        return bestScore;
    }
}
