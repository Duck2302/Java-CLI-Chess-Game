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

        // Rochade
        if (movedPiece instanceof coreGame.gameElements.pieces.King && Math.abs(toX - fromX) == 2) {
            // kurze Rochade
            if (toX == 6) {
                Piece rook = arr[7][fromY];
                arr[7][fromY] = null;
                arr[5][fromY] = rook;
                rook.setPosition(new Position(5, fromY));
                if (rook instanceof coreGame.gameElements.pieces.Rook) {
                    ((coreGame.gameElements.pieces.Rook)rook).setHasMoved(true);
                }
            }
            // lange Rochade
            if (toX == 2) {
                Piece rook = arr[0][fromY];
                arr[0][fromY] = null;
                arr[3][fromY] = rook;
                rook.setPosition(new Position(3, fromY));
                if (rook instanceof coreGame.gameElements.pieces.Rook) {
                    ((coreGame.gameElements.pieces.Rook)rook).setHasMoved(true);
                }
            }
            if (movedPiece instanceof coreGame.gameElements.pieces.King) {
                ((coreGame.gameElements.pieces.King)movedPiece).setHasMoved(true);
            }
        }

        arr[fromX][fromY] = null;
        arr[toX][toY] = movedPiece;
        movedPiece.setPosition(to);

        // Setze hasMoved für König und Turm
        if (movedPiece instanceof coreGame.gameElements.pieces.King) {
            ((coreGame.gameElements.pieces.King)movedPiece).setHasMoved(true);
        }
        if (movedPiece instanceof coreGame.gameElements.pieces.Rook) {
            ((coreGame.gameElements.pieces.Rook)movedPiece).setHasMoved(true);
        }
    }
}