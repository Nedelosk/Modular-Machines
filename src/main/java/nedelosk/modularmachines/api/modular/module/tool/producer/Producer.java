package nedelosk.modularmachines.api.modular.module.tool.producer;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Producer implements IProducer {

	public String modifier;
	
	public Producer(String modifier) {
		this.modifier = modifier;
	}
	
	public Producer(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		readFromNBT(nbt, modular, stack);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		
	}
	
	@Override
	public String getName(ModuleStack stack) {
		return "producer" + modifier;
	}
	

	@Override
	public void update(IModular modular, ModuleStack stack) {
		
	}
	
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return null;
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return null;
	}

}
