import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.Random;

public class ViewModel implements ViewListener {

    private ViewState viewState;
    private View view;
    private Model model;
    private static Random rand = new Random();

    public static void main(String[] args) {
        ViewModel start = new ViewModel();
        start.startGame();
    }

    private void startGame() {
        view = new View(this);
        viewState = new ViewState();
        model = new Model();
        populateGridWithShips();
        model.isUserEnteringRow = true;
        model.isUserEnteringColumn = false;
        invalidateView();
    }

    private void invalidateView() {
        generateViewStateFromModel();
        view.setNewViewState(viewState);
    }

    private void generateViewStateFromModel() {
        if (didPlayerWin()) {
            viewState.askForInput = false;
            viewState.displayOutput = true;
            viewState.output = Model.USER_WON_GAME_1 + model.currentPlayer.getOtherPlayer() + Model.USER_WON_GAME_2;
            return;
        }
        if (model.startOfGame) {
            viewState.output = Model.PLAY_MODE_ENJOY + "\n";
            viewState.output += gridToString() + "\n";
            viewState.output += Model.ENTER_ROW + model.currentPlayer;
            viewState.displayOutput = true;
            viewState.askForInput = true;
            return;
        }

        if(model.didUserQuitGame){
            viewState.displayOutput= true;
            viewState.askForInput = false;
            viewState.output = Model.USER_QUIT_TEXT;
            return;
        }
        if (model.didCurrentUserMiss) {
            viewState.output = Model.YOU_MISSED + "\n";
            viewState.output += gridToString();
            viewState.output += Model.ENTER_ROW + model.currentPlayer;
            return;
        }
        if (model.didCurrentUserHitOwnShip) {
            viewState.output = Model.HIT_OWN_SHIP + model.currentPlayer.getOtherPlayer() + "\n";
            viewState.output += gridToString();
            viewState.output += Model.ENTER_ROW + model.currentPlayer;
            return;
        }
        if (model.didCurrentUserHitEnemyShip) {
            viewState.output = Model.YOU_DESTROYED_A_SHIP + "\n";
            viewState.output += gridToString();

            viewState.output += Model.ENTER_ROW + model.currentPlayer;
            return;
        }
        if (model.isShipAlreadyHit) {
            viewState.output = Model.SHIP_ALREADY_HIT + "\n";
            viewState.output += gridToString();
            viewState.output += Model.ENTER_ROW + model.currentPlayer;
            return;
        }
        if (model.isUserEnteringRow) {
            viewState.askForInput = true;
            viewState.displayOutput = true;
            viewState.output = Model.ENTER_ROW + model.currentPlayer;
            return;
        }
        if (model.isUserEnteringColumn) {
            viewState.askForInput = true;
            viewState.displayOutput = true;
            viewState.output = Model.ENTER_COLUMN + model.currentPlayer;
        }
    }

    @Override
    public void enteredInput(String input) {
        model.startOfGame = false;
        model.didCurrentUserMiss = false;
        model.didCurrentUserHitOwnShip = false;
        model.didCurrentUserHitEnemyShip = false;
        model.isShipAlreadyHit = false;
        model.didUserQuitGame = false;
        int enteredRow = model.isUserEnteringRow ? getValueOrNegativeOne(input) : 0;
        int enteredColumn = model.isUserEnteringColumn ? getValueOrNegativeOne(input) : 0;
        if (didUserQuit(input)) {
            model.didUserQuitGame = true;
            invalidateView();
            return;
        }

        if (model.isUserEnteringRow && !isXOrYWithinGridRange(enteredRow)) {
            invalidateView();
            return;
        }
        if (model.isUserEnteringColumn && !isXOrYWithinGridRange(enteredColumn)) {
            invalidateView();
            return;
        }
        if (model.isUserEnteringRow) {
            model.currentUserEnteredRow = enteredRow;
            model.isUserEnteringRow = false;
            model.isUserEnteringColumn = true;
            invalidateView();
            return;
        }
        if (model.isUserEnteringColumn) {
            model.currentUserEnteredColumn = enteredColumn;
            model.isUserEnteringColumn = false;
            model.isUserEnteringRow = true;
            enteredCoordinate();
        }
    }

