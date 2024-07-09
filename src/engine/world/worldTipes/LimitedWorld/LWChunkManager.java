package engine.world.worldTipes.LimitedWorld;

import engine.entities.Block;
import engine.handler.Handler;
import engine.math.Transform;
import engine.world.Chunk;
import engine.world.ChunkCluster;
import engine.world.World;
import engine.world.worldGen.Noise;
import engine.world.worldTipes.ChunkManager;
import org.joml.Vector3f;

public class LWChunkManager extends ChunkManager {
    private ChunkCluster[][][] chunkClusters;

    private int X, Y, Z;

    public LWChunkManager(int x, int y, int z, Handler handler, String worldPath, World world) {
        super(world);
        X = x;
        Y = y;
        Z = z;

        maxDX = X*ChunkCluster.SIZE;
        maxDY = Y*ChunkCluster.SIZE;
        maxDZ = Z*ChunkCluster.SIZE;
        minDX = 0;
        minDY = 0;
        minDZ = 0;

        chunkClusters = new ChunkCluster[X][Y][Z];

        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                for (int k = 0; k < Z; k++) {
                    chunkClusters[i][j][k] = new ChunkCluster(new Transform(
                            new Vector3f(i*ChunkCluster.SIZE,
                                    j*ChunkCluster.SIZE,
                                    k*ChunkCluster.SIZE),
                            new Vector3f(0,0,0),1),this, handler);
                }
            }
        }
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                for (int k = 0; k < Z; k++) {
                    chunkClusters[i][j][k].updateBatchRenderers();
                }
            }
        }
    }

    @Override
    public void render() {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                for (int k = 0; k < Z; k++) {
                    chunkClusters[i][j][k].renderSolids();
                }
            }
        }
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                for (int k = 0; k < Z; k++) {
                    chunkClusters[i][j][k].renderLiquids();
                }
            }
        }
    }

    @Override
    public void tick() {
    }

    @Override
    public void genWorld() {
        Noise noise = new Noise(0.2f,0.8f,
                X*ChunkCluster.SIZE,Z*ChunkCluster.SIZE);

        noise.multiply(Y*ChunkCluster.SIZE);

        noise.indirectSmooth();
        noise.invertedDirectSmooth();
        noise.smooth();
        noise.indirectSmooth();
        noise.indirectSmooth();

        Noise n1 = new Noise(0.7f, 1.2f, 128,128),
        n2 = new Noise(0.6f,1.6f,12,12),
        n3 = new Noise(0.5f,1.5f,4,4),
        n4 = new Noise(0.7f,1.9f,8,8),
        n5 = new Noise(0.3f,1.3f,6,6),
        n6 = new Noise(0.8f, 1.2f,64,64),
        n7 = new Noise(0.7f, 1.3f,128,128),
        n8 = new Noise(0.6f,1.4f, 32,32);
        System.out.println("Finished secondary noise generation!");
        n1.multiply(256);
        n2.multiply(256);
        n3.multiply(256);
        n4.multiply(256);
        n5.multiply(256);
        n7.multiply(256);
        n8.multiply(256);
        n1.smooth(2);
        n2.indirectSmooth(2);
        n2.invertedDirectSmooth(3);
        n3.indirectSmooth();
        n4.smooth();
        n5.indirectSmooth();
        n7.smooth();
        n8.indirectSmooth();
        n1.simplify(256);
        n2.simplify(256);
        n3.simplify(256);
        n4.simplify(256);
        n5.simplify(256);
        n7.simplify(256);
        n8.simplify(256);
        System.out.println("Finished secondary noise smoothing!");

        noise.multiply(n1);
        System.out.println("Finished mul 1!");
        noise.multiply(n2);
        System.out.println("Finished mul 2!");
        noise.multiply(n3);
        System.out.println("Finished mul 3!");
        noise.multiply(n4);
        System.out.println("Finished mul 4!");
        noise.multiply(n5);
        System.out.println("Finished mul 5!");
        noise.multiply(n6);
        System.out.println("Finished mul 6!");
        noise.multiply(n7);
        System.out.println("Finished mul 7!");
        noise.multiply(n8);
        System.out.println("Finished mul 8!");

        noise.indirectSmooth(4);
        noise.invertedDirectSmooth(6);
        System.out.println("Finished smoothing!");


        for (int i = 0; i < X*ChunkCluster.SIZE; i++)
            for (int j = 0; j < Z*ChunkCluster.SIZE; j++)
                for (int k = 58; k >= 0 ;k--)
                    setBlock(i,k,j,"9");
        System.out.println("Finished block placement 1!");

        for (int i = 0; i < X*ChunkCluster.SIZE; i++) {
            for (int j = 0; j < Z*ChunkCluster.SIZE; j++) {
                for (float k = noise.getNoiseVal(i,j)-1; k >= 0 ;k--) {
                    setBlock(i,k,j,"1");
                    if (getBlock(i,k+1,j).getId().equals("0"))
                        setBlock(i,k,j,"2");
                    if (!getBlock(i,k+3,j).getId().equals("0"))
                        setBlock(i,k,j,"3");
                    if (k == 0)
                        setBlock(i,k,j,"4");
                }
            }
        }
        System.out.println("Finished block placement 2!");

        for (int i = 0; i < X*ChunkCluster.SIZE; i++) {
            for (int j = 0; j < Z * ChunkCluster.SIZE; j++) {
                for (float k = noise.getNoiseVal(i, j) - 1; k >= 0; k--) {
                    if (getBlock(i, k + 1, j).getId().equals("9") ||
                            getBlock(i + 1, k, j).getId().equals("9") ||
                            getBlock(i - 1, k, j).getId().equals("9") ||
                            getBlock(i, k, j + 1).getId().equals("9") ||
                            getBlock(i, k, j - 1).getId().equals("9"))
                        setBlock(i, k, j, Block.getRandBlockIdFromList(new String[] {"6","3","4","1"}));
                }
            }
        }
        System.out.println("Finished block placement 3!");

        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                for (int k = 0; k < Z; k++) {
                    chunkClusters[i][j][k].updateBatchRenderers();
                }
            }
        }
        System.out.println("Finished updating batch renderer!");
    }

    @Override
    public Chunk getChunk(String id) {
        return getChunk((int) Chunk.idToVector3f(id).x,(int) Chunk.idToVector3f(id).y,(int) Chunk.idToVector3f(id).z);
    }

    @Override
    public Chunk getChunk(float x, float y, float z) {
            if(x/ChunkCluster.SIZE >= X || y/ChunkCluster.SIZE >= Y || z/ChunkCluster.SIZE >= Z
                    || x/ChunkCluster.SIZE < 0 || y/ChunkCluster.SIZE < 0 || z/ChunkCluster.SIZE < 0)
                return null;
        return chunkClusters[(int) x/ChunkCluster.SIZE]
                [(int) y/ChunkCluster.SIZE]
                [(int) z/ChunkCluster.SIZE].getChunk(x,y,z);
    }

    @Override
    public Block getBlock(float x, float y, float z) {
        if (y >= Y*ChunkCluster.SIZE)
            return Block.blocks[0];
        if (x >= X*ChunkCluster.SIZE)
            return Block.blocks[0];
        if (z >= Z*ChunkCluster.SIZE)
            return Block.blocks[0];
        if (y < 0)
            return Block.blocks[0];
        if (x < 0)
            return Block.blocks[0];
        if (z < 0)
            return Block.blocks[0];
        if(getChunk(x,y,z) == null) return Block.blocks[0];
        return getChunk(x,y,z)
                .getBlock((int)x%Chunk.SIZE,(int)y%Chunk.SIZE,(int)z%Chunk.SIZE);
    }

    @Override
    public void setBlock(float x, float y, float z, String id) {
        setBlock((int) x, (int) y, (int) z, id);
    }

    public void setBlock(int x, int y, int z, String id) {
        if (getChunk(x,y,z) != null)
            getChunk(x,y,z).setBlock(x % Chunk.SIZE, y % Chunk.SIZE, z % Chunk.SIZE, id);
    }
}
