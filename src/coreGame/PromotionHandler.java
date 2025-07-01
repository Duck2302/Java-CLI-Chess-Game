package coreGame;

import coreGame.gameElements.pieces.*;
import coreGame.gameElements.Position;

public class PromotionHandler {
    public static boolean isPromotion(Position position, Color color) {
        return (color == Color.WHITE && position.getY() == 7) ||
               (color == Color.BLACK && position.getY() == 0);
    }

    public static Piece promotePawn(Color color, int choice) {
        switch (choice) {
            case 1:
                return new Queen(null, color);
            case 2:
                return new Rook(null, color);
            case 3:
                return new Bishop(null, color);
            case 4:
                return new Knight(null, color);
            default:
                return new Queen(null, color);
        }
    }
}

