package engine.world.worldGen;

public class GradientNoise extends Noise {
    public GradientNoise(float minHeight, float maxHeight, int x, int y, float maxR) {
        super(x, y);

        float delta = maxHeight - minHeight;
        float step = (delta / (1 + x / 2) + delta / (1 + y / 2)) / 2;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i == 0 || j == 0 || i == x - 1 || j == y - 1)
                    noise[i][j] = minHeight;
                else if (i >= x / 2 - maxR || j >= y / 2 - maxR || i < x / 2 + maxR || j < y / 2 + maxR)
                    noise[i][j] = minHeight;
                else {
                    if (i < x / 2 - maxR && j < y / 2 - maxR)
                        noise[i][j] = Math.min(i,j)*step+minHeight;
                    else
                        noise[i][j] = Math.min(x-i, y-j)*step+minHeight;
                }
            }
        }
    }
}
