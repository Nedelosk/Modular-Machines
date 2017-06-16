package modularmachines.api.modules.assemblers;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.items.IItemHandlerModifiable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;

public abstract class StoragePage implements IStoragePage {
	
	protected final IAssembler assembler;
	protected final IStoragePosition position;
	protected final ItemStackHandlerPage itemHandler;
	protected final List<IStoragePage> childs;
	protected boolean wasInitialized;
	
	public StoragePage(IAssembler assembler, IStoragePosition position, int size) {
		this.assembler = assembler;
		this.position = position;
		this.childs = new ArrayList<>();
		this.itemHandler = new ItemStackHandlerPage(size, this);
		this.wasInitialized = false;
	}
	
	public StoragePage(IAssembler assembler, IStoragePosition position) {
		this.assembler = assembler;
		this.position = position;
		this.childs= new ArrayList<>();
		this.itemHandler = new ItemStackHandlerPage(1, this);
		this.wasInitialized = false;
	}
	
	@Override
	public void init() {
		wasInitialized = true;
	}
	
	@Override
	public boolean wasInitialized() {
		return wasInitialized;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Items", itemHandler.serializeNBT());
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {	
		itemHandler.deserializeNBT(compound.getCompoundTag("Items"));
	}

	@Override
	public void canAssemble(IAssembler assembler, List<AssemblerError> errors) {
	}

	@Override
	public void createSlots(Container container, IAssembler assembler, List<Slot> slots) {
	}

	@Override
	public void onSlotChanged(Container container, IAssembler assembler) {
	}
	
	@Override
	public IStorage assemble(IAssembler assembler, IModuleLogic logic) {
		ItemStack storageStack = getStorageStack();
		if (!storageStack.isEmpty()) {
			IModuleContainer container = ModuleHelper.getContainerFromItem(storageStack);
			ModuleData data = container.getData();
			if (data != null) {
				return data.createStorage(logic, position, this);
			}
		}
		return null;
	}
	
	@Override
	public IItemHandlerModifiable getItemHandler() {
		return itemHandler;
	}
	
	@Override
	public IAssembler getAssembler() {
		return assembler;
	}
	
	@Override
	public IStoragePosition getPosition(){
		return position;
	}
	
	@Override
	public ItemStack getStorageStack(){
		return itemHandler.getStackInSlot(0);
	}
	
	@Override
	public List<IStoragePage> getChilds(){
		return childs;
	}
	
	@Override
	public void addChild(IStoragePage child){
		if(!childs.contains(child)){
			childs.add(child);
		}
	}
	
	@Override
	@Nullable
	public StoragePage getParent(){
		return null;
	}
	
	@Override
	public int getComplexity() {
		int complexity = 0;
		for (int index = 0; index < itemHandler.getSlots(); index++) {
			ItemStack slotStack = itemHandler.getStackInSlot(index);
			IModuleContainer container = ModuleHelper.getContainerFromItem(slotStack);
			if (container != null) {
				ModuleData data = container.getData();
				complexity += data.getComplexity();
			}
		}
		return complexity;
	}
	
	@Override
	public int getAllowedComplexity() {
		int allowedComplexity = 0;
		for (int index = 0; index < itemHandler.getSlots(); index++) {
			ItemStack slotStack = itemHandler.getStackInSlot(index);
			IModuleContainer container = ModuleHelper.getContainerFromItem(slotStack);
			if (container != null) {
				ModuleData data = container.getData();
				allowedComplexity += data.getAllowedComplexity();
			}
		}
		return allowedComplexity;
	}
	
	@Override
	public boolean isEmpty(){
		return false;
	}

	@Override
	public void setContainer(Container container) {
		
	}

	@Override
	public void detectAndSendChanges() {
		
	}
	
	@Override
	public boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setGui(GuiContainer gui) {
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void initGui() {
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawTooltips(int mouseX, int mouseY) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackground(int mouseX, int mouseY) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
	}
	
}
