package ru.freeze.model;

public class Board {

    private volatile int[][] board = new int[8][8];

    public void createRook(int row, int col){
        if (board[row][col] != 1)
            board[row][col] = 1;

    }
    public boolean isFieldEmpty(int row, int col){
        return board[row][col] != 1;
    }

    public void move(int row, int col, int newRow, int newCol){
        board[newRow][newCol] = 1;
        board[row][col] = 0;

        //todo bug maybe here operations is not atomic =(
    }

    public void printBoard(){
        for (int i = 0; i < 8; i++) {
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j]+ "|");
            }
            System.out.println();
        }
    }

}
