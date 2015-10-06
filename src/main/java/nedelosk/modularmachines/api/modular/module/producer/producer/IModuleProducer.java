package nedelosk.modularmachines.api.modular.module.producer.producer;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modular.module.producer.tool.IModuleTool;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.api.recipes.NeiStack;
import net.minecraft.item.ItemStack;

public interface IModuleProducer extends IModuleTool, IModuleInventory {

	int getBurnTime();
	
	int getBurnTimeTotal();
	
	ArrayList<NeiStack> addNEIStacks();
	
	IModule buildModule(ItemStack[] stacks);
	
	PartType[] getRequiredComponents();
	
	ModuleStack creatModule(ItemStack stack);
	
	int getColor();
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ItemStack stack);
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile);
	
}
