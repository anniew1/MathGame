import java.util.Scanner;

public class MathGame {

    private Player player1;
    private Player player2;
    private Player player3;
    private Player currentPlayer;
    private Player winner;
    private boolean gameOver;
    private Scanner scanner;
    private int playerStreak;
    private Player pastWinner;
    private int incorrectAnswers;

    // create MathGame object
    public MathGame(Player player1, Player player2, Player player3, Scanner scanner) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.scanner = scanner;
        currentPlayer = null; // will get assigned at start of game
        winner = null; // will get assigned when a Player wins
        pastWinner = null; // will get assigned once there are two wins
        gameOver = false;
        playerStreak = 1;
        incorrectAnswers = 0;
    }

    // ------------ PUBLIC METHODS (to be used by client classes) ------------

    // returns winning Player; will be null if neither Player has won yet
    public Player getWinner() {
        return winner;
    }

    // returns winning Player's streak; will be 1 if player doesn't have one
    public int getPlayerStreak() {
        return playerStreak;
    }

    // plays a round of the math game
    public void playRound() {
        chooseStartingPlayer();  // this helper method (shown below) sets currentPlayer to either player1, player2, or player3
        while (!gameOver) {
            printGameState();   // this helper method (shown below) prints the state of the Game
            System.out.println("Current player: " + currentPlayer.getName());
            boolean correct = askQuestion();  // this helper method (shown below) asks a question and returns T or F
            if (correct) {
                System.out.println("Correct!");
                currentPlayer.incrementScore();  // this increments the currentPlayer's score
                swapPlayers();  // this helper method (shown below) sets currentPlayer to the other Player
                incorrectAnswers = 0;
            } else {
                System.out.println("INCORRECT!");
                incorrectAnswers++;
                swapPlayers();
                if (incorrectAnswers == 2) {  // ensures game ends once two players get it wrong in a row
                    gameOver = true;
                    pastWinner = winner;
                    determineWinner();
                    if (winner == pastWinner) {
                        playerStreak++;
                    } else {
                        playerStreak = 1;
                    }
                }
            }
        }
    }

    // prints the current scores of the two players
    private void printGameState() {
        System.out.println("--------------------------------------");
        System.out.println("Current Scores:");
        System.out.println(player1.getName() + ": " + player1.getScore());
        System.out.println(player2.getName() + ": " + player2.getScore());
        System.out.println(player3.getName() + ": " + player3.getScore());
        System.out.println("--------------------------------------");
    }

    // resets the game back to its starting state
    public void resetGame() {
        player1.reset(); // this method resets the player
        player2.reset();
        player3.reset();
        gameOver = false;
        currentPlayer = null;
        incorrectAnswers = 0;
    }

    // ------------ PRIVATE HELPER METHODS (internal use only) ------------

    // randomly chooses one of the Player objects to be the currentPlayer
    private void chooseStartingPlayer() {
        int randNum = (int) (Math.random() * 3) + 1;
        if (randNum == 1) {
            currentPlayer = player1;
        } else if (randNum == 2) {
            currentPlayer = player2;
        } else {
            currentPlayer = player3;
        }
    }

    // asks a math question and returns true if the player answered correctly, false if not
    private boolean askQuestion() {
        int operation = (int) (Math.random() * 4) + 1;
        int num1 = (int) (Math.random() * 100) + 1;
        int num2;
        int correctAnswer;
        System.out.println("Type in your answer as an integer (/ is int division)");
        if (operation == 1) {
            num2 = (int) (Math.random() * 100) + 1;
            System.out.print(num1 + " + " + num2 + " = ");
            correctAnswer = num1 + num2;
        } else if (operation == 2) {
            num2 = (int) (Math.random() * 100) + 1;
            System.out.print(num1 + " - " + num2 + " = ");
            correctAnswer = num1 - num2;
        } else if (operation == 3) {
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + " * " + num2 + " = ");
            correctAnswer = num1 * num2;
        } else {  // option == 4
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + " / " + num2 + " = ");
            correctAnswer = num1 / num2;
        }

        int playerAnswer = scanner.nextInt(); // get player's answer using Scanner
        scanner.nextLine(); // clear text buffer after numeric scanner input

        if (playerAnswer == correctAnswer) {
            return true;
        } else {
            return false;
        }
    }

    // swaps the currentPlayer to the next player in line
    private void swapPlayers() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else if (currentPlayer == player2) {
            currentPlayer = player3;
        } else {
            currentPlayer = player1;
        }
    }

    // sets the winner when the game ends based on who has the msot amount of points
    private void determineWinner() {
        int mostPoints = Math.max((Math.max(player1.getScore(), player2.getScore())), player3.getScore());
        if (mostPoints == player1.getScore()) {
            winner = player1;
        } else if (mostPoints == player2.getScore()) {
            winner = player2;
        } else {
            winner = player3;
        }
    }
}
