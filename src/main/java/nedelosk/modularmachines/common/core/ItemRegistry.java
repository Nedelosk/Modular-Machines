package nedelosk.modularmachines.common.core;

import nedelosk.forestday.common.blocks.BlockGravel;
import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.forestday.common.machines.base.block.BlockMachines;
import nedelosk.forestday.common.machines.base.block.BlockMachinesWood;
import nedelosk.forestday.common.machines.base.block.item.ItemBlockMachines;
import nedelosk.forestday.common.machines.base.fluid.heater.TileFluidHeater;
import nedelosk.forestday.common.machines.base.furnace.base.TileFurnace;
import nedelosk.forestday.common.machines.base.furnace.coke.TileCokeFurnace;
import nedelosk.forestday.common.machines.base.heater.generator.TileHeatGenerator;
import nedelosk.forestday.common.machines.base.saw.TileSaw;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.forestday.common.machines.base.wood.kiln.TileKiln;
import nedelosk.forestday.common.machines.base.wood.workbench.TileWorkbench;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.BlockCharcoalKiln;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalAsh;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalKiln;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.IModuleSpecial;
import nedelosk.modularmachines.common.blocks.ModularAssemblerBlock;
import nedelosk.modularmachines.common.blocks.ModularMachineBlock;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularAssembler;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularMachine;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.items.ModuleItems;
import nedelosk.modularmachines.common.modular.ModularMachine;
import nedelosk.nedeloskcore.common.blocks.BlockOre;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemRegistry {
	
	public static void preInit()
	{
		
		//Blocks
		MMItems.Module_Items.registerItem(new ModuleItems());
    	for(String s : ModuleItems.names)
    	{
    		for(int i = 0;i < 3;i++)
    		{
        		ItemStack stack = new ItemStack(MMItems.Module_Items.item());
        		stack.setTagCompound(new NBTTagCompound());
        		if(ModuleItems.modules.get(s) instanceof IModuleSpecial)
        		{
        			NBTTagCompound nbt = new NBTTagCompound();
        			((IModuleSpecial)ModuleItems.modules.get(s)).writeToItemNBT(nbt, i);
        			ModuleItems.modules.get(s).readFromNBT(nbt);
        		}
        		stack.getTagCompound().setString("Name", s);
        		stack.getTagCompound().setInteger("Tier", i);
    			ModularMachinesApi.addModuleItem(stack, ModuleItems.modules.get(s), i + 1, true);
    		}
    	}
		
	}
	
}
