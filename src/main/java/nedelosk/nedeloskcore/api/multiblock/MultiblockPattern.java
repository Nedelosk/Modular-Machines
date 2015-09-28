package nedelosk.nedeloskcore.api.multiblock;

import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MultiblockPattern {

    public final char[][][] pattern;
    private final int offsetX;
    private final int offsetY;
    private final int offsetZ;
    public final int tier;

    public MultiblockPattern(char[][][] pattern) {
        this(pattern, 1, 1, 1);
    }
    
    public MultiblockPattern(char[][][] pattern, int offsetX, int offsetY, int offsetZ) {
        this.pattern = pattern;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.tier = 1;
    }

    public MultiblockPattern(char[][][] pattern, int offsetX, int offsetY, int offsetZ, int tier) {
        this.pattern = pattern;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.tier = tier;
    }

    public char getPatternMarker(int x, int y, int z) {
        return pattern[y][x][z];
    }

    public int getMasterOffsetX() {
        return offsetX;
    }

    public int getMasterOffsetY() {
        return offsetY;
    }

    public int getMasterOffsetZ() {
        return offsetZ;
    }

    public int getPatternHeight() {
        return pattern.length;
    }

    public int getPatternWidthX() {
        return pattern[0].length;
    }

    public int getPatternWidthZ() {
        return pattern[0][0].length;
    }

    public int getMasterRelativeX(int posX, int patternX) {
        return (offsetX - patternX) + posX;
    }

    public int getMasterRelativeY(int posY, int patternY) {
        return (offsetY - patternY) + posY;
    }

    public int getMasterRelativeZ(int posZ, int patternZ) {
        return (offsetZ - patternZ) + posZ;
    }

    public TileEntity placeStructure(World world, int xCoord, int yCoord, int zCoord, Block block, Map<Character, Integer> blockMapping) {
        if (block == null)
            return null;

        int xWidth = getPatternWidthX();
        int zWidth = getPatternWidthZ();
        int height = getPatternHeight();

        int xOffset = xCoord - getMasterOffsetX();
        int yOffset = yCoord - getMasterOffsetY();
        int zOffset = zCoord - getMasterOffsetZ();

        TileEntity master = null;

        for (byte px = 0; px < xWidth; px++) {
            for (byte py = 0; py < height; py++) {
                for (byte pz = 0; pz < zWidth; pz++) {

                    char marker = getPatternMarker(px, py, pz);

                    Integer metadata = blockMapping.get(marker);
                    if (metadata == null)
                        continue;

                    int x = px + xOffset;
                    int y = py + yOffset;
                    int z = pz + zOffset;

                    world.setBlock(x, y, z, block, metadata, 3);

                    if (px == getMasterOffsetX() && py == getMasterOffsetY() && pz == getMasterOffsetZ())
                        master = world.getTileEntity(x, y, z);
                }
            }
        }
        return master;
    }

}
