package de.nedelosk.modularmachines.client.gui;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.client.gui.buttons.ButtonAssemblerTab;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssemblerStorage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModularAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GuiAssembler extends GuiForestBase<IModularHandler> {

	public AssemblerException lastException;

	public GuiAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		if(!handler.isAssembled()){
			String s = Translator.translateToLocal("module.storage." + handler.getAssembler().getSelectedPosition().getName() + ".name");
			this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(guiTexture);
		drawTexturedModalRect(this.guiLeft + 176, this.guiTop + 77, 176, 77, 48, 86);
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
			if(((SlotAssembler)slot).isActive){
				super.drawSlot(slot);
			}
		}else{
			super.drawSlot(slot);
		}
	}

	@Override
	public void addButtons() {
		//left
		buttonManager.add(new ButtonAssemblerTab(0, guiLeft + 8, guiTop + 5, EnumPosition.TOP, false));
		buttonManager.add(new ButtonAssemblerTab(1, guiLeft + 8, guiTop + 27, EnumPosition.LEFT, false));
		buttonManager.add(new ButtonAssemblerTab(2, guiLeft + 8, guiTop + 49, EnumPosition.INTERNAL, false));
		//right
		buttonManager.add(new ButtonAssemblerTab(3, guiLeft + 140, guiTop + 5, EnumPosition.BACK, true));
		buttonManager.add(new ButtonAssemblerTab(4, guiLeft + 140, guiTop + 27, EnumPosition.RIGHT, true));

		buttonList.add(new GuiButton(5, guiLeft + 100, guiTop + 90, Translator.translateToLocal("modular.assembler.assemble")));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if(button.id == 5){
			try{
				IModular modular = handler.getAssembler().assemble();
				if(modular != null){
					handler.setAssembled(true);
					handler.setModular(modular);
					handler.setAssembler(null);
					PacketHandler.INSTANCE.sendToServer(new PacketModularAssembler(handler, true));
				}
			}catch(AssemblerException e){
				lastException = e;
			}
		}
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}

	@Nonnull
	public List<Rectangle> getExtraGuiAreas() {
		return Collections.singletonList(new Rectangle(guiLeft + 176, guiTop + 77, 48, 86));
	}
}