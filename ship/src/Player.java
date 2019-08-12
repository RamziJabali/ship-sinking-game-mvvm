
public enum Player {
    X("X"),
    O("O"),
    NH("."),
    HIT("*"),
    MISS("X");

    public final String displayValue;

    Player(String displayValue) {
        this.displayValue = displayValue;
    }
}
