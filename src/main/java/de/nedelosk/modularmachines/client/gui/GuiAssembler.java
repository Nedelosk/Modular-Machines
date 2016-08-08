package de.nedelosk.modularmachines.client.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.client.gui.buttons.AssemblerAssembleTab;
import de.nedelosk.modularmachines.client.gui.buttons.AssemblerTab;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssemblerStorage;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GuiAssembler extends GuiBase<IModularHandler> {

	public AssemblerException lastException;

	public GuiAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		if(!handler.isAssembled()){
			if(handler.getAssembler() instanceof IPositionedModularAssembler){
				String s = Translator.translateToLocal("module.storage." + ((IPositionedModularAssembler)handler.getAssembler()).getSelectedPosition().getName() + ".name");
				this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
			}else{
				String s = Translator.translateToLocal("module.storage.name");
				this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
			}
		}

		String exceptionText;
		if(lastException != null){
			exceptionText = lastException.getLocalizedMessage();
		}else{
			exceptionText = Translator.translateToLocal("modular.assembler.info");
		}
		if(lastException != null){
			this.fontRendererObj.drawSplitString(exceptionText, 186, 83, 117, Color.WHITE.getRGB());
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(guiTexture);
		drawTexturedModalRect(this.guiLeft + 180, this.guiTop + 77, 130, 173, 126, 83);
		render();
		widgetManager.drawWidgets();
	}

	@Override
	public boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
		if(slot instanceof SlotAssembler){
			if(!((SlotAssembler)slot).isActive){
				return false;
			}
		}
		return super.isMouseOverSlot(slot, mouseX, mouseY);
	}

	@Override
	public void drawSlot(Slot slot) {
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		if(slot instanceof SlotAssembler){
			RenderUtil.bindTexture(guiTexture);
			drawTexturedModalRect(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1, ((SlotAssembler)slot).isActive ? 56 : 74, 238, 18, 18);
		}else if(slot instanceof SlotAssemblerStorage){
			RenderUtil.bindTexture(guiTexture);
			drawTexturedModalRect(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1, 56, 238, 18, 18);
		}
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		if(slot instanceof SlotAssembler){
			if(!((SlotAssembler)slot).isActive){
				return;
			}
		}
		super.drawSlot(slot);
	}

	@Override
	public void addButtons() {
		//left
		if(handler.getAssembler() instanceof IPositionedModularAssembler){
			buttonManager.add(new AssemblerTab(0, guiLeft + 8, guiTop + 5, EnumPosition.TOP, false));
			buttonManager.add(new AssemblerTab(1, guiLeft + 8, guiTop + 27, EnumPosition.LEFT, false));
			buttonManager.add(new AssemblerTab(2, guiLeft + 8, guiTop + 49, EnumPosition.INTERNAL, false));
			//right
			buttonManager.add(new AssemblerTab(3, guiLeft + 140, guiTop + 5, EnumPosition.BACK, true));
			buttonManager.add(new AssemblerTab(4, guiLeft + 140, guiTop + 27, EnumPosition.RIGHT, true));
		}
		buttonManager.add(new AssemblerAssembleTab(5, guiLeft + 140, guiTop + 49));
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}

	@Nonnull
	public List<Rectangle> getExtraGuiAreas() {
		return Collections.singletonList(new Rectangle(guiLeft + 180, guiTop + 77, 126, 83));
	}
}