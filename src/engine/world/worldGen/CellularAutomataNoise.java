package engine.world.worldGen;

import java.util.Random;

public class CellularAutomataNoise extends Noise {
    private int density;

    public CellularAutomataNoise(int x, int y, int density) {
        super(x, y);
        this.density = density;

        genCNoise();
    }

    public void genCNoise() {
        Random random = new Random();
        for (int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                if(random.nextInt(1, 101) > density) {
                    noise[i][j] = 1;
                } else {
                    noise[i][j] = 0;
                }
            }
        }
    }

    public void applyCA(int n) {
        for(int o = 0; o < n; o++) {
            float tempNoise[][] = new float[x][y];
            for (int i = 0; i < x; i++) {
                for(int j = 0; j < y; j++) {
                    int neighborWalls = 0;
                    for(int a = i-1; a <= i+1; a++) {
                        for (int b = j-1; b <= j+1; b++) {
                            if(isInMap(a, b)) {
                                if(a != i || b != j) {
                                    if(noise[a][b] == 1) {
                                        neighborWalls++;
                                    }
                                }
                            } else {
                                neighborWalls++;
                            }
                        }
                    }
                    if(neighborWalls > 4) {
                        tempNoise[i][j] = 1;
                    } else  {
                        tempNoise[i][j] = 0;
                    }
                }
            }
            noise = tempNoise;
        }
    }

    public boolean isInMap(int x, int y) {
        return (x >= 0 && y >= 0 && x < this.x && y < this.y);
    }
}
