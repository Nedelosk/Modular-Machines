package nedelosk.modularmachines.api.modular.module.basic.basic;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Module implements IModule{
	
	public String modifier;
	
	public Module() {
	}
	
	public Module(String modifier) {
		this.modifier = modifier;
	}
	
	public Module(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Modifier"))
			this.modifier = nbt.getString("Modifier");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		readFromNBT(nbt);
	}

	@Override
	public void update(IModular modular) {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if(modifier != null)
			nbt.setString("Modifier", modifier);
	}
	
	@Override
	public String getModifier()
	{
		return modifier;
	}

	@Override
	public String getName() {
		return "module" + getModuleName() + ((modifier != null) ? modifier : "");
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
