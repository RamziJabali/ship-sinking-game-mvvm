package com.eljabali;

import static com.eljabali.Model.*;

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
        viewState.output = PLAY_MODE_ENJOY;
        viewState.output += ENTER_ROW;
        viewState.askForInput = true;
        viewState.displayOutput = true;
        model.enterRow = true;
        model.enterColumn = false;
        model = new Model();
        //TODO: method to add ships to sea(grid)
        //TODO: add a toString method to concert grid to string
        //TODO: add a toString method to concert grid to string
        invalidateView();
    }
    private void invalidateView() {
        view.setNewViewState(viewState);
    }
    @Override
    public void enteredInput(String input) {

    }
}

