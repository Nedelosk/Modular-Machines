package de.nedelosk.modularmachines.client.gui;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.client.gui.buttons.ButtonAssemblerTab;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAssembler extends GuiForestBase<IModularHandler> {

	public GuiAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String s = Translator.translateToLocal("mm.gui.modular.assembler.name");
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
	}

	@Override
	public void addButtons() {
		//left
		buttonManager.add(new ButtonAssemblerTab(0, guiLeft + 8, guiTop + 5, EnumPosition.TOP, false, 0));
		buttonManager.add(new ButtonAssemblerTab(1, guiLeft + 8, guiTop + 27, EnumPosition.INTERNAL, false, 10));
		buttonManager.add(new ButtonAssemblerTab(2, guiLeft + 8, guiTop + 49, EnumPosition.LEFT, false, 14));
		//right
		buttonManager.add(new ButtonAssemblerTab(0, guiLeft + 140, guiTop + 5, EnumPosition.DOWN, true, 0));
		buttonManager.add(new ButtonAssemblerTab(1, guiLeft + 140, guiTop + 27, EnumPosition.BACK, true, 10));
		buttonManager.add(new ButtonAssemblerTab(2, guiLeft + 140, guiTop + 49, EnumPosition.RIGHT, true, 14));
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}
}