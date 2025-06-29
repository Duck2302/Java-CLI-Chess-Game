package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;

public class Pawn extends Piece{
    public Pawn(Position position, Color color) {
        super(position, color, "Pawn");
    }
    @Override
    public Position[] getLegalMoves(ChessBoard chessBoard) {
        Position[] moves = new Position[4];
        int index = 0;

        int direction = (getColor() == Color.WHITE) ? 1 : -1;
        int startRow = (getColor() == Color.WHITE) ? 1 : 6;

        int x = getPosition().getX();
        int y = getPosition().getY();

        // Ein Feld vorwärts, nur wenn leer
        int newY = y + direction;
        if (newY >= 0 && newY < 8 && chessBoard.getBoardArray()[x][newY] == null) {
            moves[index++] = new Position(x, newY);

            // Zwei Felder vorwärts vom Startfeld, nur wenn beide leer
            if (y == startRow) {
                int twoForwardY = y + 2 * direction;
                if (twoForwardY >= 0 && twoForwardY < 8 && chessBoard.getBoardArray()[x][twoForwardY] == null) {
                    moves[index++] = new Position(x, twoForwardY);
                }
            }
        }

        // Schlagen nach links
        int leftX = x - 1;
        if (leftX >= 0 && newY >= 0 && newY < 8) {
            Piece target = chessBoard.getBoardArray()[leftX][newY];
            if (target != null && target.getColor() != this.getColor()) {
                moves[index++] = new Position(leftX, newY);
            }
        }
        // Schlagen nach rechts
        int rightX = x + 1;
        if (rightX < 8 && newY >= 0 && newY < 8) {
            Piece target = chessBoard.getBoardArray()[rightX][newY];
            if (target != null && target.getColor() != this.getColor()) {
                moves[index++] = new Position(rightX, newY);
            }
        }

        Position[] legalMoves = new Position[index];
        System.arraycopy(moves, 0, legalMoves, 0, index);
        return legalMoves;
    }
}
