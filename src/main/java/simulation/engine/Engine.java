package simulation.engine;


import lombok.Getter;
import simulation.engine.handlers.MoveHandler;
import simulation.engine.handlers.NutritionHandler;
import simulation.engine.handlers.ReproductionHandler;
import simulation.gamefield.Cell;
import simulation.gamefield.GameMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Engine {
    @Getter
    private final GameMap GAME_MAP;
    private final ExecutorService EXECUTOR_SERVICE;

    public Engine(int width, int height) {
        this.GAME_MAP = new GameMap(width, height);
        this.EXECUTOR_SERVICE = Executors.newFixedThreadPool(width * height);
    }

    public void simulate() {
        int round = 1;
        System.out.println("Start");
        printSimulationState();
        while (!GAME_MAP.allOrganismsAreExtinct()) {
            System.out.println("Round " + round);

            executeInParallel(cell -> new NutritionHandler().handleNutrition(cell));

            executeInParallel(cell -> new MoveHandler().moveAllAnimals(cell, GAME_MAP));

            executeInParallel(cell -> new ReproductionHandler().reproduceAll(cell));

            executeInParallel(cell -> new NutritionHandler().checkAndHandleDeath(cell));

            executeInParallel(cell -> new NutritionHandler().decreaseSaturation(cell));

            printSimulationState();
            round++;
        }

        EXECUTOR_SERVICE.shutdown();
    }

    private void executeInParallel(CellOperation cellOperation) {
        for (Cell cell : GAME_MAP.getCELLS()) {
            EXECUTOR_SERVICE.submit(() -> cellOperation.operate(cell));
        }

        try {
            EXECUTOR_SERVICE.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printSimulationState() {
        AtomicInteger totalPlants = new AtomicInteger(0);
        AtomicInteger totalAnimals = new AtomicInteger(0);

        for (Cell cell : GAME_MAP.getCELLS()) {
            int plantsInCell = cell.getPlants().size();
            int animalsInCell = cell.getAnimals().size();

            totalPlants.addAndGet(plantsInCell);
            totalAnimals.addAndGet(animalsInCell);

            System.out.println("Cell (" + cell.getX() + ", " + cell.getY() + "): " +
                    "Plants: " + plantsInCell +
                    ", Animals: " + animalsInCell);
        }

        System.out.println("Total on the island - Plants: " + totalPlants.get() + ", Animals: " + totalAnimals.get());
        System.out.println("------");
    }


    @FunctionalInterface
    private interface CellOperation {
        void operate(Cell cell);
    }
}

