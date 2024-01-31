package simulation.generators;

import simulation.gamefield.Cell;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CellsGenerator {
    private final int MAP_WIDTH;
    private final int MAP_HEIGHT;
    private final int THREAD_POOL_SIZE;

    public CellsGenerator(int MAP_WIDTH, int MAP_HEIGHT) {
        this.MAP_WIDTH = MAP_WIDTH;
        this.MAP_HEIGHT = MAP_HEIGHT;
        this.THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    }

    public ArrayList<Cell> generateCells() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ArrayList<Future<Cell>> futures = new ArrayList<>();

        for (int y = 1; y <= MAP_HEIGHT; y++) {
            for (int x = 1; x <= MAP_WIDTH; x++) {
                int finalY = y;
                int finalX = x;
                Future<Cell> future = executorService.submit(() -> createCell(finalX, finalY));
                futures.add(future);
            }
        }

        ArrayList<Cell> generatedCells = new ArrayList<>();
        for (Future<Cell> future : futures) {
            try {
                Cell cell = future.get();
                generatedCells.add(cell);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        return generatedCells;
    }

    private Cell createCell(int x, int y) {
        return new Cell(x, y);
    }
}
