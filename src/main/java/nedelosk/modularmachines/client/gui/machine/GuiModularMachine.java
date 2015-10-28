package nedelosk.modularmachines.client.gui.machine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.client.gui.GuiBase;
import nedelosk.forestday.utils.RenderUtils;
import nedelosk.modularmachines.api.modular.machines.manager.IModularGuiManager;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGui;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGuiWithButtons;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.ModularPageSaver;
import nedelosk.modularmachines.common.network.packets.machine.ModularPageTileSaver;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSelectPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiModularMachine extends GuiBase<TileModular> {

	public InventoryPlayer inventory;

	public GuiModularMachine(TileModular tile, InventoryPlayer inventory) {
		super(tile, inventory);
		this.inventory = inventory;
		widgetManager = new WidgetManagerModular(this);

		ModuleStack<IModule, IProducerGui> gui = tile.getModular().getGuiManager().getModuleWithGui(Minecraft.getMinecraft().thePlayer, tile);

		if (gui != null && gui.getProducer() instanceof IProducerGuiWithWidgets)
			((IProducerGuiWithWidgets) gui.getProducer()).addWidgets(this, tile.modular, gui);
		ySize = gui.getProducer().getGuiTop(tile.modular, gui);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		ModuleStack<IModule, IProducerGui> stack = tile.getModular().getGuiManager().getModuleWithGui(Minecraft.getMinecraft().thePlayer, tile);
		stack.getProducer().renderString(fontRenderer, guiLeft, guiTop, x, y, stack);
	}

	@Override
	protected void renderProgressBar() {

		IModularGuiManager guiManager = tile.getModular().getGuiManager();

		if (guiManager.getModuleWithGui(Minecraft.getMinecraft().thePlayer, tile).getProducer() instanceof IProducerInventory) {
			for (Slot slot : (ArrayList<Slot>) inventorySlots.inventorySlots) {
				ModuleStack gui = guiManager.getModuleWithGui(Minecraft.getMinecraft().thePlayer, tile);
				if (slot.slotNumber < ((IProducerInventory) gui.getProducer()).getSizeInventory(gui)) {
					RenderUtils.drawTexturedModalRect(guiLeft + slot.xDisplayPosition - 1,
							guiTop + slot.yDisplayPosition - 1, 1, 56, 238, 18, 18);
				}
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();

		int id = 0;
		ModuleStack<IModule, IProducerGui> gui = tile.getModular().getGuiManager().getModuleWithGui(Minecraft.getMinecraft().thePlayer, tile);
		IModularGuiManager guiManager = tile.getModular().getGuiManager();

		for (int i = 0; i < guiManager.getModuleWithGuis().size(); i++) {
			buttonList.add(new GuiBookmarkModular(i, (i >= 7) ? guiLeft + 166 : guiLeft + -28,
					(i >= 7) ? guiTop + 8 + 22 * (i - 7) : guiTop + 8 + 22 * i,
					tile.getModular().getGuiManager().getModuleWithGuis().get(i), i >= 7));
			id++;
		}

		if (gui != null && gui.getProducer() instanceof IProducerGuiWithButtons)
			((IProducerGuiWithButtons) gui.getProducer()).addButtons(this, this.tile.modular, gui);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button instanceof GuiBookmarkModular) {
			GuiBookmarkModular bookmark = (GuiBookmarkModular) button;
			IModularGuiManager guiManager = tile.getModular().getGuiManager();

			if (!guiManager.getPage().equals(bookmark.stack.getModule().getName(bookmark.stack))) {
				guiManager.setPage(bookmark.stack.getModule().getName(bookmark.stack));
				EntityPlayer entityPlayer = inventory.player;
				if(entityPlayer.getExtendedProperties(ModularPageSaver.class.getName()) != null)
					if(((ModularPageSaver)entityPlayer.getExtendedProperties(ModularPageSaver.class.getName())).getSave(tile.xCoord, tile.yCoord, tile.zCoord) != null)
						((ModularPageSaver)entityPlayer.getExtendedProperties(ModularPageSaver.class.getName())).getSave(tile.xCoord, tile.yCoord, tile.zCoord).page = guiManager.getPage();
					else
						((ModularPageSaver)entityPlayer.getExtendedProperties(ModularPageSaver.class.getName())).saver.add(new ModularPageTileSaver(guiManager.getPage(), tile.xCoord, tile.yCoord, tile.zCoord));
				else
					entityPlayer.registerExtendedProperties(ModularPageSaver.class.getName(), new ModularPageSaver(new ModularPageTileSaver(guiManager.getPage(), tile.xCoord, tile.yCoord, tile.zCoord)));
				PacketHandler.INSTANCE.sendToServer(new PacketModularSelectPage(this.tile, bookmark.stack.getModule().getName(bookmark.stack)));
			}
		}
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
		ModuleStack<IModule, IProducerGui> gui = tile.getModular().getGuiManager().getModuleWithGui(Minecraft.getMinecraft().thePlayer, tile);
		IModularGuiManager guiManager = tile.getModular().getGuiManager();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		if (gui != null && gui.getProducer().getCustomGui(tile.modular, gui) != null)
			RenderUtils.bindTexture(gui.getProducer().getCustomGui(tile.modular, gui));
		else
			RenderUtils.bindTexture(guiTexture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		RenderUtils.bindTexture(new ResourceLocation(getModName(), "textures/gui/inventory_player.png"));
		drawTexturedModalRect(this.guiLeft + 7, this.guiTop + ySize - 83, 7, 83, 162, 76);

		RenderUtils.bindTexture(guiTexture);
		renderProgressBar();
		gui.getProducer().updateGui(this, guiLeft, guiTop, tile.getModular(), gui);
		widgetManager.drawWidgets();

	}

}
