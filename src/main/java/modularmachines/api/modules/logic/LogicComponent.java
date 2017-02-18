package modularmachines.api.modules.logic;

import net.minecraft.nbt.NBTTagCompound;

public class LogicComponent {

	public static final String ENERGY = "energy";
	public static final String HEAT = "heat";
	public static final String MODEL = "model";
	public static final String UPDATE = "update";
	
	protected IModuleLogic logic;
	
	public LogicComponent() {
	}
	
	public void update(){
		
	}
	
	public void setLogic(IModuleLogic logic) {
		this.logic = logic;
	}
	
	public IModuleLogic getLogic() {
		return logic;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return compound;
	}
	
}
