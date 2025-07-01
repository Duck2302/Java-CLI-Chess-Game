package coreGame;

import coreGame.gameElements.*;
import coreGame.gameElements.pieces.*;

public class MoveValidator {
    public static boolean isKingInCheck(ChessBoard board, Color color) {
        return board.isKingInCheck(color);
    }

    public static boolean isCheckmate(ChessBoard board, Color color) {
        return board.isCheckmate(color);
    }

    public static boolean isLegalMove(ChessBoard board, Move move, Color currentPlayer, Move lastMove) {
        // Hier kann die Logik für die Prüfung eines legalen Zuges ausgelagert werden
        // (z.B. En Passant, Rochade, eigene Figur schlagen, etc.)
        // Fürs Erste: Prüfe, ob der Zug den eigenen König ins Schach setzt
        Piece[][] arr = board.getBoardArray();
        Position from = move.getFrom();
        Position to = move.getTo();
        Piece selected = arr[from.getX()][from.getY()];
        Piece captured = arr[to.getX()][to.getY()];
        arr[from.getX()][from.getY()] = null;
        arr[to.getX()][to.getY()] = selected;
        selected.setPosition(to);
        boolean illegal = board.isKingInCheck(currentPlayer);
        // Rückgängig machen
        arr[from.getX()][from.getY()] = selected;
        arr[to.getX()][to.getY()] = captured;
        selected.setPosition(from);
        return !illegal;
    }
}

