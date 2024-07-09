package engine.world;

import engine.entities.Block;
import engine.gfx.BatchRenderer;
import engine.handler.Handler;
import engine.math.Transform;
import engine.world.worldTipes.ChunkManager;
import org.joml.Vector3f;

public class Chunk {
    private Handler handler;
    private final Transform transform;
    public static final int SIZE = 16;

    private final String id;

    private ChunkManager chunkManager;

    private Block[][][] blocks;

    private float shadowStrength = 0.3f;

    private BatchRenderer batchRenderer, liquidBatchRenderer;

    public Chunk(String id, Transform transform, Handler handler, ChunkManager chunkManager) {
        this.transform = transform;
        this.handler = handler;
        this.chunkManager = chunkManager;
        batchRenderer = new BatchRenderer(handler);
        liquidBatchRenderer = new BatchRenderer(handler);
        blocks = new Block[SIZE][SIZE][SIZE];
        this.id = id;
        genDefaultChunk();
    }

    private void genDefaultChunk() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++)
                    blocks[i][j][k] = Block.getBlock(i, j, k, handler, this, "0");
    }

    public void updateBatchRenderer() {
        batchRenderer.clear();
        liquidBatchRenderer.clear();
        int f = 0;
        int lF = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    if (!blocks[i][j][k].id.equals("0") && blocks[i][j][k] != null) {
                        //blocks near
                        boolean up, down, left, right, ULF, URF, ULB, URB, DLF, DRF, DLB, DRB;
                        //face corner 1-4
                        ULF = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j + 1, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j + 1, transform.position.z + k + 1) != null;
                        URF = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j + 1, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j + 1, transform.position.z + k + 1) != null;
                        ULB = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j + 1, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j + 1, transform.position.z + k - 1) != null;
                        URB = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j + 1, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j + 1, transform.position.z + k - 1) != null;
                        DLF = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j - 1, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j - 1, transform.position.z + k + 1) != null;
                        DRF = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j - 1, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j - 1, transform.position.z + k + 1) != null;
                        DLB = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j - 1, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j - 1, transform.position.z + k - 1) != null;
                        DRB = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j - 1, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j - 1, transform.position.z + k - 1) != null;
                        float c1 = 0, c2 = 0, c3 = 0, c4 = 0;
                        //Left----------------------------------------------------------------------------
                        up = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j + 1, transform.position.z + k).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j + 1, transform.position.z + k) != null;
                        down = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j - 1, transform.position.z + k).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j - 1, transform.position.z + k) != null;
                        right = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j, transform.position.z + k + 1) != null;
                        left = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j, transform.position.z + k - 1) != null;
                        c1 = 0;
                        c2 = 0;
                        c3 = 0;
                        c4 = 0;
                        if (up) {
                            c1 += shadowStrength;
                            c4 += shadowStrength;
                        }
                        if (down) {
                            c3 += shadowStrength;
                            c2 += shadowStrength;
                        }
                        if (left) {
                            c2 += shadowStrength;
                            c1 += shadowStrength;
                        }
                        if (right) {
                            c3 += shadowStrength;
                            c4 += shadowStrength;
                        }

                        if (c1 == 0 && ULB) c1 += shadowStrength;
                        if (c2 == 0 && DLB) c2 += shadowStrength;
                        if (c3 == 0 && DLF) c3 += shadowStrength;
                        if (c4 == 0 && ULF) c4 += shadowStrength;

                        if (i - 1 < 0) {
                            if (chunkManager.getChunk(transform.position.x - SIZE, transform.position.y, transform.position.z) == null) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getLeftFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    liquidBatchRenderer.addVboS(blocks[i][j][k].getLeftFace(c1, c2, c3, c4));
                                    liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            } else if (verifyBlock(blocks[i][j][k], chunkManager.getChunk(transform.position.x - SIZE, transform.position.y, transform.position.z)
                                    .getBlock(SIZE - 1, j, k))) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getLeftFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    liquidBatchRenderer.addVboS(blocks[i][j][k].getLeftFace(c1, c2, c3, c4));
                                    liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            }
                        } else if (verifyBlock(blocks[i][j][k], blocks[i - 1][j][k])) {
                            if (!blocks[i][j][k].isLiquid()) {
                                batchRenderer.addVboS(blocks[i][j][k].getLeftFace(c1, c2, c3, c4));
                                batchRenderer.addEboS(Block.getEboS(f));
                                f++;
                            } else {
                                liquidBatchRenderer.addVboS(blocks[i][j][k].getLeftFace(c1, c2, c3, c4));
                                liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                lF++;
                            }
                        }
                        //Right---------------------------------------------------------------------------
                        up = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j + 1, transform.position.z + k).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j + 1, transform.position.z + k) != null;
                        down = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j - 1, transform.position.z + k).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j - 1, transform.position.z + k) != null;
                        right = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j, transform.position.z + k - 1) != null;
                        left = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j, transform.position.z + k + 1) != null;
                        c1 = 0;
                        c2 = 0;
                        c3 = 0;
                        c4 = 0;
                        if (up) {
                            c1 += shadowStrength;
                            c4 += shadowStrength;
                        }
                        if (down) {
                            c3 += shadowStrength;
                            c2 += shadowStrength;
                        }
                        if (left) {
                            c2 += shadowStrength;
                            c1 += shadowStrength;
                        }
                        if (right) {
                            c3 += shadowStrength;
                            c4 += shadowStrength;
                        }

                        if (c1 == 0 && URF) c1 += shadowStrength;
                        if (c2 == 0 && DRF) c2 += shadowStrength;
                        if (c3 == 0 && DRB) c3 += shadowStrength;
                        if (c4 == 0 && URB) c4 += shadowStrength;

                        if (i + 1 >= SIZE) {
                            if (chunkManager.getChunk(transform.position.x + SIZE, transform.position.y, transform.position.z) == null) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getRightFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    liquidBatchRenderer.addVboS(blocks[i][j][k].getRightFace(c1, c2, c3, c4));
                                    liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            } else if (verifyBlock(blocks[i][j][k], chunkManager.getChunk(transform.position.x + SIZE, transform.position.y, transform.position.z)
                                    .getBlock(0, j, k))) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getRightFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    liquidBatchRenderer.addVboS(blocks[i][j][k].getRightFace(c1, c2, c3, c4));
                                    liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            }
                        } else if (verifyBlock(blocks[i][j][k], blocks[i + 1][j][k])) {
                            if (!blocks[i][j][k].isLiquid()) {
                                batchRenderer.addVboS(blocks[i][j][k].getRightFace(c1, c2, c3, c4));
                                batchRenderer.addEboS(Block.getEboS(f));
                                f++;
                            } else {
                                liquidBatchRenderer.addVboS(blocks[i][j][k].getRightFace(c1, c2, c3, c4));
                                liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                lF++;
                            }
                        }
                        //Bottom--------------------------------------------------------------------------
                        up = chunkManager.getBlock(transform.position.x + i, transform.position.y + j - 1, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i, transform.position.y + j - 1, transform.position.z + k - 1) != null;
                        down = chunkManager.getBlock(transform.position.x + i, transform.position.y + j - 1, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i, transform.position.y + j - 1, transform.position.z + k + 1) != null;
                        left = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j - 1, transform.position.z + k).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j - 1, transform.position.z + k) != null;
                        right = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j - 1, transform.position.z + k).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j - 1, transform.position.z + k) != null;
                        c1 = 0;
                        c2 = 0;
                        c3 = 0;
                        c4 = 0;
                        if (up) {
                            c3 += shadowStrength;
                            c2 += shadowStrength;
                        }
                        if (down) {
                            c1 += shadowStrength;
                            c4 += shadowStrength;
                        }
                        if (left) {
                            c2 += shadowStrength;
                            c1 += shadowStrength;
                        }
                        if (right) {
                            c4 += shadowStrength;
                            c3 += shadowStrength;
                        }

                        if (c1 == 0 && DLF) c1 += shadowStrength;
                        if (c2 == 0 && DLB) c2 += shadowStrength;
                        if (c3 == 0 && DRB) c3 += shadowStrength;
                        if (c4 == 0 && DRF) c4 += shadowStrength;

                        if (j - 1 < 0) {
                            if (chunkManager.getChunk(transform.position.x, transform.position.y - SIZE, transform.position.z) == null) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getBottomFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    liquidBatchRenderer.addVboS(blocks[i][j][k].getBottomFace(c1, c2, c3, c4));
                                    liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            } else if (verifyBlock(blocks[i][j][k], chunkManager.getChunk(transform.position.x, transform.position.y - SIZE, transform.position.z)
                                    .getBlock(i, SIZE - 1, k))) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getBottomFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    liquidBatchRenderer.addVboS(blocks[i][j][k].getBottomFace(c1, c2, c3, c4));
                                    liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            }
                        } else if (verifyBlock(blocks[i][j][k], blocks[i][j - 1][k])) {
                            if (!blocks[i][j][k].isLiquid()) {
                                batchRenderer.addVboS(blocks[i][j][k].getBottomFace(c1, c2, c3, c4));
                                batchRenderer.addEboS(Block.getEboS(f));
                                f++;
                            } else {
                                liquidBatchRenderer.addVboS(blocks[i][j][k].getBottomFace(c1, c2, c3, c4));
                                liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                lF++;
                            }
                        }
                        //Top-----------------------------------------------------------------------------
                        up = chunkManager.getBlock(transform.position.x + i, transform.position.y + j + 1, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i, transform.position.y + j + 1, transform.position.z + k + 1) != null;
                        down = chunkManager.getBlock(transform.position.x + i, transform.position.y + j + 1, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i, transform.position.y + j + 1, transform.position.z + k - 1) != null;
                        left = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j + 1, transform.position.z + k).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j + 1, transform.position.z + k) != null;
                        right = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j + 1, transform.position.z + k).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j + 1, transform.position.z + k) != null;
                        c1 = 0;
                        c2 = 0;
                        c3 = 0;
                        c4 = 0;
                        if (up) {
                            c2 += shadowStrength;
                            c3 += shadowStrength;
                        }
                        if (down) {
                            c1 += shadowStrength;
                            c4 += shadowStrength;
                        }
                        if (left) {
                            c1 += shadowStrength;
                            c2 += shadowStrength;
                        }
                        if (right) {
                            c3 += shadowStrength;
                            c4 += shadowStrength;
                        }

                        if (c1 == 0 && ULB) c1 += shadowStrength;
                        if (c2 == 0 && ULF) c2 += shadowStrength;
                        if (c3 == 0 && URF) c3 += shadowStrength;
                        if (c4 == 0 && URB) c4 += shadowStrength;

                        if (j + 1 >= SIZE) {
                            if (chunkManager.getChunk(transform.position.x, transform.position.y + SIZE, transform.position.z) == null) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getTopFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                } else {
                                    liquidBatchRenderer.addVboS(blocks[i][j][k].getTopFace(c1, c2, c3, c4));
                                    liquidBatchRenderer.addEboS(Block.getEboS(f));
                                }
                                f++;
                            } else if (verifyBlock(blocks[i][j][k], chunkManager.getChunk(transform.position.x, transform.position.y + SIZE, transform.position.z)
                                    .getBlock(i, 0, k))) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getTopFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    liquidBatchRenderer.addVboS(blocks[i][j][k].getTopFace(c1, c2, c3, c4));
                                    liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            }
                        } else if (verifyBlock(blocks[i][j][k], blocks[i][j + 1][k])) {
                            if (!blocks[i][j][k].isLiquid()) {
                                batchRenderer.addVboS(blocks[i][j][k].getTopFace(c1, c2, c3, c4));
                                batchRenderer.addEboS(Block.getEboS(f));
                                f++;
                            } else {
                                liquidBatchRenderer.addVboS(blocks[i][j][k].getTopFace(c1, c2, c3, c4));
                                liquidBatchRenderer.addEboS(Block.getEboS(lF));
                                lF++;
                            }
                        }
                        //Back----------------------------------------------------------------------------
                        up = chunkManager.getBlock(transform.position.x + i, transform.position.y + j + 1, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i, transform.position.y + j + 1, transform.position.z + k - 1) != null;
                        down = chunkManager.getBlock(transform.position.x + i, transform.position.y + j - 1, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i, transform.position.y + j - 1, transform.position.z + k - 1) != null;
                        right = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j, transform.position.z + k - 1) != null;
                        left = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j, transform.position.z + k - 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j, transform.position.z + k - 1) != null;
                        c1 = 0;
                        c2 = 0;
                        c3 = 0;
                        c4 = 0;
                        if (up) {
                            c1 += shadowStrength;
                            c4 += shadowStrength;
                        }
                        if (down) {
                            c3 += shadowStrength;
                            c2 += shadowStrength;
                        }
                        if (left) {
                            c1 += shadowStrength;
                            c2 += shadowStrength;
                        }
                        if (right) {
                            c4 += shadowStrength;
                            c3 += shadowStrength;
                        }

                        if (c1 == 0 && URB) c1 += shadowStrength;
                        if (c2 == 0 && DRB) c2 += shadowStrength;
                        if (c3 == 0 && DLB) c3 += shadowStrength;
                        if (c4 == 0 && ULB) c4 += shadowStrength;

                        if (k - 1 < 0) {
                            if (chunkManager.getChunk(transform.position.x, transform.position.y, transform.position.z - SIZE) == null) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getBackFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    batchRenderer.addVboS(blocks[i][j][k].getBackFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            } else if (verifyBlock(blocks[i][j][k], chunkManager.getChunk(transform.position.x, transform.position.y, transform.position.z - SIZE)
                                    .getBlock(i, j, SIZE - 1))) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getBackFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    batchRenderer.addVboS(blocks[i][j][k].getBackFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            }
                        } else if (verifyBlock(blocks[i][j][k], blocks[i][j][k - 1])) {
                            if (!blocks[i][j][k].isLiquid()) {
                                batchRenderer.addVboS(blocks[i][j][k].getBackFace(c1, c2, c3, c4));
                                batchRenderer.addEboS(Block.getEboS(f));
                                f++;
                            } else {
                                batchRenderer.addVboS(blocks[i][j][k].getBackFace(c1, c2, c3, c4));
                                batchRenderer.addEboS(Block.getEboS(lF));
                                lF++;
                            }
                        }
                        //Front---------------------------------------------------------------------------
                        up = chunkManager.getBlock(transform.position.x + i, transform.position.y + j + 1, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i, transform.position.y + j + 1, transform.position.z + k + 1) != null;
                        down = chunkManager.getBlock(transform.position.x + i, transform.position.y + j - 1, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i, transform.position.y + j - 1, transform.position.z + k + 1) != null;
                        right = chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i - 1, transform.position.y + j, transform.position.z + k + 1) != null;
                        left = chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j, transform.position.z + k + 1).isSolid() &&
                                chunkManager.getBlock(transform.position.x + i + 1, transform.position.y + j, transform.position.z + k + 1) != null;
                        c1 = 0;
                        c2 = 0;
                        c3 = 0;
                        c4 = 0;
                        if (up) {
                            c3 += shadowStrength;
                            c4 += shadowStrength;
                        }
                        if (down) {
                            c2 += shadowStrength;
                            c1 += shadowStrength;
                        }
                        if (left) {
                            c3 += shadowStrength;
                            c2 += shadowStrength;
                        }
                        if (right) {
                            c1 += shadowStrength;
                            c4 += shadowStrength;
                        }

                        if (c1 == 0 && DLF) c1 += shadowStrength;
                        if (c2 == 0 && DRF) c2 += shadowStrength;
                        if (c3 == 0 && URF) c3 += shadowStrength;
                        if (c4 == 0 && ULF) c4 += shadowStrength;

                        if (k + 1 >= SIZE) {
                            if (chunkManager.getChunk(transform.position.x, transform.position.y, transform.position.z + SIZE) == null) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getFrontFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    batchRenderer.addVboS(blocks[i][j][k].getFrontFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            } else if (verifyBlock(blocks[i][j][k], chunkManager.getChunk(transform.position.x, transform.position.y, transform.position.z + SIZE)
                                    .getBlock(i, j, 0))) {
                                if (!blocks[i][j][k].isLiquid()) {
                                    batchRenderer.addVboS(blocks[i][j][k].getFrontFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(f));
                                    f++;
                                } else {
                                    batchRenderer.addVboS(blocks[i][j][k].getFrontFace(c1, c2, c3, c4));
                                    batchRenderer.addEboS(Block.getEboS(lF));
                                    lF++;
                                }
                            }
                        } else if (verifyBlock(blocks[i][j][k], blocks[i][j][k + 1])) {
                            if (!blocks[i][j][k].isLiquid()) {
                                batchRenderer.addVboS(blocks[i][j][k].getFrontFace(c1, c2, c3, c4));
                                batchRenderer.addEboS(Block.getEboS(f));
                                f++;
                            } else {
                                batchRenderer.addVboS(blocks[i][j][k].getFrontFace(c1, c2, c3, c4));
                                batchRenderer.addEboS(Block.getEboS(lF));
                                lF++;
                            }
                        }
                        //--------------------------------------------------------------------------------
                    }
                }
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++)
                    blocks[i][j][k].setChunk(this);
        batchRenderer.update();
        liquidBatchRenderer.update();
    }

    public void renderSolids() {
        handler.getDefaultShader().setTransform(transform.getTransformation());
        batchRenderer.render();
    }

    public void renderLiquids() {
        handler.getDefaultShader().setTransform(transform.getTransformation());
        liquidBatchRenderer.render();
    }

    public static String genId(float x, float y, float z) {
        return genId(new Vector3f(x, y, z));
    }

    public static String genId(Vector3f position) {
        return new String(Integer.toString((int) position.x) + "/"
                + Integer.toString((int) position.y) + "/"
                + Integer.toString((int) position.z));
    }

    public Transform getTransform() {
        return transform;
    }

    public String getId() {
        return id;
    }

    public Block getBlock(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= SIZE || y >= SIZE || z >= SIZE)
            return Block.blocks[0];
        return blocks[x][y][z];
    }

    public void setBlock(int x, int y, int z, String id) {
        blocks[x][y][z] = Block.getBlock(x, y, z, handler, this, id);
        blocks[x][y][z].setChunk(this);
    }

    public BatchRenderer getBatchRenderer() {
        return batchRenderer;
    }

    public Handler getHandler() {
        return handler;
    }

    public static Vector3f idToVector3f(String id) {
        ;
        String[] str = id.split("/");
        int a = 0, b = 0, c = 0;
        try {
            a = Integer.parseInt(str[0]);
            b = Integer.parseInt(str[1]);
            c = Integer.parseInt(str[2]);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return new Vector3f(a, b, c);
    }

    public void save() {
    }

    private boolean verifyBlock(Block b1, Block b2) {
        boolean a = true;

        if (b1.isSolid() && b2.isSolid()) a = false;
        if (!b1.isTransparent() && b2.isTransparent()) a = true;
        if (b1.isTransparent() && !b2.isTransparent()) a = false;
        if (b1.isLiquid() && b2.isLiquid()) a = false;

        return a;
    }
}
