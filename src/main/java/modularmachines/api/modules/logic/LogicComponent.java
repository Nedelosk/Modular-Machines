package modularmachines.api.modules.logic;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.IModuleContainer;

public class LogicComponent {

	public static final String ENERGY = "energy";
	public static final String HEAT = "heat";
	public static final String MODEL = "model";
	public static final String UPDATE = "update";
	
	protected IModuleContainer provider;
	
	
	public LogicComponent() {
	}
	
	public void update(){
	}
	
	public void setProvider(IModuleContainer provider) {
		this.provider = provider;
	}
	
	public IModuleContainer getProvider() {
		return provider;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return compound;
	}
	
}
