// language: java
package coreGame.gameElements;

import coreGame.gameElements.pieces.Piece;

public class Move {
    private Position from;
    private Position to;
    private Piece movedPiece;
    private Piece capturedPiece;

    public Move(Position from, Position to, Piece movedPiece, Piece capturedPiece) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    // Korrigierte Koordinatenzuweisung
    public void execute(ChessBoard board) {
        Piece[][] arr = board.getBoardArray();
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        arr[fromX][fromY] = null;
        arr[toX][toY] = movedPiece;
        movedPiece.setPosition(to);
    }
}