package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;

public class Knight extends Piece {
    public Knight(Position position, Color color) {
        super(position, color, "Knight");
    }

    @Override
    public Position[] getLegalMoves(ChessBoard chessBoard) {
        // Knight moves in an "L" shape: two squares in one direction and one square perpendicular
        Position[] moves = new Position[8];
        int index = 0;

        int[][] knightMoves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : knightMoves) {
            int newX = getPosition().getX() + move[0];
            int newY = getPosition().getY() + move[1];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Piece target = chessBoard.getBoardArray()[newX][newY];
                if (target == null || target.getColor() != this.getColor()) {
                    moves[index++] = new Position(newX, newY);
                }
            }
        }

        // Resize the array to the actual number of moves made
        Position[] legalMoves = new Position[index];
        System.arraycopy(moves, 0, legalMoves, 0, index);
        return legalMoves;
    }
}
