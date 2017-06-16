package modularmachines.api.modules.pages;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;

public class ModulePage<P extends Module> implements IPage {

	@SideOnly(Side.CLIENT)
	protected GuiContainer gui;
	@SideOnly(Side.CLIENT)
	protected Container container;
	protected int index = -1;
	protected final P parent;
	
	public ModulePage(P parent) {
		this.parent = parent;
	}
	
	public void setIndex(int index) {
		if(index <= 0){
			this.index = index;
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public P getParent(){
		return parent;
	}
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
    	return compound;
    }
    
    public void readFromNBT(NBTTagCompound compound){
    	
    }

	public int getPlayerInvPosition() {
		return 83;
	}
	
	/* GUI */
	
	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public GuiContainer getGui(){
		return gui;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setGui(GuiContainer gui){
		this.gui = gui;
		if(gui != null) {
			this.container = gui.inventorySlots;
		}else{
			this.container = null;
		}
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

	@Override
	@SideOnly(Side.CLIENT)
	public void updateGui(){
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initGui(){
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton){
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY){
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawBackground(int mouseX, int mouseY){
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawTooltips(int mouseX, int mouseY){
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawFrontBackground(int mouseX, int mouseY){
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(){
		
	}
	
	@Override
	public String getPageTitle(){
		return parent.getData().getDisplayName();
	}
	
	/* CONTAINER */

	@Override
	public void detectAndSendChanges(){
		
	}
	
	@Override
	public Container getContainer() {
		return container;
	}

	public void createSlots(List<Slot> slots) {
		
	}
	
}
