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
        viewState.output = Model.PLAY_MODE_ENJOY+ "\n";
        viewState.output += gridToString();
        viewState.displayOutput = true;
//        viewState.askForInput = true;
//        model.enterRow = true;
//        model.enterColumn = false;
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
                if(model.shipGrid[row][column] == null){
                    grid.append("[").append(Model.UNHIT_POSITION).append("]");
                }
                if(model.shipGrid[row][column] != null && !model.shipGrid[row][column].shipHit){
                    grid.append("[").append(Model.UNHIT_POSITION).append("]");
                }

                if(model.shipGrid[row][column] != null && model.shipGrid[row][column].shipHit){
                    grid.append("[").append(Model.HIT_POSITION).append("]");
                }
            }
            grid.append("\n");
        }
        return grid.toString();
    }

    private void populateGridWithShips() {
        for(int numOfShips = 0; numOfShips < Model.NUMBER_SHIPS; numOfShips++) {
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

    }
}

