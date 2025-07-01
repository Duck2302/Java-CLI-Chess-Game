package coreGame;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;
import coreGame.gameElements.pieces.Piece;

import java.util.HashMap;
import java.util.Map;


public class GUI {
    private final Map<String, String> pieceSymbols;


    public GUI() {
        this.pieceSymbols = createPieceSymbolMap();
    }


    private Map<String, String> createPieceSymbolMap() {
        Map<String, String> map = new HashMap<>();
        map.put("King_BLACK", "♔");
        map.put("Queen_BLACK", "♕");
        map.put("Rook_BLACK", "♖");
        map.put("Bishop_BLACK", "♗");
        map.put("Knight_BLACK", "♘");
        map.put("Pawn_BLACK", "♙");
        map.put("King_WHITE", "♚");
        map.put("Queen_WHITE", "♛");
        map.put("Rook_WHITE", "♜");
        map.put("Bishop_WHITE", "♝");
        map.put("Knight_WHITE", "♞");
        map.put("Pawn_WHITE", "♟");
        return map;
    }


    public void printBoard(ChessBoard board) {
        Piece[][] arr = board.getBoardArray();
        System.out.println();
        for (int y = 7; y >= 0; y--) {
            System.out.print((y + 1) + " ");
            for (int x = 0; x < 8; x++) {
                Piece p = arr[x][y];
                if (p != null) {
                    String symbolKey = p.getName() + "_" + p.getColor().name();
                    String symbol = pieceSymbols.getOrDefault(symbolKey, p.getName());
                    System.out.print("\t" + symbol);
                } else {
                    if ((x + y) % 2 == 0) {
                        System.out.print("\t □ ");
                    } else {
                        System.out.print("\t ■ ");
                    }
                }
            }
            System.out.println();
        }
        System.out.print("  ");
        for (char c = 'a'; c <= 'h'; c++) {
            System.out.print("\t " + c + " ");
        }
        System.out.println("\n");
    }


    public void printPieces(Piece[] pieces) {
        System.out.println("Available pieces:");
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] != null) {
                Position pos = pieces[i].getPosition();
                char x = (char) ('a' + pos.getX());
                int y = pos.getY() + 1;
                System.out.printf("%d:\t %s %s%s\n", i + 1, pieces[i].getName(), x, y);
            }
        }
    }

    public void printLegalMoves(Position[] moves) {
        System.out.println("Possible moves:");
        for (int i = 0; i < moves.length; i++) {
            char x = (char) ('a' + moves[i].getX());
            int y = moves[i].getY() + 1;
            String extra = "";
            // Rochade markieren: kurze (g1/g8), lange (c1/c8)
            if ((moves[i].getX() == 6 || moves[i].getX() == 2) &&
                (moves[i].getY() == 0 || moves[i].getY() == 7)) {
                extra = " (castle)";
            }
            System.out.printf("%d: %s%s%s\n", i + 1, x, y, extra);
        }
        System.out.println("0: Back");
    }
}