    private boolean didUserQuit(String userInput) {
        return userInput.equalsIgnoreCase(Model.Q);
    }

    private void enteredCoordinate() {
        if (!isThisAShip(model.currentUserEnteredRow, model.currentUserEnteredColumn)) {
            model.seaGrid[model.currentUserEnteredRow][model.currentUserEnteredColumn] = Model.MISSED_HIT_POSITION;
            model.didCurrentUserMiss = true;
            switchPlayers();
            invalidateView();
            return;
        }
        if (isThisShipAlreadyHit(model.currentUserEnteredRow, model.currentUserEnteredColumn)) {
            model.isShipAlreadyHit = true;
            invalidateView();
            return;
        }
        updatePointPlayerPoints();
        if (didCurrentPlayerHitHisOwnShip()) {
            model.didCurrentUserHitOwnShip = true;
            getShipAtEnteredCoordinate().shipHit = true;
            switchPlayers();
            invalidateView();
            return;
        }
        model.didCurrentUserHitEnemyShip = true;
        getShipAtEnteredCoordinate().shipHit = true;
        switchPlayers();
        invalidateView();
    }

    private boolean didPlayerWin() {
        return Player.X.points == Model.NUMBER_PER_PLAYER || Player.O.points == Model.NUMBER_PER_PLAYER;
    }

    private void updatePointPlayerPoints() {
        if (didCurrentPlayerHitHisOwnShip()) {
            model.currentPlayer.getOtherPlayer().points++;
            return;
        }
        model.currentPlayer.points++;
    }

    private String gridToString() {
        final String padding = "  ";
        StringBuilder grid = new StringBuilder(padding);
        for (int i = 0; i < Model.SEA_SIZE; i++) {
            grid.append(i).append(padding);
        }
        grid.append("\n");
        for (int row = 0; row < Model.SEA_SIZE; row++) {
            grid.append(row);
            for (int column = 0; column < Model.SEA_SIZE; column++) {
                if (isThisAShip(row, column)) {
                    grid.append("[").append(isThisShipAlreadyHit(row, column) ?
                            Model.HIT_POSITION : Model.UNHIT_POSITION).append("]");
                    continue;
                }
                grid.append("[").append(model.seaGrid[row][column].equals(Model.UNHIT_POSITION) ?
                        Model.UNHIT_POSITION : Model.MISSED_HIT_POSITION).append("]");
            }
            grid.append("\n");
        }
        return grid.toString();
    }

    private void populateGridWithShips() {
        for (int numOfShips = 0; numOfShips < Model.NUMBER_SHIPS; numOfShips++) {
            Ship randomShip = new Ship(model.currentPlayer);
            do {
                randomizeShipXAndY();
            } while (!isCoordinateAvailable());
            populateCoordinate(randomShip);
            switchPlayers();
        }
    }

    private void randomizeShipXAndY() {
        model.currentUserEnteredRow = rand.nextInt(Model.SEA_SIZE);
        model.currentUserEnteredColumn = rand.nextInt(Model.SEA_SIZE);
    }

    private void populateCoordinate(Ship ship) {
        model.shipGrid[model.currentUserEnteredRow][model.currentUserEnteredColumn] = ship;
    }

    private void switchPlayers() {
        model.currentPlayer = model.currentPlayer.getOtherPlayer();
    }

    private boolean isCoordinateAvailable() {
        return getShipAtEnteredCoordinate() == null;
    }

    private boolean didCurrentPlayerHitHisOwnShip() {
        return getShipAtEnteredCoordinate().player == model.currentPlayer;
    }

    private boolean isThisShipAlreadyHit(int row, int column) {
        return model.shipGrid[row][column].shipHit;
    }

    private boolean isThisAShip(int row, int column) {
        return model.shipGrid[row][column] != null;
    }

    private boolean isXOrYWithinGridRange(int value) {
        return value < Model.SEA_SIZE && value >= 0;
    }

    private Ship getShipAtEnteredCoordinate() {
        return model.shipGrid[model.currentUserEnteredRow][model.currentUserEnteredColumn];
    }

    private int getValueOrNegativeOne(String input) {
        int result;
        try {
            result = Integer.valueOf(input);
        } catch (NumberFormatException exception) {
            result = -1;
        }
        return result;
    }
}