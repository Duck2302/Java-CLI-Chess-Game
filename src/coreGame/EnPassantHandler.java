package coreGame;

import coreGame.gameElements.*;
import coreGame.gameElements.pieces.*;

public class EnPassantHandler {
    public static boolean isEnPassant(Piece piece, Position from, Position to, Move lastMove, Piece[][] arr) {
        if (!(piece instanceof Pawn) || lastMove == null) return false;
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        if (Math.abs(dx) == 1 && arr[to.getX()][to.getY()] == null) {
            if (piece.getColor() == Color.WHITE && dy == 1) {
                return lastMove.getMovedPiece() instanceof Pawn &&
                        lastMove.getFrom().getY() == 6 && lastMove.getTo().getY() == 4 &&
                        lastMove.getTo().getX() == to.getX() && lastMove.getTo().getY() == from.getY();
            } else if (piece.getColor() == Color.BLACK && dy == -1) {
                return lastMove.getMovedPiece() instanceof Pawn &&
                        lastMove.getFrom().getY() == 1 && lastMove.getTo().getY() == 3 &&
                        lastMove.getTo().getX() == to.getX() && lastMove.getTo().getY() == from.getY();
            }
        }
        return false;
    }

    public static void performEnPassant(Piece[][] arr, Position from, Position to) {
        int capturedY = from.getY();
        arr[to.getX()][capturedY] = null;
    }

    public static void undoEnPassant(Piece[][] arr, Position from, Position to, Piece captured) {
        int capturedY = from.getY();
        arr[to.getX()][capturedY] = captured;
    }
}
