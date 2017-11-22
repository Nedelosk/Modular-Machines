package modularmachines.api.modules.pages;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.components.IComponentProvider;
import modularmachines.api.components.IComponentTag;
import modularmachines.api.modules.Module;

public class PageComponent<P extends Module> implements IPageComponent<P> {
	protected final P parent;
	protected int index = -1;
	
	public PageComponent(P parent) {
		this.parent = parent;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return compound;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
	}
	
	public int getPlayerInvPosition() {
		return 83;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IPage createPage(GuiContainer gui) {
		return new Page(this, gui);
	}
	
	@Override
	public String getTitle() {
		return parent.getData().getDisplayName();
	}
	
	@Override
	public void detectAndSendChanges() {
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
	}
	
	@Override
	public void setIndex(int index) {
		if (index <= 0) {
			this.index = index;
		}
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public P getParent() {
		return parent;
	}
	
	@Override
	public Class<?>[] getComponentInterfaces() {
		return new Class[0];
	}
	
	@Override
	public void setProvider(IComponentProvider provider) {
	
	}
	
	@Override
	public IComponentProvider getProvider() {
		return null;
	}
	
	@Override
	public IComponentTag[] getTags() {
		return new IComponentTag[0];
	}
}
