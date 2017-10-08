package modularmachines.api.modules.pages;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;

public interface IModuleComponent<P extends Module> {
	
	@SideOnly(Side.CLIENT)
	IPage createPage(GuiContainer gui);

	void detectAndSendChanges();

	String getTitle();
	
	NBTTagCompound writeToNBT(NBTTagCompound compound);
	
	void readFromNBT(NBTTagCompound compound);
	
	int getPlayerInvPosition();
	
	void createSlots(List<Slot> slots);
	
	void setIndex(int index);
	
	int getIndex();
	
	P getParent();
	
}
