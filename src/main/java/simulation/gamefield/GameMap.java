package simulation.gamefield;

import lombok.Getter;
import simulation.generators.CellsGenerator;

import java.util.ArrayList;

@Getter
public class GameMap {
    private final ArrayList<Cell> CELLS;
    private final int WIDTH;
    private final int HEIGHT;

    public GameMap(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CELLS = new CellsGenerator(width, height).generateCells();
    }

    public boolean allOrganismsAreExtinct() {
        for (Cell cell : this.getCELLS()) {
            if (!cell.getOrganisms().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
