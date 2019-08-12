
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
        viewState.output = Model.PLAY_MODE_ENJOY + "\n";
        viewState.output += gridToString() + "\n";
        viewState.output += Model.ENTER_ROW;
        viewState.displayOutput = true;
        viewState.askForInput = true;
        model.enterRow = true;
        model.enterColumn = false;
        invalidateView();
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
                if (model.shipGrid[row][column] == null) {
                    grid.append("[").append(Model.UNHIT_POSITION).append("]");

                }
                if (model.shipGrid[row][column] != null && !model.shipGrid[row][column].shipHit) {
                    grid.append("[").append(Model.UNHIT_POSITION).append("]");
                }

                if (model.shipGrid[row][column] != null && model.shipGrid[row][column].shipHit) {
                    grid.append("[").append(Model.HIT_POSITION).append("]");
                }
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
        model.row = rand.nextInt(Model.SEA_SIZE);
        model.column = rand.nextInt(Model.SEA_SIZE);
    }

    private void populateCoordinate(Ship randomShip) {
        model.shipGrid[model.row][model.column] = randomShip;
    }

    private void switchPlayers() {
        if (model.currentPlayer == Player.X) {
            model.currentPlayer = Player.O;
        } else {
            model.currentPlayer = Player.X;
        }
    }

    private boolean isCoordinateAvailable() {
        return model.shipGrid[model.row][model.column] == null;
    }

    private void invalidateView() {
        view.setNewViewState(viewState);
    }

    @Override
    public void enteredInput(String input) {
        if (model.enterRow || model.enterColumn) {
            int enteredRow = 0;
            int enteredColumn = 0;
            try {
                if (model.enterRow) {
                    enteredRow = Integer.valueOf(input);
                }
                if (model.enterColumn) {
                    enteredColumn = Integer.valueOf(input);
                }
            } catch (NumberFormatException exception) {
                if (model.enterRow) {
                    enteredRow = -1;
                }
                if (model.enterColumn) {
                    enteredColumn = -1;
                }
            }
            if (model.enterRow) {
                if (!isXOrYWithinGridRange(enteredRow)) {
                    viewState.askForInput = true;
                    model.enterRow = true;
                    viewState.displayOutput = true;
                    viewState.output = Model.ENTER_ROW + model.currentPlayer;
                    invalidateView();
                    return;
                }
            }
            if (model.enterColumn) {
                if (!isXOrYWithinGridRange(enteredColumn)) {
                    viewState.askForInput = true;
                    model.enterColumn = true;
                    viewState.displayOutput = true;
                    viewState.output = Model.ENTER_COLUMN + model.currentPlayer;
                    invalidateView();
                    return;
                }
            }
            if (model.enterRow) {
                model.row = enteredRow;
                model.enterRow = false;
                model.enterColumn = true;
                viewState.output = Model.ENTER_COLUMN + model.currentPlayer;
                viewState.displayOutput = true;
                viewState.askForInput = true;
                invalidateView();
                return;
            }
            if (model.enterColumn) {
                model.column = enteredColumn;
                model.enterColumn = false;
                model.enterRow = true;
                viewState.displayOutput = true;
                viewState.askForInput = true;
                enteredCoordinate();
            }
            return;
        }
    }

    private void enteredCoordinate() {
//        if (model.shipGrid[model.row][model.column].player != null) {
//            if (model.shipGrid[model.row][model.column].shipHit) {
//                model.enterRow = true;
//                viewState.output = gridToString();
//                viewState.output += Model.SHIP_ALREADY_HIT + "\n";
//                viewState.output += Model.ENTER_ROW + model.currentPlayer;
//                viewState.displayOutput = true;
//                viewState.askForInput = true;
//                invalidateView();
//                return;
//            }
//        }
//        if (model.shipGrid[model.row][model.column].player != null) {
//            if (model.shipGrid[model.row][model.column].shipHit) {
//                model.enterRow = true;
//                viewState.output = gridToString();
//                viewState.output += Model.SHIP_ALREADY_HIT + "\n";
//                viewState.output += Model.ENTER_ROW + model.currentPlayer;
//                viewState.displayOutput = true;
//                viewState.askForInput = true;
//                invalidateView();
//                return;
//            }
//        }
//
//        if (model.shipGrid[model.row][model.column].player == model.currentPlayer && !model.shipGrid[model.row][model.column].shipHit) {
//            model.shipGrid[model.row][model.column].shipHit = true;
//            viewState.output = gridToString();
//            viewState.output += Model.HIT_OWN_SHIP + model.currentPlayer;
//        }
//        if (model.shipGrid[model.row][model.column].player != null && !model.shipGrid[model.row][model.column].shipHit && model.currentPlayer != model.shipGrid[model.row][model.column].player) {
//            model.shipGrid[model.row][model.column].shipHit = true;
//            viewState.output = gridToString();
//            viewState.output += Model.YOU_DESTROYED_A_SHIP + model.currentPlayer;
//        }
//
//        switchPlayers();
//        invalidateView();
    }

    private boolean isXOrYWithinGridRange(int value) {
        return value < Model.SEA_SIZE && value >= 0;
    }

    private boolean isGridSizeWithinRange(int value) {
        return value > 2;
    }

}

