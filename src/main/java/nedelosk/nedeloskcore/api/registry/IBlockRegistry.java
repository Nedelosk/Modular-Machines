/**
 * (C) 2015 Nedelosk
 */
package nedelosk.nedeloskcore.api.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

public interface IBlockRegistry {

	public Block registerBlock(Block block, String name, String modName);
	
	public Block registerBlock(Block block, Class<? extends ItemBlock> itemblock, String name, String modName);
	
	public void registerTile(Class<? extends TileEntity> tile, String name, String modName);
	
	public String setUnlocalizedBlockName(String name, String modName);
	
}
