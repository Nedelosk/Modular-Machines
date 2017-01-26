package modularmachines.api.modules.pages;

import java.util.List;

import modularmachines.api.modules.Module;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModulePage implements IPage {

	protected Container container;
	protected int index = -1;
	protected final Module parent;
	
	public ModulePage(Module parent) {
		this.parent = parent;
	}
	
	@Override
	public void setContainer(Container container) {
		this.container = container;
	}
	
	@Override
	public Container getContainer() {
		return container;
	}
	
	public void setIndex(int index) {
		if(index < 0){
			this.index = index;
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public Module getParent(){
		return parent;
	}
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
    	return compound;
    }
    
    public void readFromNBT(NBTTagCompound compound){
    	
    }

	public void createSlots(List<Slot> slots) {
		
	}

	public int getPlayerInvPosition() {
		return 83;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getXSize() {
		return 176;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getYSize() {
		return 166;
	}
	
}
