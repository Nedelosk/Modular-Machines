package nedelosk.nedeloskcore.api.multiblock;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;

public interface ITileMultiblock<M extends IMultiblock> extends ISidedInventory {
	
	boolean isWorking();
	
	void setWorking(boolean isWorking);
	
	MultiblockModifierValveTypeString getModifier();
	
	char getPatternMarker();

	String getMultiblockName();
	
    boolean isStructureTile(TileEntity tile);
    
    void setPatternPosition(byte x, byte y, byte z);
	
	void updateMultiblock();
	
	void testMultiblock();
	
	void onBlockChange(int depth);
	
	boolean testPatterns();
	
	boolean testPattern(MultiblockPattern pattern, M multiblock);
    
    ITileMultiblock getMasterBlock();
	
	void setMaster(ITileMultiblock tile);
	
	M getMultiblock();
	
	MultiblockPattern getPattern();
	
	boolean isMultiblock();
	
	boolean isMultiblockSolid();
	
	boolean isTested();
	
	void setIsMultiblock(boolean isMultiblock);
	
	int getXCoord();
	
	int getYCoord();
	
	int getZCoord();
	
	void onCreateMultiBlock();
	
}
