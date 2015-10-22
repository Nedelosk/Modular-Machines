package nedelosk.modularmachines.api.modular.module.tool.producer;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IProducer {
	
	void updateServer(IModular modular, ModuleStack stack);
	
	void updateClient(IModular modular, ModuleStack stack);
	
	void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception;

	void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception;
	
	String getName(ModuleStack stack);
	
	String getModifier(ModuleStack stack);
	
	IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack);
	
	IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile);
	
}
