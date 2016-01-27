package nedelosk.modularmachines.api.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.forestcore.library.gui.GuiBase;
import nedelosk.forestcore.library.tile.TileBaseInventory;
import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.modularmachines.api.modular.basic.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiModular<T extends TileBaseInventory & IModularTileEntity> extends GuiBase<T> {

	public GuiModular(T tile, InventoryPlayer inventory) {
		super(tile, inventory);
		widgetManager = new WidgetManagerModular(this);
		ModuleStack<IModule, IModuleGui> gui = tile.getModular().getGuiManager().getModuleWithGui();
		if (gui != null && gui.getModule() instanceof IProducerGuiWithWidgets) {
			((IProducerGuiWithWidgets) gui.getModule()).addWidgets(this, tile.getModular(), gui);
		}
		ySize = gui.getModule().getGuiTop(tile.getModular(), gui);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		ModuleStack stack = tile.getModular().getGuiManager().getModuleWithGui();
		stack.getModule().renderString(fontRenderer, guiLeft, guiTop, x, y, stack);
	}

	@Override
	protected void renderProgressBar() {
		IModularGuiManager guiManager = tile.getModular().getGuiManager();
		if (guiManager.getModuleWithGui().getModule() instanceof IModuleInventory) {
			for ( int slotID = 36; slotID < inventorySlots.inventorySlots.size(); slotID++ ) {
				Slot slot = ((ArrayList<Slot>) inventorySlots.inventorySlots).get(slotID);
				ModuleStack gui = guiManager.getModuleWithGui();
				if (slot.getSlotIndex() < ((IModuleInventory) gui.getModule()).getSizeInventory(gui)) {
					drawTexturedModalRect(guiLeft + slot.xDisplayPosition - 1, guiTop + slot.yDisplayPosition - 1, 56, 238, 18, 18);
				}
			}
		}
	}

	@Override
	public void addButtons() {
		ModuleStack gui = tile.getModular().getGuiManager().getModuleWithGui();
		IModularGuiManager guiManager = tile.getModular().getGuiManager();
		for ( int i = 0; i < guiManager.getModuleWithGuis().size(); i++ ) {
			buttonManager.add(new ButtonGuiTab(i, (i >= 7) ? guiLeft + 166 : guiLeft + -28, (i >= 7) ? guiTop + 8 + 22 * (i - 7) : guiTop + 8 + 22 * i,
					tile.getModular().getGuiManager().getModuleWithGuis().get(i), i >= 7));
		}
		if (gui != null && gui.getModule() instanceof IProducerGuiWithButtons) {
			((IProducerGuiWithButtons) gui.getModule()).addButtons(this, tile.getModular(), gui);
		}
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected String getGuiName() {
		return "modular_machine";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		ModuleStack gui = tile.getModular().getGuiManager().getModuleWithGui();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (gui != null && gui.getModule().getCustomGui(tile.getModular(), gui) != null) {
			RenderUtil.bindTexture(gui.getModule().getCustomGui(tile.getModular(), gui));
		} else {
			RenderUtil.bindTexture(guiTexture);
		}
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		RenderUtil.bindTexture(new ResourceLocation(getModName(), "textures/gui/inventory_player.png"));
		drawTexturedModalRect(this.guiLeft + 7, this.guiTop + ySize - 83, 7, 83, 162, 76);
		RenderUtil.bindTexture(guiTexture);
		renderProgressBar();
		gui.getModule().updateGui(this, guiLeft, guiTop, tile.getModular(), gui);
		widgetManager.drawWidgets();
	}
}
