package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;
import coreGame.gameElements.Move;

public class Pawn extends Piece{
    public Pawn(Position position, Color color) {
        super(position, color, "Pawn");
    }

    // Standardmethode bleibt für Kompatibilität
    @Override
    public Position[] getLegalMoves(ChessBoard chessBoard) {
        return getLegalMoves(chessBoard, null);
    }

    // Überladene Methode für En Passant
    public Position[] getLegalMoves(ChessBoard chessBoard, Move lastMove) {
        Position[] moves = new Position[6];
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

        // En Passant prüfen
        if (lastMove != null && lastMove.getMovedPiece() instanceof Pawn) {
            int lastFromY = lastMove.getFrom().getY();
            int lastToY = lastMove.getTo().getY();
            int lastToX = lastMove.getTo().getX();
            // Prüfe, ob der gegnerische Bauer gerade zwei Felder gezogen ist und neben diesem steht
            if (Math.abs(lastToY - lastFromY) == 2 && lastToY == y) {
                if (Math.abs(lastToX - x) == 1) {
                    // En Passant möglich
                    int epX = lastToX;
                    int epY = y + direction;
                    if (epY >= 0 && epY < 8 && chessBoard.getBoardArray()[epX][epY] == null) {
                        moves[index++] = new Position(epX, epY);
                    }
                }
            }
        }

        Position[] legalMoves = new Position[index];
        System.arraycopy(moves, 0, legalMoves, 0, index);
        return legalMoves;
    }
}
