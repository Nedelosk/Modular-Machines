package modularmachines.api.modules.assemblers;

import java.util.List;

import javax.annotation.Nullable;

import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IStoragePage {

    NBTTagCompound writeToNBT(NBTTagCompound compound);
    
    void readFromNBT(NBTTagCompound compound);
	
	@Nullable
	IStorage assemble(IAssembler assembler, IModuleLogic logic);
	
	void canAssemble(IAssembler assembler, List<AssemblerError> errors);
	
	IItemHandlerModifiable getItemHandler();
	
	IAssembler getAssembler();
	
	IStoragePosition getPosition();
	
	ItemStack getStorageStack();
	
	List<IStoragePage> getChilds();
	
	void addChild(IStoragePage child);
	
	@Nullable
	IStoragePage getParent();
	
	int getComplexity();
	
	int getAllowedComplexity();
	
	boolean isEmpty();

	void createSlots(Container container, IAssembler assembler, List<Slot> slots);

	@SideOnly(Side.CLIENT)
	void setGui(GuiContainer gui);
	
	@SideOnly(Side.CLIENT)
	void initGui();
	
	@SideOnly(Side.CLIENT)
	void drawTooltips(int mouseX, int mouseY);
	
	@SideOnly(Side.CLIENT)
	void handleMouseClicked(int mouseX, int mouseY, int mouseButton);
	
	@SideOnly(Side.CLIENT)
	void drawBackground(int mouseX, int mouseY);
	
	void setContainer(Container container);

	void onSlotChanged(Container container, IAssembler assembler);

	void detectAndSendChanges();
	
	boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot);

	void updateGui();

	@SideOnly(Side.CLIENT)
	void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY);

}
