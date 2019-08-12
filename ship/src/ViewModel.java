

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
        viewState.output = Model.PLAY_MODE_ENJOY;
        //viewState.askForInput = true;
        viewState.displayOutput = true;
        model.enterRow = true;
        model.enterColumn = false;
        model = new Model();
        populateGridWithShips();
        //TODO: method to add ships to sea(grid)
        //TODO: add a toString method to concert grid to string
        invalidateView();
    }

    private void populateGridWithShips() {

    }

    private void invalidateView() {
        view.setNewViewState(viewState);
    }

    @Override
    public void enteredInput(String input) {

    }
}

