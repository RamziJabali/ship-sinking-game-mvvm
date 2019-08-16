
public enum Player {
    X("X"),
    O("O");

    public final String displayValue;
    public int points;

    public Player getOtherPlayer() {
        if (this == X) {
            return O;
        }
        return X;
    }

    Player(String displayValue) {
        this.displayValue = displayValue;
    }
}
