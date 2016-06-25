package de.nedelosk.modularmachines.client.gui;

import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.client.gui.buttons.ButtonAssemblerGroup;
import de.nedelosk.modularmachines.client.gui.buttons.ButtonAssemblerSlot;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiModularAssembler extends GuiForestBase<IAssembler> {

	public static final ResourceLocation assemblerOverlay = new ResourceLocation("forestmods:textures/gui/modular_assembler_overlays.png");
	public static final ResourceLocation assembler = new ResourceLocation("forestmods:textures/gui/modular_assembler.png");

	public GuiModularAssembler(IAssembler assembler, InventoryPlayer inventory) {
		super(assembler, inventory);

		xSize = 256;
		ySize = 256;
	}

	@Override
	public void addButtons() {
		if(handler.getCurrentGroup() != null){
			for(IAssemblerSlot slot : handler.getCurrentGroup().getSlots().values()){
				buttonManager.add(new ButtonAssemblerSlot(buttonManager.getButtons().size(), guiLeft + 38 + slot.getXPos() * 18, guiTop + 8 + slot.getYPos() * 18, 18, 18, slot));
			}
		}

		for(int ID = 0;ID < handler.getMaxControllers();ID++){
			if(ID < 8){
				buttonManager.add(new ButtonAssemblerGroup(buttonList.size() + buttonManager.getButtons().size(), guiLeft + 14, guiTop + 44 + ID * 25, handler.getGroups().get(ID), ID));
			}else if(ID < 16){
				buttonManager.add(new ButtonAssemblerGroup(buttonList.size() + buttonManager.getButtons().size(), guiLeft + 224, guiTop + 44 + ID * 25, handler.getGroups().get(ID), ID));
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
		if(handler.getCurrentGroup() != null){
			for(IAssemblerSlot slot : handler.getCurrentGroup().getSlots().values()){
				slot.renderPaths(this);
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}
}
