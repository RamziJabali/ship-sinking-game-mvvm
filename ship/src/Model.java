
public class Model {
    public static final String ENTER_ROW = "Enter a coordinate (0 . . 9) (Row) or Enter 'Q' to quit";
    public static final String ENTER_COLUMN = "Enter a coordinate (0 . . 9) (Column) or Enter 'Q' to quit";
    public static final String USER_LOST_GAME = " You lost!";
    public static final String USER_WON_GAME_1 = " Congratulations! Player ";
    public static final String USER_WON_GAME_2 = "won";
    public static final String PLAY_MODE_ENJOY = "OK - We're going to start with user X....";
    public static final String UNHIT_POSITION = ".";
    public static final String HIT_POSITION = "*";
    public static final String MISSED_HIT_POSITION = "X";
    public static final String YOU_HIT_A_SHIP = "Good job! A ship was hit";
    public static final String YOU_DESTROYED_A_SHIP = "Congrats! A ship was destroyed";
    public static final String YOU_MISSED = "No ships were hit";
    public static final String DIVIDER_LINE = "----------------------------------------------------------------------";

    public static final int NUMBER_SHIPS = 12;
    public static final int SEA_SIZE = 5;

    public boolean enterRow;
    public boolean enterColumn;

    public int row;
    public int column;

    public Player currentPlayer = Player.X;

    public Ship ship;

    public Ship[][] shipGrid;

    public Model() {
        shipGrid = new Ship[NUMBER_SHIPS][NUMBER_SHIPS];
        for (int row = 0; row < SEA_SIZE; row++) {
            for (int column = 0; column < SEA_SIZE; column++) {
                shipGrid[row][column] = null;
            }
        }
    }
}

