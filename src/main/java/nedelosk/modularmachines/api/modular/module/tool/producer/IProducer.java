package nedelosk.modularmachines.api.modular.module.tool.producer;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IProducer {
	
	void update(IModular modular, ModuleStack stack);
	
	void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack);

	void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack);
	
	String getName(ModuleStack stack);
	
	IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack);
	
	IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile);
	
}
