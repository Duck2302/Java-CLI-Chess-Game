package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;

public class Queen extends Piece {
    public Queen(Position position, Color color) {
        super(position, color, "Queen");
    }

    @Override
    public Position[] getLegalMoves(ChessBoard chessBoard) {
        Position[] moves = new Position[27];
        int index = 0;
        int x = getPosition().getX();
        int y = getPosition().getY();

        // Richtungen: Läufer (diagonal)
        for (int dx = -1; dx <= 1; dx += 2) {
            for (int dy = -1; dy <= 1; dy += 2) {
                for (int step = 1; step < 8; step++) {
                    int newX = x + dx * step;
                    int newY = y + dy * step;
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
        }
        // Richtungen: Turm (horizontal/vertikal)
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
