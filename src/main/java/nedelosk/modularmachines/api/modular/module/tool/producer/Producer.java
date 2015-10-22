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
		try {
			readFromNBT(nbt, modular, stack);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		modifier = nbt.getString("Modifier");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		nbt.setString("Modifier", modifier);
	}
	
	@Override
	public String getName(ModuleStack stack) {
		return "producer" + modifier;
	}
	
	@Override
	public String getModifier(ModuleStack stack) {
		return modifier;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {}
	
	@Override
	public void updateClient(IModular modular, ModuleStack stack) {}
	
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return null;
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return null;
	}

}
