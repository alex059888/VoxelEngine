package engine.gfx.textures;

public enum TexSizes {
    X4(4),
    X8(8),
    X16(16),
    X32(32),
    X48(48),
    X64(64),
    X128(128),
    X256(256),
    X512(512),
    X1024(1024),
    X2048(2048),
    X4096(4096),
    X8192(8192);

    public int pixels;

    TexSizes(int pixels) {
        this.pixels = pixels;
    }

    public int pixels() {
        return pixels;
    }
}
