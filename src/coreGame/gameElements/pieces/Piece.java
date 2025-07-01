package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;

public abstract  class Piece {
    private final Color color;
    private Position position;
    protected String name;

    /**
     * Gibt alle legalen Züge zurück. Für Bauern prüft diese Methode KEIN En Passant.
     * Für En Passant siehe Pawn#getLegalMoves(ChessBoard, Move).
     */
    abstract public Position[] getLegalMoves(ChessBoard chessBoard);

    public Piece(Position position, Color color, String name) {
        this.position = position;
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getPieceInformation() {
        return "Name: " + getName() +
                ", Color: " + getColor() +
                ", Position: " + getPosition();
    }

    public boolean hasLegalMoves(ChessBoard board) {
        return getLegalMoves(board).length > 0;
    }
}