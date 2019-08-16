
public class Model {
    public static final String ENTER_ROW = "Enter a coordinate (0 . . 4) (Row) or Enter 'Q' to quit Player: ";
    public static final String ENTER_COLUMN = "Enter a coordinate (0 . . 4) (Column) or Enter 'Q' to quit Player: ";
    public static final String USER_WON_GAME_1 = " Congratulations! Player ";
    public static final String USER_WON_GAME_2 = " won";
    public static final String PLAY_MODE_ENJOY = "OK - We're going to start with user X....";
    public static final String UNHIT_POSITION = ".";
    public static final String HIT_POSITION = "*";
    public static final String MISSED_HIT_POSITION = "X";
    public static final String HIT_OWN_SHIP = "Oh no, you hit your own ship player ";
    public static final String YOU_DESTROYED_A_SHIP = "Congrats! YOU destroyed YOUR ENEMIES SHIP ";
    public static final String SHIP_ALREADY_HIT = "This ship is already destroyed please select another area";
    public static final String YOU_MISSED = "No ships were hit";

    public static final int NUMBER_PER_PLAYER = 6;
    public static final int NUMBER_SHIPS = NUMBER_PER_PLAYER * 2;
    public static final int SEA_SIZE = 5;

    public boolean isUserEnteringRow;
    public boolean isUserEnteringColumn;

    public boolean startOfGame;
    public boolean didCurrentUserMiss;
    public boolean didCurrentUserHitOwnShip;
    public boolean didCurrentUserHitEnemyShip;
    public boolean isShipAlreadyHit;

    public int currentUserEnteredRow;
    public int currentUserEnteredColumn;

    public Player currentPlayer = Player.X;

    public Ship[][] shipGrid;
    public String[][] seaGrid;

    public Model() {
        shipGrid = new Ship[SEA_SIZE][SEA_SIZE];

        seaGrid = new String[SEA_SIZE][SEA_SIZE];
        for (int row = 0; row < SEA_SIZE; row++) {
            for (int column = 0; column < SEA_SIZE; column++) {
                seaGrid[row][column] = UNHIT_POSITION;
            }
        }
        startOfGame = true;
    }
}

