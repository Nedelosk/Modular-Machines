package de.nedelosk.modularmachines.client.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.client.gui.buttons.AssemblerAssembleTab;
import de.nedelosk.modularmachines.client.gui.buttons.AssemblerTab;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssemblerStorage;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiAssembler extends GuiBase<IModularHandler> {

	protected static final ResourceLocation modularWdgets = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public AssemblerException lastException;
	public boolean hasChange = false;
	public IModular modular;
	public ItemStack modularStack;
	public int complexity;
	public int complexityAllowed;
	public int positionComplexity;
	public int positionComplexityAllowed;

	public GuiAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
		onUpdate();
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
			this.fontRendererObj.drawSplitString(exceptionText, 186, 8, 117, Color.WHITE.getRGB());
		}

		String complexity = Translator.translateToLocal("modular.assembler.complexity");
		this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity"), -65 - (fontRendererObj.getStringWidth(complexity) / 2), 83, Color.WHITE.getRGB());
		this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity.current") + this.complexity, -124, 83 + 12, Color.WHITE.getRGB());
		this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity.allowed") + this.complexityAllowed, -124, 83 + 21, Color.WHITE.getRGB());
		if(handler.getAssembler() instanceof IPositionedModularAssembler && positionComplexityAllowed > 0){
			String positionComplexity = Translator.translateToLocal("modular.assembler.complexity.position");
			this.fontRendererObj.drawString(Translator.translateToLocal(positionComplexity), -65 - (fontRendererObj.getStringWidth(positionComplexity) / 2), 83 + 36, Color.WHITE.getRGB());
			this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity.current") + this.positionComplexity, -124, 83 + 48, Color.WHITE.getRGB());
			this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity.allowed") + this.positionComplexityAllowed, -124, 83 + 57, Color.WHITE.getRGB());
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if(hasChange){
			onUpdate();
			hasChange = false;
		}
	}

	protected void onUpdate(){
		IModularAssembler assembler = handler.getAssembler();
		if(handler != null && handler.getAssembler() != null){
			try{
				lastException = null;
				modular = assembler.assemble();
				modularStack = ModularMachinesApi.saveModular(new ItemStack(BlockManager.blockModular), modular, player);
			}catch(AssemblerException exception){
				lastException = exception;
				modular = null;
				modularStack = null;
			}
			complexity = assembler.getComplexity(true, null);
			complexityAllowed = assembler.getAllowedComplexity(null);
			if(assembler instanceof IPositionedModularAssembler){
				IPositionedModularAssembler positionedAssembler = (IPositionedModularAssembler) assembler;
				positionComplexity = assembler.getComplexity(false, positionedAssembler.getSelectedPosition());
				positionComplexityAllowed = assembler.getAllowedComplexity(positionedAssembler.getSelectedPosition());
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();

		hasChange = true;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(modularWdgets);
		drawTexturedModalRect(this.guiLeft + 180, this.guiTop + 2, 0, 0, 126, 158);
		drawTexturedModalRect(this.guiLeft + -130, this.guiTop + 77, 130, 0, 126, 83);
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
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(modularWdgets);
		if(slot instanceof SlotAssembler){
			drawTexturedModalRect(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1,  56, ((SlotAssembler)slot).isActive ? 238 : 220, 18, 18);
		}else if(slot instanceof SlotAssemblerStorage){
			drawTexturedModalRect(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1, 56, 238, 18, 18);
		}
		RenderUtil.bindTexture(guiTexture);
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
			buttonManager.add(new AssemblerTab(0, guiLeft + 8, guiTop + 5, EnumStoragePosition.TOP, false));
			buttonManager.add(new AssemblerTab(1, guiLeft + 8, guiTop + 27, EnumStoragePosition.LEFT, false));
			buttonManager.add(new AssemblerTab(2, guiLeft + 8, guiTop + 49, EnumStoragePosition.INTERNAL, false));
			//right
			buttonManager.add(new AssemblerTab(3, guiLeft + 140, guiTop + 5, EnumStoragePosition.BACK, true));
			buttonManager.add(new AssemblerTab(4, guiLeft + 140, guiTop + 27, EnumStoragePosition.RIGHT, true));
		}
		buttonManager.add(new AssemblerAssembleTab(5, guiLeft + 140, guiTop + 49));
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}

	@Nonnull
	public List<Rectangle> getExtraGuiAreas() {
		return Collections.singletonList(new Rectangle(guiLeft + 180, guiTop + 2, 126, 158));
	}
}