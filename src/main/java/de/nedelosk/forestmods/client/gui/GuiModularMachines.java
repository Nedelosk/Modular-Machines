package de.nedelosk.forestmods.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.managers.IModularGuiManager;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.buttons.ButtonModulePageTab;
import de.nedelosk.forestmods.client.gui.buttons.ButtonModuleTab;
import de.nedelosk.forestmods.client.gui.widgets.WidgetManagerModular;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiModularMachines<T extends TileEntity & IModularTileEntity<IModular>> extends GuiForestBase<T> {

	protected IModulePage currentPage;
	protected IModule module;

	public GuiModularMachines(T tile, InventoryPlayer inventory, IModulePage currentPage) {
		super(tile, inventory);
		this.currentPage = currentPage;
		this.module = currentPage.getModuleStack().getModule();
		module.createGui();
		widgetManager = new WidgetManagerModular(this, currentPage);
		widgetManager.add(module.getGui().getWidgets());
		ySize = module.getGui().getGuiTop();
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		currentPage.renderStrings(fontRenderer, guiLeft, guiTop, x, y);
	}

	@Override
	protected void render() {
		IModuleInventory inv = module.getInventory();
		if (inv != null) {
			for ( int slotID = 36; slotID < inventorySlots.inventorySlots.size(); slotID++ ) {
				Slot slot = ((ArrayList<Slot>) inventorySlots.inventorySlots).get(slotID);
				if (slot.getSlotIndex() < inv.getSizeInventory()) {
					drawTexturedModalRect(guiLeft + slot.xDisplayPosition - 1, guiTop + slot.yDisplayPosition - 1, 56, 238, 18, 18);
				}
			}
		}
	}

	@Override
	public void addButtons() {
		IModularGuiManager guiManager = tile.getModular().getManager(IModularGuiManager.class);
		for ( int i = 0; i < guiManager.getAllGuis().size(); i++ ) {
			ModuleStack stack = guiManager.getAllGuis().get(i).getModuleStack();
			buttonManager.add(new ButtonModuleTab(i, (i >= 7) ? guiLeft + 166 : guiLeft + -28, (i >= 7) ? guiTop + 8 + 22 * (i - 7) : guiTop + 8 + 22 * i,
					stack, i >= 7));
		}
		for ( int pageID = 0; pageID < module.getPages().length; pageID++ ) {
			IModulePage page = module.getPages()[pageID];
			buttonManager.add(
					new ButtonModulePageTab(getButtonManager().getButtons().size(), pageID > 4 ? 12 + guiLeft + (pageID - 5) * 30 : 12 + guiLeft + pageID * 30,
							pageID > 4 ? module.getGui().getGuiTop() + guiTop : -19 + guiTop, module.getModuleStack(), pageID > 4 ? true : false, pageID));
		}
		buttonManager.add(module.getGui().getButtons());
	}

	@Override
	protected String getGuiTexture() {
		return "modular_machine";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation guiTexture = this.guiTexture;
		if (module.getGui().getCustomGui() != null) {
			guiTexture = module.getGui().getCustomGui();
		}
		RenderUtil.bindTexture(guiTexture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		RenderUtil.bindTexture(guiTexture);
		render();
		currentPage.updateGui(this, guiLeft, guiTop);
		widgetManager.drawWidgets();
		RenderUtil.bindTexture(new ResourceLocation(getTextureModID(), "textures/gui/inventory_player.png"));
		drawTexturedModalRect(this.guiLeft + 7, this.guiTop + ySize - module.getInventory().getPlayerInvPosition(), 7, 83, 162, 76);
	}
}
