package nedelosk.nedeloskcore.multiblocks;

import net.minecraft.world.World;

public class MultiblockStructureBase {

	protected int height;
	protected int length;
	protected int width;
	protected String structureID = "StructureBase";
	protected World world;
	
	public char[][][] pattern = new char[height][length][width];
	
	public MultiblockStructureBase(int height, int length, int width, String structureID, char[][][] pattern, World world) {
		this.height = height;
		this.length = length;
		this.width = width;
		this.structureID = structureID;
		this.pattern = pattern;
		this.world = world;
		
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
	}
	
	public String getStructureID() {
		return structureID;
	}
	
}
