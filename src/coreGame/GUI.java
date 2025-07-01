package coreGame;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.Position;
import coreGame.gameElements.pieces.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


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
            // Nur (castle) anzeigen, wenn es sich wirklich um eine Rochade handelt
            if ((moves[i].getX() == 6 || moves[i].getX() == 2) &&
                (moves[i].getY() == 0 || moves[i].getY() == 7)) {
                // Prüfen, ob das aktuelle Piece ein König ist und der Zug eine Rochade ist
                // Dazu benötigen wir das aktuelle Piece und die Startposition
                // Diese Information muss an die Methode übergeben werden
                // Daher: Methode um Piece und From-Position erweitern
                extra = " (castle)"; // Platzhalter, wird gleich verbessert
            }
            System.out.printf("%d: %s%s%s\n", i + 1, x, y, extra);
        }
        System.out.println("0: Back");
    }

    public int askForPieceSelection(int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Choose a piece (number): ");
            String input = scanner.nextLine();
            try {
                int pieceIndex = Integer.parseInt(input) - 1;
                if (pieceIndex >= 0 && pieceIndex < max) {
                    return pieceIndex;
                }
            } catch (Exception ignored) {}
            System.out.println("Invalid selection. Try again.");
        }
    }

    public int askForMoveSelection(int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Choose a move (number, 0 to go back): ");
            String input = scanner.nextLine();
            try {
                int moveIndex = Integer.parseInt(input) - 1;
                if (moveIndex == -1) return -1; // zurück
                if (moveIndex >= 0 && moveIndex < max) {
                    return moveIndex;
                }
            } catch (Exception ignored) {}
            System.out.println("Invalid selection. Try again.");
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public Piece choosePawnPromotion(coreGame.gameElements.pieces.Color color) {
        System.out.println("Pawn promotion! Choose a piece:");
        System.out.println("1. Queen");
        System.out.println("2. Rook");
        System.out.println("3. Bishop");
        System.out.println("4. Knight");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Choose (1-4): ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        return new coreGame.gameElements.pieces.Queen(null, color);
                    case 2:
                        return new coreGame.gameElements.pieces.Rook(null, color);
                    case 3:
                        return new coreGame.gameElements.pieces.Bishop(null, color);
                    case 4:
                        return new coreGame.gameElements.pieces.Knight(null, color);
                    default:
                        System.out.println("Invalid choice. Please choose 1-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public int askForPromotionChoice() {
        System.out.println("Pawn promotion! Choose a piece:");
        System.out.println("1. Queen");
        System.out.println("2. Rook");
        System.out.println("3. Bishop");
        System.out.println("4. Knight");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Choose (1-4): ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid choice. Please choose 1-4.");
        }
    }
}