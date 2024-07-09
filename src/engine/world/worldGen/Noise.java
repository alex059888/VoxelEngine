package engine.world.worldGen;

import java.util.Random;

public class Noise {
    protected float minHeight = 0, maxHeight = 2, noise[][];
    protected int x,y;

    public Noise (float minHeight, float maxHeight, int x, int y) {
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.x = x;
        this.y = y;

        noise = new float[x][y];

        genNoise();
    }

    public Noise (int x, int y) {
        this.x = x;
        this.y = y;
        noise = new float[x][y];
    }

    public void genNoise() {
        for (int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                noise[i][j] = new Random().nextFloat(minHeight, maxHeight);
            }
        }
    }

    public void smooth(int n) {
        for(int i = 0; i < n; i++) {
            smooth();
        }
    }

    public void invertedDirectSmooth() {
        for (int i = x-1; i >= 0; i--) {
            for(int j = y-1; j >= 0; j--) {
                float newVal = noise[i][j];
                if(i > 0 && j > 0)
                    newVal = (newVal + noise[i-1][j-1])/2;
                if(i > 0)
                    newVal = (newVal + noise[i-1][j])/2;
                if(j > 0)
                    newVal = (newVal + noise[i][j-1])/2;
                if(i < x-1 && j > 0)
                    newVal = (newVal + noise[i+1][j-1])/2;
                if(i < x-1)
                    newVal = (newVal + noise[i+1][j])/2;
                if(j < y-1)
                    newVal = (newVal + noise[i][j+1])/2;
                if(i < x-1 && j < y-1)
                    newVal = (newVal + noise[i+1][j+1])/2;
                if(i > 0 && j < y-1)
                    newVal = (newVal + noise[i-1][j+1])/2;
                noise[i][j] = newVal;
            }
        }
    }

    public void invertedDirectSmooth(int n) {
        for(int i = 0; i < n; i++) {
            invertedDirectSmooth();
        }
    }

    public void indirectSmooth(int n) {
        for (int i = 0; i < n; i++) {
            indirectSmooth();
        }
    }

    public void indirectSmooth() {
        float[][] a = new float[x][y];
        for (int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                int newVal = 0, m = 0;
                newVal+=noise[i][j];
                m++;
                if(i > 0 && j > 0) {
                    newVal += noise[i - 1][j - 1];
                    m++;
                }
                if(i > 0) {
                    newVal += noise[i - 1][j];
                    m++;
                }
                if(j > 0) {
                    newVal += noise[i][j - 1];
                    m++;
                }
                if(i < x-1 && j > 0) {
                    newVal += noise[i + 1][j - 1];
                    m++;
                }
                if(i < x-1) {
                    newVal += noise[i + 1][j];
                    m++;
                }
                if(j < y-1) {
                    newVal += noise[i][j + 1];
                    m++;
                }
                if(i < x-1 && j < y-1) {
                    newVal += noise[i + 1][j + 1];
                    m++;
                }
                if(i > 0 && j < y-1) {
                    newVal += noise[i - 1][j + 1];
                    m++;
                }
                a[i][j] = newVal / m;
            }
        }
        noise = a;
    }

    public void smooth() {
        float newNoise[][] = new float[x][y];
        for (int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                float newVal = noise[i][j];
                if(i > 0 && j > 0)
                    newVal = (newVal + noise[i-1][j-1])/2;
                if(i > 0)
                    newVal = (newVal + noise[i-1][j])/2;
                if(j > 0)
                    newVal = (newVal + noise[i][j-1])/2;
                if(i < x-1 && j > 0)
                    newVal = (newVal + noise[i+1][j-1])/2;
                if(i < x-1)
                    newVal = (newVal + noise[i+1][j])/2;
                if(j < y-1)
                    newVal = (newVal + noise[i][j+1])/2;
                if(i < x-1 && j < y-1)
                    newVal = (newVal + noise[i+1][j+1])/2;
                if(i > 0 && j < y-1)
                    newVal = (newVal + noise[i-1][j+1])/2;
                newNoise[i][j] = newVal;
            }
        }
        noise = newNoise;
    }

    public float getMinHeight() {
        return minHeight;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float[][] getNoise() {
        return noise;
    }

    public float getNoiseVal(int x, int y) {
        return noise[x][y];
    }

    public void multiply(float n) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                noise[i][j]*=n;
            }
        }
    }

    public void simplify(float n) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                noise[i][j]=noise[i][j]/n;
            }
        }
    }

    public void multiply(Noise noise) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.noise[i][j]*=noise.noise[calcMultiplierPos(x, noise.x, i)][calcMultiplierPos(y, noise.y, j)];
            }
        }
    }

    private int calcMultiplierPos(int n1, int n2, int cp) {
        if (n2 < n1)
            return (int) ((float) n2 / (float) n1 * cp);
        return cp;
    }
}
