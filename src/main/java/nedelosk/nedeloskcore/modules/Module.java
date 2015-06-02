package nedelosk.nedeloskcore.modules;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.nedeloskcore.api.registry.IBlockRegistry;
import nedelosk.nedeloskcore.api.registry.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract class Module {
	
	public void preInit(){}
	public void init(){}
	public void postInit(){}
	
	public void registerRecipes(){}
	public abstract boolean getRequiredBoolean();
	
	public abstract String getModuleName();
	
	public abstract String getModuleVersion();
	
}
