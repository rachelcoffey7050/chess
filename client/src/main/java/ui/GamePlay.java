package ui;

import client.ServerFacade;

import java.util.Scanner;

public class GamePlay {

    private final ServerFacade facade;
    private final BoardPrinter printer;

    public GamePlay(ServerFacade facade, BoardPrinter printer){
        this.facade = facade;
        this.printer = printer;
    }

    public void runGamePlay(){
        Scanner sc = new Scanner(System.in);
        boolean playing = true;
        printer.printBoard();

        while (playing) {
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input: Please type a number");
                sc.nextLine();
                return;
            }

            int n = sc.nextInt();

            if (n == 1) {
                help();
            } else if (n == 2) {
                redrawChessBoard();
            } else if (n == 3) {
                leave();
                playing = false;
            } else if (n == 4) {
                makeMove();
            } else if (n == 5) {
                resign();
            } else if (n == 6) {
                highlight();
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    private void help(){
        System.out.println("Menu:\n1. Help\n2. Redraw Chess Board\n3. Leave\n4. Make Move\n5. Resign\n6. Highlight Legal Moves ");
    }

    private void leave(){
        System.out.println("Leaving Chess Game");
    }

    private void redrawChessBoard(){
        printer.printBoard();
    }

    private void makeMove(){
        // make move with websocket?
        printer.printBoard();
    }
     private void resign(){
         System.out.println("Game Over: Forfeited");
         // websocket ends game?
     }

     private void highlight(){
        //get list of legal moves from chess game and print them out
     }
}
