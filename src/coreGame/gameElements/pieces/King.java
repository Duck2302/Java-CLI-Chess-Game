package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;

public class King extends Piece {
    public King(Position position, Color color) {
        super(position, color, "King");
    }

    @Override
    public Position[] getLegalMoves(ChessBoard chessBoard) {
        // King can move one square in any direction
        Position[] moves = new Position[8];
        int index = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip the current position
                int newX = getPosition().getX() + dx;
                int newY = getPosition().getY() + dy;
                if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                    Piece target = chessBoard.getBoardArray()[newX][newY];
                    if (target == null || target.getColor() != this.getColor()) {
                        moves[index++] = new Position(newX, newY);
                    }
                }
            }
        }

        // Resize the array to the actual number of moves made
        Position[] legalMoves = new Position[index];
        System.arraycopy(moves, 0, legalMoves, 0, index);
        return legalMoves;
    }
}
