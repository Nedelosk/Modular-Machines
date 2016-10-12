package de.nedelosk.modularmachines.api.modules.storage;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerContainer;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssemblerStorage;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChildPage implements IStoragePage{

	@SideOnly(Side.CLIENT)
	protected IGuiBase gui;
	protected IContainerBase container;
	protected final IStoragePosition position;
	protected IStoragePage parent;
	protected IModularAssembler assembler;

	public ChildPage(IStoragePosition position) {
		this.position = position;
	}

	@Override
	public IGuiBase getGui() {
		return gui;
	}

	@Override
	public void setGui(IGuiBase gui) {
		this.gui = gui;
	}

	@Override
	public IContainerBase getContainer(){
		return container;
	}

	@Override
	public void setContainer(IContainerBase container){
		this.container = container;
	}

	@Override
	public void updateGui() {
		if(parent != null){
			parent.updateGui();
		}
	}

	@Override
	public void initGui() {
		if(parent != null){
			parent.setGui(gui);
			parent.initGui();
		}
	}

	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		if(parent != null){
			parent.handleMouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		if(parent != null){
			parent.drawForeground(fontRenderer, mouseX, mouseY);
		}
	}

	@Override
	public void drawBackground(int mouseX, int mouseY) {
		if(parent != null){
			parent.drawBackground(mouseX, mouseY);
		}
	}

	@Override
	public void drawTooltips(int mouseX, int mouseY) {
		if(parent != null){
			parent.drawTooltips(mouseX, mouseY);
		}
	}

	@Override
	public void drawFrontBackground(int mouseX, int mouseY) {
		if(parent != null){
			parent.drawFrontBackground(mouseX, mouseY);
		}
	}

	@Override
	public int getXSize() {
		if(parent != null){
			return parent.getXSize();
		}
		return 0;
	}

	@Override
	public int getYSize() {
		if(parent != null){
			return parent.getYSize();
		}
		return 0;
	}

	@Override
	public void addButtons() {
		if(parent != null){
			parent.addButtons();
		}
	}

	@Override
	public void addWidgets() {
		if(parent != null){
			parent.addWidgets();
		}
	}

	@Override
	public String getPageTitle() {
		if(parent != null){
			parent.getPageTitle();
		}
		return null;
	}

	@Override
	public IItemHandlerStorage getItemHandler() {
		if(parent != null){
			return parent.getItemHandler();
		}
		return null;
	}

	@Override
	public void createSlots(IAssemblerContainer container, List<Slot> slots) {
		if(parent != null){
			parent.createSlots(container, slots);
		}
	}

	@Override
	public boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot) {
		if(parent != null){
			return parent.isItemValid(stack, slot, storageSlot);
		}
		return false;
	}

	@Override
	public int getPlayerInvPosition() {
		if(parent != null){
			return parent.getPlayerInvPosition();
		}
		return 0;
	}

	@Override
	public void onSlotChanged(IAssemblerContainer container) {
		if(parent != null){
			parent.onSlotChanged(container);
		}
	}

	@Override
	public void setAssembler(IModularAssembler assembler) {
		this.assembler = assembler;
	}

	@Override
	public IModularAssembler getAssembler() {
		return assembler;
	}

	@Override
	public ItemStack getStorageStack() {
		if(parent != null){
			return parent.getStorageStack();
		}
		return null;
	}

	@Override
	public void detectAndSendChanges() {
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return new NBTTagCompound();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
	}

	@Override
	public void canAssemble(IModular modular) throws AssemblerException {
	}

	@Override
	public IStorage assemble(IModular modular) throws AssemblerException {
		return null;
	}

	@Override
	public IStoragePage setParent(IStoragePage parentPage) {
		this.parent = parentPage;
		return this;
	}

	@Override
	public IStoragePage getParent() {
		return parent;
	}

	@Override
	public IStoragePage addChild(IStoragePage childPage) {
		return this;
	}

	@Override
	public List<IStoragePage> getChilds() {
		return Collections.emptyList();
	}
}
