package ru.freeze.model;

import javafx.util.Pair;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ChessRook implements Runnable {

    private Integer countMoves = 0;
    private volatile CountDownLatch countDownLatch;
    private Random random = new Random();
    private String name;
    private volatile Board board;

    private Pair<Integer, Integer> placeIndexes;

    public ChessRook(String name, Board board, CountDownLatch countDownLatch,
                     Pair<Integer, Integer> initPlaceIndexes) {

        this.countDownLatch = countDownLatch;
        this.placeIndexes = initPlaceIndexes;
        this.name = name;
        this.board = board;
    }

    @Override
    public void run() {
        try {

            System.out.println("run");
            while (countMoves < 50) {
                Pair<Integer, Integer> nextMove = calculateCoordinatesForMove();
                System.out.println("[" + name + "] planning to move row:" + nextMove.getKey() + " col:" + nextMove.getValue());
                long startTime = System.currentTimeMillis();
                do {
                    if (board.isFieldEmpty(nextMove.getKey(), nextMove.getValue())) {
                        board.move(placeIndexes.getKey(), placeIndexes.getValue(), nextMove.getKey(), nextMove.getValue());
                        System.out.println("[" + name + "] moving" );
                        placeIndexes = nextMove;
                        countMoves++;
                        Thread.sleep(random.nextInt(200) + 100);
                        break;
                    }
                    System.out.println("[" + name + "] field is busy now" );

                } while ( (System.currentTimeMillis() - startTime) < 5000 );

                System.out.println("[" + name + "] moves "+ countMoves );
            }
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Pair<Integer, Integer> calculateCoordinatesForMove() {

        if (random.nextBoolean()) {
            //find new move in row
            int newValueRow = random.nextInt(8);
            while (newValueRow == placeIndexes.getKey()) {
                newValueRow = random.nextInt(8);
            }

            return new Pair<>(newValueRow, placeIndexes.getValue());
        } else {
            //find new move in line
            int newValueLine = random.nextInt(8);
            while (newValueLine == placeIndexes.getValue()) {
                newValueLine = random.nextInt(8);
            }
            return new Pair<>(placeIndexes.getValue(), newValueLine);
        }
    }
}
