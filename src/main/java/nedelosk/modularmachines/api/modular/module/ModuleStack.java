package nedelosk.modularmachines.api.modular.module;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;


public final class ModuleStack
{
    private IModule module;
    private int tier;
    
    private ModuleStack()
    {
    }
    
    public ModuleStack(IModule module, int tier)
    {
    	this.module = module;
    	this.tier = tier;
    }

    public IModule getModule()
    {
        return this.module;
    }
    
    public int getTier() {
		return tier;
	}

	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		module = ModularMachinesApi.buildModule(nbt.getString("ModuleName"), nbt.getCompoundTag("Module"), modular);
		tier = nbt.getInteger("Tier");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("ModuleName", module.getName());
		nbt.setInteger("Tier", tier);
		NBTTagCompound nbtTag = new NBTTagCompound();
		module.writeToNBT(nbtTag);
		nbt.setTag("Module", nbtTag);
	}
	
	public static ModuleStack loadStackFromNBT(NBTTagCompound nbt, IModular modular)
	{
		ModuleStack stack = new ModuleStack();
		stack.readFromNBT(nbt, modular);
		return stack;
	}
}