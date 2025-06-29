package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;

public class Bishop extends Piece {
    public Bishop(Position position, Color color) {
        super(position, color, "Bishop");
    }
    @Override
    public Position[] getLegalMoves(ChessBoard chessBoard) {
        // Bishops move diagonally, so we need to calculate all possible diagonal moves
        Position[] moves = new Position[13]; // Max 13 diagonal moves in one direction
        int index = 0;

        // Check all four diagonal directions
        for (int dx = -1; dx <= 1; dx += 2) {
            for (int dy = -1; dy <= 1; dy += 2) {
                for (int step = 1; step < 8; step++) {
                    int newX = getPosition().getX() + dx * step;
                    int newY = getPosition().getY() + dy * step;
                    if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                        Piece target = chessBoard.getBoardArray()[newX][newY];
                        if (target == null) {
                            moves[index++] = new Position(newX, newY);
                        } else {
                            if (target.getColor() != this.getColor()) {
                                moves[index++] = new Position(newX, newY);
                            }
                            break; // Blockiert
                        }
                    } else {
                        break; // Out of bounds
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
