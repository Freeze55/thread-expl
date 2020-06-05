package ru.freeze;

import javafx.util.Pair;
import ru.freeze.model.Board;
import ru.freeze.model.ChessRook;

import java.util.concurrent.*;


public class Main {

    public static final Integer COUNT = 5;
    public static volatile Board board = new Board();

    public static void main(String[] args) throws ExecutionException, InterruptedException {



        ExecutorService threadPool = Executors.newFixedThreadPool(COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        board.createRook(0,0);
        board.createRook(2,3);
        board.createRook(2,4);
        board.createRook(7,7);
        board.createRook(4,3);
        board.printBoard();

        long startTime = System.currentTimeMillis();
        threadPool.execute(new ChessRook("one", board, countDownLatch, new Pair<>(0,0)));
        threadPool.execute(new ChessRook("second", board, countDownLatch, new Pair<>(2,3)));
        threadPool.execute(new ChessRook("third", board, countDownLatch, new Pair<>(2,4)));
        threadPool.execute(new ChessRook("four", board, countDownLatch, new Pair<>(7,7)));
        threadPool.execute(new ChessRook("five", board, countDownLatch, new Pair<>(4,3)));


        countDownLatch.await();
        System.out.println((System.currentTimeMillis() - startTime) );
        threadPool.shutdown();
        System.out.println("done");
        board.printBoard();

    }
}
