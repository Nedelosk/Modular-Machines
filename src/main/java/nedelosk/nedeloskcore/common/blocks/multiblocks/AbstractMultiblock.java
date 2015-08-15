package nedelosk.nedeloskcore.common.blocks.multiblocks;

import java.util.ArrayList;

import nedelosk.nedeloskcore.api.INBTTagable;
import nedelosk.nedeloskcore.api.NCoreApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public abstract class AbstractMultiblock implements INBTTagable {

	public AbstractMultiblock() {
		if(createPatterns() != null)
			NCoreApi.addMultiblockPattern(getMultiblockName(), createPatterns());
		else
			NCoreApi.addMultiblockPattern(getMultiblockName(), createPattern());
		NCoreApi.registerMuliblock(getMultiblockName(), this);
	}
	
	public abstract String getMultiblockName();
	
	public abstract MultiblockPattern createPattern();
	
	public abstract ArrayList<MultiblockPattern> createPatterns();
	
	public abstract void updateMultiblock();
	
	public abstract boolean testBlock();
	
	public abstract boolean isPatternBlockValid(int x, int y, int z, char pattern, TileMultiblockBase tile);

	public abstract void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side);
	
	public abstract Container getContainer(TileMultiblockBase base, InventoryPlayer inventory);

	public abstract Object getGUIContainer(TileMultiblockBase base, InventoryPlayer inventory);

	public abstract void updateClient(TileMultiblockBase base);
	
	public abstract void updateServer(TileMultiblockBase base);
	
}
