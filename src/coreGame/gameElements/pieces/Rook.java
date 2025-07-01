package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;

public class Rook extends Piece {
    private boolean hasMoved = false;

    public Rook(Position position, Color color) {
        super(position, color, "Rook");
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }

    @Override
    public Position[] getLegalMoves(ChessBoard chessBoard) {
        Position[] moves = new Position[14];
        int index = 0;
        int x = getPosition().getX();
        int y = getPosition().getY();

        int[][] directions = { {1,0}, {-1,0}, {0,1}, {0,-1} };
        for (int[] dir : directions) {
            for (int step = 1; step < 8; step++) {
                int newX = x + dir[0] * step;
                int newY = y + dir[1] * step;
                if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                    Piece target = chessBoard.getBoardArray()[newX][newY];
                    if (target == null) {
                        moves[index++] = new Position(newX, newY);
                    } else {
                        if (target.getColor() != this.getColor()) {
                            moves[index++] = new Position(newX, newY);
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        Position[] legalMoves = new Position[index];
        System.arraycopy(moves, 0, legalMoves, 0, index);
        return legalMoves;
    }
}
