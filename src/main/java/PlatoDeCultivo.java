import java.util.Random;

public class PlatoDeCultivo {
    private static final int SIZE = 20;
    private int[][] bacteriaGrid;
    private int[][] foodGrid;
    private Random random;

    public PlatoDeCultivo() {
        bacteriaGrid = new int[SIZE][SIZE];
        foodGrid = new int[SIZE][SIZE];
        random = new Random();
    }

    public void inicializarPlato(int initialBacteriaCount, int initialFood) {
        // Initialize bacteria in the center 4x4 grid
        int centerStart = SIZE / 2 - 2;
        int bacteriaPerCell = initialBacteriaCount / 16;

        for (int i = centerStart; i < centerStart + 4; i++) {
            for (int j = centerStart; j < centerStart + 4; j++) {
                bacteriaGrid[i][j] = bacteriaPerCell;
            }
        }

        // Initialize food evenly across the grid
        int foodPerCell = initialFood / (SIZE * SIZE);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                foodGrid[i][j] = foodPerCell;
            }
        }
    }

    public void simularDia(int foodAmount) {
        // Distribute new food
        int foodPerCell = foodAmount / (SIZE * SIZE);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                foodGrid[i][j] += foodPerCell;
            }
        }

        // Simulate each cell
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                simularCelda(i, j);
            }
        }
    }

    private void simularCelda(int x, int y) {
        int bacteriaCount = bacteriaGrid[x][y];
        int foodCount = foodGrid[x][y];

        if (bacteriaCount == 0) return;

        int newBacteria = 0;
        int foodConsumed = 0;

        for (int i = 0; i < bacteriaCount; i++) {
            if (foodCount >= 100) {
                foodCount -= 20;
                foodConsumed += 20;
                int result = random.nextInt(100);
                if (result < 3) {
                    // Bacteria dies
                } else if (result >= 3 && result < 60) {
                    // Bacteria stays
                } else {
                    // Bacteria moves
                    moverBacteria(x, y);
                }
            } else if (foodCount >= 10) {
                foodCount -= 10;
                foodConsumed += 10;
                int result = random.nextInt(100);
                if (result < 6) {
                    // Bacteria dies
                } else if (result >= 6 && result < 20) {
                    // Bacteria stays
                } else {
                    // Bacteria moves
                    moverBacteria(x, y);
                }
            } else {
                int result = random.nextInt(100);
                if (result < 20) {
                    // Bacteria dies
                } else if (result >= 20 && result < 60) {
                    // Bacteria stays
                } else {
                    // Bacteria moves
                    moverBacteria(x, y);
                }
            }
        }

        if (foodConsumed >= 150) {
            newBacteria = bacteriaCount * 3;
        } else if (foodConsumed >= 100) {
            newBacteria = bacteriaCount * 2;
        } else if (foodConsumed >= 50) {
            newBacteria = bacteriaCount * 1;
        }

        bacteriaGrid[x][y] += newBacteria;
        foodGrid[x][y] = foodCount;
    }

    private void moverBacteria(int x, int y) {
        int direction = random.nextInt(8);
        int newX = x, newY = y;

        switch (direction) {
            case 0: newX = x - 1; newY = y - 1; break; // Top-left
            case 1: newX = x - 1; break; // Top
            case 2: newX = x - 1; newY = y + 1; break; // Top-right
            case 3: newY = y + 1; break; // Right
            case 4: newX = x + 1; newY = y + 1; break; // Bottom-right
            case 5: newX = x + 1; break; // Bottom
            case 6: newX = x + 1; newY = y - 1; break; // Bottom-left
            case 7: newY = y - 1; break; // Left
        }

        if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE) {
            bacteriaGrid[newX][newY]++;
            bacteriaGrid[x][y]--;
        }
    }

    public int[][] getBacteriaGrid() {
        return bacteriaGrid;
    }

    public int[][] getFoodGrid() {
        return foodGrid;
    }
}
