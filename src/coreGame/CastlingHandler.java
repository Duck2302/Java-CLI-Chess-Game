package coreGame;

import coreGame.gameElements.*;
import coreGame.gameElements.pieces.*;

public class CastlingHandler {
    public static boolean isCastlingMove(Piece piece, Position from, Position to) {
        return piece instanceof King && Math.abs(to.getX() - from.getX()) == 2;
    }

    public static void performCastling(Piece[][] arr, Position from, Position to) {
        int y = from.getY();
        if (to.getX() == 6) { // kurze Rochade
            Piece rook = arr[7][y];
            arr[7][y] = null;
            arr[5][y] = rook;
            if (rook != null) rook.setPosition(new Position(5, y));
        } else if (to.getX() == 2) { // lange Rochade
            Piece rook = arr[0][y];
            arr[0][y] = null;
            arr[3][y] = rook;
            if (rook != null) rook.setPosition(new Position(3, y));
        }
    }

    public static void undoCastling(Piece[][] arr, Position from, Position to) {
        int y = from.getY();
        if (to.getX() == 6) { // kurze Rochade
            Piece rook = arr[5][y];
            arr[5][y] = null;
            arr[7][y] = rook;
            if (rook != null) rook.setPosition(new Position(7, y));
        } else if (to.getX() == 2) { // lange Rochade
            Piece rook = arr[3][y];
            arr[3][y] = null;
            arr[0][y] = rook;
            if (rook != null) rook.setPosition(new Position(0, y));
        }
    }
}

