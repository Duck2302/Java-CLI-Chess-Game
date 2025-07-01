package coreGame.gameElements.pieces;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;

public class King extends Piece {
    private boolean hasMoved = false;

    public King(Position position, Color color) {
        super(position, color, "King");
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }

    @Override
    public Position[] getLegalMoves(ChessBoard chessBoard) {
        // King can move one square in any direction
        Position[] moves = new Position[8 + 2]; // +2 für Rochade
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

        // Rochade prüfen
        if (!hasMoved && !isInCheckNoCastle(chessBoard)) {
            int y = (getColor() == Color.WHITE) ? 0 : 7;
            // kurze Rochade
            if (canCastle(chessBoard, true)) {
                moves[index++] = new Position(6, y);
            }
            // lange Rochade
            if (canCastle(chessBoard, false)) {
                moves[index++] = new Position(2, y);
            }
        }

        // Resize the array to the actual number of moves made
        Position[] legalMoves = new Position[index];
        System.arraycopy(moves, 0, legalMoves, 0, index);
        return legalMoves;
    }

    // Hilfsmethode, die keine Rochade prüft (verhindert Endlosschleife)
    private boolean isInCheckNoCastle(ChessBoard chessBoard) {
        Position kingPos = getPosition();
        Piece[][] arr = chessBoard.getBoardArray();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = arr[i][j];
                if (p != null && p.getColor() != getColor()) {
                    if (p instanceof King) {
                        int dx = Math.abs(p.getPosition().getX() - kingPos.getX());
                        int dy = Math.abs(p.getPosition().getY() - kingPos.getY());
                        if ((dx <= 1 && dy <= 1) && (dx + dy != 0)) {
                            return true;
                        }
                        continue;
                    }
                    for (Position pos : p.getLegalMoves(chessBoard)) {
                        if (pos.getX() == kingPos.getX() && pos.getY() == kingPos.getY()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isInCheck(ChessBoard chessBoard) {
        // Nutze Board-Logik
        return chessBoard.isKingInCheck(getColor());
    }

    private boolean canCastle(ChessBoard chessBoard, boolean kingside) {
        int y = (getColor() == Color.WHITE) ? 0 : 7;
        int rookX = kingside ? 7 : 0;
        Piece rook = chessBoard.getBoardArray()[rookX][y];
        if (!(rook instanceof Rook)) return false;
        if (((Rook)rook).hasMoved()) return false;
        // Felder zwischen König und Turm müssen leer sein
        int dir = kingside ? 1 : -1;
        int start = getPosition().getX();
        int end = kingside ? 6 : 1;
        for (int x = start + dir; kingside ? x < 7 : x > 0; x += dir) {
            if (chessBoard.getBoardArray()[x][y] != null) return false;
        }
        // König darf nicht durch bedrohte Felder ziehen
        for (int x = start; kingside ? x <= 6 : x >= 2; x += dir) {
            Position pos = new Position(x, y);
            // Simuliere König auf diesem Feld
            Position oldPos = getPosition();
            Piece[][] arr = chessBoard.getBoardArray();
            Piece tmp = arr[x][y];
            arr[oldPos.getX()][oldPos.getY()] = null;
            arr[x][y] = this;
            setPosition(pos);
            boolean inCheck = chessBoard.isKingInCheck(getColor());
            arr[oldPos.getX()][oldPos.getY()] = this;
            arr[x][y] = tmp;
            setPosition(oldPos);
            if (inCheck) return false;
        }
        return true;
    }
}
