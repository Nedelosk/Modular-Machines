package modularmachines.api.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import modularmachines.api.gui.GuiManager;
import modularmachines.api.gui.IContainerBase;
import modularmachines.api.gui.Page;
import modularmachines.api.gui.Widget;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.controller.IModuleController;
import modularmachines.api.modules.handlers.ContentInfo;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.handlers.inventory.IModuleInventory;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.handlers.inventory.ModuleInventoryBuilder;
import modularmachines.api.modules.handlers.inventory.SlotInfo;
import modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import modularmachines.api.modules.handlers.tank.FluidTankAdvanced;
import modularmachines.api.modules.handlers.tank.IModuleTank;
import modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import modularmachines.api.modules.handlers.tank.ModuleTankBuilder;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.core.managers.ItemManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public abstract class ModulePage<M extends IModule> extends Page implements IModulePage {

	protected static final ResourceLocation modularWdgets = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	protected final IModuleState<M> moduleState;
	protected final IModuleInventory inventory;
	protected final IModuleTank tank;
	protected final String pageID;
	private IModular modular;

	public ModulePage(String pageID, String title, IModuleState<M> moduleState) {
		super(title);
		this.modular = moduleState.getModular();
		this.moduleState = moduleState;
		this.pageID = pageID;
		this.inventory = createInventory();
		this.tank = createTank();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (gui != null) {
			Widget widget = gui.getWidgetManager().getWidgetAtMouse(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
			if (widget != null) {
				onClickeWidget(widget, mouseButton);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void onClickeWidget(Widget widget, int mouseButton) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui() {
		if (gui != null) {
			for(Widget widget : (List<Widget>) gui.getWidgetManager().getWidgets()) {
				onUpdateWidget(widget);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void onUpdateWidget(Widget widget) {
		if (widget.getProvider() instanceof IModuleState) {
			Widget<IModuleState> widgetState = widget;
			if(widgetState.getProvider().getIndex() == moduleState.getIndex()){
				if(!widgetState.getProvider().equals(moduleState)){
					widgetState.setProvider(moduleState);
				}
			}else{
				IModuleState state = moduleState.getModular().getModule(widgetState.getProvider().getIndex());
				if(!widgetState.getProvider().equals(state)){
					widgetState.setProvider(state);
				}
			}
		} else if (widget.getProvider() instanceof IFluidTank) {
			Widget<IFluidTank> widgetTank = widget;
			if (tank != null) {
				if (widgetTank.getProvider() instanceof FluidTankAdvanced) {
					FluidTankAdvanced tank = this.tank.getTank(((FluidTankAdvanced) widgetTank.getProvider()).index);
					if (!((FluidTankAdvanced) widgetTank.getProvider()).equals(tank)) {
						widgetTank.setProvider(tank);
					}
				}
			}
		}
	}

	protected IModuleInventory createInventory() {
		IModuleInventoryBuilder invBuilder = new ModuleInventoryBuilder();
		invBuilder.setModuleState(moduleState);
		createInventory(invBuilder);
		if (!invBuilder.isEmpty()) {
			return invBuilder.build();
		}
		return null;
	}

	protected IModuleTank createTank() {
		IModuleTankBuilder tankBuilder = new ModuleTankBuilder();
		tankBuilder.setModuleState(moduleState);
		createTank(tankBuilder);
		if (!tankBuilder.isEmpty()) {
			return tankBuilder.build();
		}
		return null;
	}

	protected void createTank(IModuleTankBuilder tankBuilder) {
	}

	protected void createInventory(IModuleInventoryBuilder invBuilder) {
	}

	@Override
	public IModuleTank getTank() {
		return tank;
	}

	@Override
	public IModuleInventory getInventory() {
		return inventory;
	}

	@Override
	public List<IModuleContentHandler> getContentHandlers() {
		List<IModuleContentHandler> handlers = new ArrayList<>();
		if (tank != null) {
			handlers.add(tank);
		}
		if (inventory != null) {
			handlers.add(inventory);
		}
		return handlers;
	}

	@Override
	public <H> H getContentHandler(Class<? extends H> contentClass) {
		if (IItemHandler.class.isAssignableFrom(contentClass) || IModuleInventory.class.isAssignableFrom(contentClass)) {
			return (H) inventory;
		}
		if (IFluidHandler.class.isAssignableFrom(contentClass) || IModuleTank.class.isAssignableFrom(contentClass)) {
			return (H) tank;
		}
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (tank != null) {
			nbt.setTag("Tank", tank.serializeNBT());
		}
		if (inventory != null) {
			nbt.setTag("Inventory", inventory.serializeNBT());
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Tank") && tank != null) {
			tank.deserializeNBT(nbt.getCompoundTag("Tank"));
		}
		if (nbt.hasKey("Inventory") && inventory != null) {
			inventory.deserializeNBT(nbt.getCompoundTag("Inventory"));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackground(int mouseX, int mouseY) {
		super.drawBackground(mouseX, mouseY);
		drawSlots();
	}

	@SideOnly(Side.CLIENT)
	protected void drawSlots() {
		if (gui != null) {
			if (inventory != null && gui.getGui() instanceof GuiContainer) {
				Container container = ((GuiContainer) gui).inventorySlots;
				for(int slotID = 36; slotID < container.inventorySlots.size(); slotID++) {
					Slot slot = ((ArrayList<Slot>) container.inventorySlots).get(slotID);
					if (slot.getSlotIndex() < inventory.getSlots()) {
						drawSlot(slot);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void drawSlot(Slot slot) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(modularWdgets);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + slot.xDisplayPosition - 1, gui.getGuiTop() + slot.yDisplayPosition - 1, 56, 238, 18, 18);
	}

	@Override
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

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getGuiTexture() {
		return new ResourceLocation("modularmachines:textures/gui/modular_machine.png");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		List<IModuleState> modulesWithPages = ModuleManager.getModulesWithPages(getModular());
		int i = 0;
		if (!modulesWithPages.isEmpty() && modulesWithPages.size() > 1) {
			for(i = 0; i < modulesWithPages.size(); i++) {
				IModuleState module = modulesWithPages.get(i);
				boolean isRight = i >= 7;
				add(GuiManager.helper.createModuleTab(isRight ? getXSize() : -28, 8 + 22 * (isRight ? i - 7 : i), module, modulesWithPages));
			}
		}
		if (modular.getModule(IModuleController.class) != null) {
			boolean isRight = i >= 7;
			Widget widget = GuiManager.helper.createAssembleTab(isRight ? getXSize() : -28, 8 + 22 * (isRight ? i - 7 : i), isRight);
			add(widget);
			widget.setProvider(new ItemStack(ItemManager.itemChassis));
		}
		if (!moduleState.getPages().isEmpty() && moduleState.getPages().size() > 1) {
			for(int pageIndex = 0; pageIndex < moduleState.getPages().size(); pageIndex++) {
				IModulePage page = moduleState.getPages().get(pageIndex);
				add(GuiManager.helper.createModulePageTab(pageIndex > 4 ? 12 + (pageIndex - 5) * 30 : 12 + pageIndex * 30, pageIndex > 4 ? getYSize() : -19, page));
			}
		}
		if (tank != null) {
			for(ContentInfo info : tank.getContentInfos()) {
				if (info != null) {
					add(GuiManager.helper.createFluidTank(info.xPosition, info.yPosition, tank.getTank(info.index)));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawTooltips(int mouseX, int mouseY) {
	}

	@Override
	public IModular getModular() {
		if (modular == null && moduleState.getModular() != null) {
			modular = moduleState.getModular();
		}
		return modular;
	}

	@Override
	public IModuleState getModuleState() {
		return moduleState;
	}

	@Override
	public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
		if (inventory != null) {
			for(SlotInfo info : inventory.getContentInfos()) {
				if (info != null) {
					modularSlots.add(new SlotModule(this, info));
				}
			}
		}
	}

	@Override
	public String getPageTitle() {
		ItemStack stack = moduleState.getProvider().getItemStack();
		if (stack != null && stack.hasDisplayName()) {
			return stack.getDisplayName();
		}
		if (title == null || title.isEmpty()) {
			return null;
		}
		return I18n.translateToLocal("module.page." + title.toLowerCase(Locale.ENGLISH) + ".name");
	}

	@Override
	public String getTabTitle() {
		return getPageTitle();
	}

	@Override
	public String getPageID() {
		return pageID;
	}
}
