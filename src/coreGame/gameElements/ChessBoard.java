package coreGame.gameElements;
import coreGame.gameElements.pieces.*;

public class ChessBoard {
    private Piece[][] chessBoard;
    public ChessBoard() {
        this.chessBoard = new Piece[8][8];
        initChessBoard();

    }

    public Piece[][] getBoardArray() {
        return chessBoard;
    }

    public Piece[] getWhitePieces() {
        Piece[] whitePieces = new Piece[16];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j] != null && chessBoard[i][j].getColor() == Color.WHITE) {
                    whitePieces[index++] = chessBoard[i][j];
                }
            }
        }
        return whitePieces;
    }

    public Piece[] getBlackPieces() {
        Piece[] blackPieces = new Piece[16];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j] != null && chessBoard[i][j].getColor() == Color.BLACK) {
                    blackPieces[index++] = chessBoard[i][j];
                }
            }
        }
        return blackPieces;
    }



    private void initChessBoard() {
        initPawns();
        initRooks();
        initKnights();
        initBishops();
        initQueens();
        initKings();
    }

    private void initPawns() {
        for (int i = 0; i < 8; i++) {
            chessBoard[i][1] = new Pawn(new Position(i, 1), Color.WHITE);
            chessBoard[i][6] = new Pawn(new Position(i, 6), Color.BLACK);
        }
    }

    private void initRooks() {
        chessBoard[0][0] = new Rook(new Position(0, 0), Color.WHITE);
        chessBoard[7][0] = new Rook(new Position(7, 0), Color.WHITE);
        chessBoard[0][7] = new Rook(new Position(0, 7), Color.BLACK);
        chessBoard[7][7] = new Rook(new Position(7, 7), Color.BLACK);
    }

    private void initKnights() {
        chessBoard[1][0] = new Knight(new Position(1, 0), Color.WHITE);
        chessBoard[6][0] = new Knight(new Position(6, 0), Color.WHITE);
        chessBoard[1][7] = new Knight(new Position(1, 7), Color.BLACK);
        chessBoard[6][7] = new Knight(new Position(6, 7), Color.BLACK);
    }
    private void initBishops() {
        chessBoard[2][0] = new Bishop(new Position(2, 0), Color.WHITE);
        chessBoard[5][0] = new Bishop(new Position(5, 0), Color.WHITE);
        chessBoard[2][7] = new Bishop(new Position(2, 7), Color.BLACK);
        chessBoard[5][7] = new Bishop(new Position(5, 7), Color.BLACK);
    }

    private void initQueens() {
        chessBoard[3][0] = new Queen(new Position(3, 0), Color.WHITE);
        chessBoard[3][7] = new Queen(new Position(3, 7), Color.BLACK);
    }
    private void initKings() {
        chessBoard[4][0] = new King(new Position(4, 0), Color.WHITE);
        chessBoard[4][7] = new King(new Position(4, 7), Color.BLACK);
    }

    // Führt einen Zug aus und gibt das geschlagene Piece zurück (falls vorhanden)
    public Piece makeMove(Move move) {
        Piece captured = getBoardArray()[move.getTo().getX()][move.getTo().getY()];
        move.execute(this);
        return captured;
    }

    // Prüft, ob der König der gegebenen Farbe im Schach steht
    public boolean isKingInCheck(Color color) {
        Position kingPos = null;
        Piece[][] arr = getBoardArray();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = arr[i][j];
                if (p != null && p instanceof King && p.getColor() == color) {
                    kingPos = p.getPosition();
                }
            }
        }
        if (kingPos == null) return false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = arr[i][j];
                if (p != null && p.getColor() != color) {
                    for (Position pos : p.getLegalMoves(this)) {
                        if (pos.getX() == kingPos.getX() && pos.getY() == kingPos.getY()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Prüft, ob die gegebene Farbe schachmatt ist
    public boolean isCheckmate(Color color) {
        if (!isKingInCheck(color)) return false;
        Piece[][] arr = getBoardArray();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = arr[i][j];
                if (p != null && p.getColor() == color) {
                    for (Position move : p.getLegalMoves(this)) {
                        // Simuliere Zug
                        Piece captured = arr[move.getX()][move.getY()];
                        Position oldPos = p.getPosition();
                        arr[oldPos.getX()][oldPos.getY()] = null;
                        arr[move.getX()][move.getY()] = p;
                        p.setPosition(move);
                        boolean stillInCheck = isKingInCheck(color);
                        // Rückgängig machen
                        arr[oldPos.getX()][oldPos.getY()] = p;
                        arr[move.getX()][move.getY()] = captured;
                        p.setPosition(oldPos);
                        if (!stillInCheck) return false;
                    }
                }
            }
        }
        return true;
    }
}
