package coreGame;

import coreGame.gameElements.ChessBoard;
import coreGame.gameElements.pieces.Color;
import coreGame.gameElements.Move;
import coreGame.gameElements.Position;
import coreGame.gameElements.pieces.Piece;
import coreGame.gameElements.pieces.Pawn;
import coreGame.gameElements.pieces.Queen;
import coreGame.gameElements.pieces.Rook;
import coreGame.gameElements.pieces.Bishop;
import coreGame.gameElements.pieces.Knight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessGame {
    private ChessBoard board;
    private GUI gui;
    private Color currentPlayer;

    public ChessGame() {
        board = new ChessBoard();
        // Standard: Konsolenmodus. Für Lanterna: gui = new GUI(true);
        gui = new GUI(); // oder: gui = new GUI(true);
        currentPlayer = Color.WHITE;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            gui.printBoard(board);
            if (board.isCheckmate(currentPlayer)) {
                System.out.println("Checkmate! " + (currentPlayer == Color.WHITE ? "black" : "white") + " wins!");
                break;
            }
            System.out.println("Turn: " + (currentPlayer == Color.WHITE ? "white" : "black"));

            // Erstelle die Liste der beweglichen Figuren jedes Mal neu (x = Spalte, y = Reihe)
            List<Piece> movablePieces = new ArrayList<>();
            List<Position> movablePositions = new ArrayList<>();
            Piece[][] arr = board.getBoardArray();
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    Piece p = arr[x][y];
                    if (p != null && p.getColor() == currentPlayer && p.hasLegalMoves(board)) {
                        movablePieces.add(p);
                        movablePositions.add(new Position(x, y));
                    }
                }
            }
            if (movablePieces.isEmpty()) {
                System.out.println("No piece with legal moves available.");
                break;
            }

            gui.printPieces(movablePieces.toArray(new Piece[0]));

            int pieceIndex = -1;
            while (true) {
                System.out.print("Choose a piece (number): ");
                String input = scanner.nextLine();
                try {
                    pieceIndex = Integer.parseInt(input) - 1;
                    if (pieceIndex >= 0 && pieceIndex < movablePieces.size()) {
                        break;
                    }
                } catch (Exception ignored) {}
                System.out.println("Invalid selection. Try again.");
            }
            // Hole die aktuelle Position und das Piece aus dem Board
            Position selectedPos = movablePositions.get(pieceIndex);
            Piece selected = arr[selectedPos.getX()][selectedPos.getY()];
            Position[] moves = selected.getLegalMoves(board);

            int moveIndex = -1;
            while (true) {
                gui.printLegalMoves(moves);
                System.out.print("Choose a move (Nnumber, 0 to go back): ");
                String input = scanner.nextLine();
                try {
                    moveIndex = Integer.parseInt(input) - 1;
                    if (moveIndex == -1) break; // zurück
                    if (moveIndex >= 0 && moveIndex < moves.length) {
                        // Nutze IMMER die gespeicherte Startposition, nicht selected.getPosition()
                        Position from = new Position(selectedPos.getX(), selectedPos.getY());
                        Position to = moves[moveIndex];
                        Piece captured = arr[to.getX()][to.getY()];
                        arr[from.getX()][from.getY()] = null;
                        arr[to.getX()][to.getY()] = selected;
                        selected.setPosition(to);
                        boolean illegal = board.isKingInCheck(currentPlayer);
                        // Rückgängig machen
                        arr[from.getX()][from.getY()] = selected;
                        arr[to.getX()][to.getY()] = captured;
                        selected.setPosition(from);
                        if (illegal) {
                            System.out.println("This move would set you own king checkmate!");
                            continue;
                        }
                        // Zug ausführen
                        Move move = new Move(from, to, selected, captured);
                        board.makeMove(move);

                        // Prüfe auf Bauernumwandlung
                        if (selected instanceof Pawn && isPawnPromotion(to, selected.getColor())) {
                            Piece promotedPiece = choosePawnPromotion(scanner, selected.getColor());
                            promotedPiece.setPosition(to);
                            System.out.println(to.toString());
                            arr[to.getX()][to.getY()] = promotedPiece;
                        }

                        break;
                    }
                } catch (Exception ignored) {}
                System.out.println("Invalid selection. Try again.");
            }
            if (moveIndex == -1) continue; // zurück zur Figurenwahl

            // Nach dem Zug Spieler wechseln
            currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
        }
        System.out.println("Game finished.");
    }

    private boolean isPawnPromotion(Position position, Color color) {
        return (color == Color.WHITE && position.getY() == 7) ||
                (color == Color.BLACK && position.getY() == 0);
    }

    private Piece choosePawnPromotion(Scanner scanner, Color color) {
        System.out.println("Pawn promotion! Choose a piece:");
        System.out.println("1. Queen");
        System.out.println("2. Rook");
        System.out.println("3. Bishop");
        System.out.println("4. Knight");

        while (true) {
            System.out.print("Choose (1-4): ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        return new Queen(null, color);
                    case 2:
                        return new Rook( null, color);
                    case 3:
                        return new Bishop( null, color);
                    case 4:
                        return new Knight( null, color);
                    default:
                        System.out.println("Invalid choice. Please choose 1-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}