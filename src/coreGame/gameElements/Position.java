package coreGame.gameElements;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Position fromChessNotation(String notation) {
        if (notation.length() != 2) return null;
        char file = notation.charAt(0);
        char rank = notation.charAt(1);
        int x = file - 'a';
        int y = Character.getNumericValue(rank) - 1;
        if (x < 0 || x > 7 || y < 0 || y > 7) return null;
        return new Position(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
