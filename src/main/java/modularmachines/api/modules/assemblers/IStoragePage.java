package modularmachines.api.modules.assemblers;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.items.IItemHandlerModifiable;

import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;

public interface IStoragePage {

    NBTTagCompound writeToNBT(NBTTagCompound compound);
    
    void readFromNBT(NBTTagCompound compound);
	
	@Nullable
	IStorage assemble(IAssembler assembler, IModuleLogic logic);
	
	void canAssemble(IAssembler assembler, List<AssemblerError> errors);
	
	IItemHandlerModifiable getItemHandler();
	
	IModuleSlots getSlots();
	
	IAssembler getAssembler();
	
	IStoragePosition getPosition();
	
	ItemStack getStorageStack();
	
	@Nullable
	IStoragePage getParent();
	
	int getComplexity();
	
	int getAllowedComplexity();
	
	boolean isEmpty();
	
	void init();
	
	boolean wasInitialized();

	void createContainerSlots(Container container, EntityPlayer player, IAssembler assembler, List<Slot> slots);

	/*@SideOnly(Side.CLIENT)
	void setGui(GuiContainer gui);
	
	@SideOnly(Side.CLIENT)
	void initGui();
	
	@SideOnly(Side.CLIENT)
	void drawTooltips(int mouseX, int mouseY);
	
	@SideOnly(Side.CLIENT)
	void handleMouseClicked(int mouseX, int mouseY, int mouseButton);
	
	@SideOnly(Side.CLIENT)
	void drawBackground(int mouseX, int mouseY);
	
	void setContainer(Container container);*/

	void onSlotChanged(EntityPlayer player, IAssembler assembler);

	/*void detectAndSendChanges();

	void updateGui();

	@SideOnly(Side.CLIENT)
	void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY);*/
	
	

}
