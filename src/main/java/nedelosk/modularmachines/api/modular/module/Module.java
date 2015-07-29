package nedelosk.modularmachines.api.modular.module;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

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
	
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		readFromNBT(nbt);
	}
	
	public int getGuiTop(IModular modular)
	{
		return 166;
	}
	
	public ResourceLocation getCustomGui(IModular modular)
	{
		return null;
	}

	@Override
	public void update(IModular modular) {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if(modifier != null)
			nbt.setString("Modifier", modifier);
	}
	
	public void addModule(ModuleStack aModule, ArrayList<ModuleStack> list)
	{
		for(ModuleStack stack : list)
		{
			if(stack.getModule().getClass() == aModule.getModule().getClass())
			{
				if(aModule.getTier() > stack.getTier())
				{
					list.remove(stack);
					list.add(new ModuleStack(aModule.getModule(), aModule.getTier()));
					return;
				}
				else
					return;
			}
		}
		list.add(aModule);
	}

	@Override
	public String getName() {
		return "module" + getModuleName() + ((modifier != null) ? modifier : "");
	}

}
