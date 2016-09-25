package de.nedelosk.modularmachines.client.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IAssemblerGui;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssemblerStorage;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleModuleStorage;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetAssembleTab;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetAssemblerTab;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiAssembler extends GuiBase<IModularHandler> implements IAssemblerGui{

	protected static final ResourceLocation modularWdgets = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public final IStoragePage page;
	public AssemblerException lastException;
	public boolean hasChange = false;
	public IModular modular;
	public int complexity;
	public int complexityAllowed;
	public int positionComplexity;
	public int positionComplexityAllowed;

	public final WidgetAssembleTab assembleTab;

	public GuiAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);

		this.page = tile.getAssembler().getStoragePage(tile.getAssembler().getSelectedPosition());

		if(page != null){
			page.setGui(this);
			page.addWidgets();
		}

		List<IStoragePosition> positions = handler.getStoragePositions();
		//left
		widgetManager.add(new WidgetAssemblerTab(8, 5, tile.getAssembler(), positions.get(0), false));
		widgetManager.add(new WidgetAssemblerTab(8, 27, tile.getAssembler(), positions.get(1), false));
		widgetManager.add(new WidgetAssemblerTab(8, 49, tile.getAssembler(), positions.get(2), false));
		//right
		widgetManager.add(new WidgetAssemblerTab(140, 5, tile.getAssembler(), positions.get(3), true));
		widgetManager.add(new WidgetAssemblerTab(140, 27, tile.getAssembler(), positions.get(4), true));
		widgetManager.add(assembleTab = new WidgetAssembleTab(140, 49));

		onUpdate();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		if(!handler.isAssembled()){
			String s = Translator.translateToLocal("module.storage." + handler.getAssembler().getSelectedPosition().getName() + ".name");
			this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		}

		String exceptionText;
		if(lastException != null){
			exceptionText = lastException.getLocalizedMessage();
		}else{
			exceptionText = Translator.translateToLocal("modular.assembler.info");
		}
		this.fontRendererObj.drawSplitString(exceptionText, 186, 8, 117, Color.WHITE.getRGB());

		String complexity = Translator.translateToLocal("modular.assembler.complexity");
		this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity"), -65 - (fontRendererObj.getStringWidth(complexity) / 2), 83, Color.WHITE.getRGB());
		this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity.current") + this.complexity, -124, 83 + 12, Color.WHITE.getRGB());
		this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity.allowed") + this.complexityAllowed, -124, 83 + 21, Color.WHITE.getRGB());

		if(positionComplexityAllowed > 0 && page != null){
			ItemStack stack = page.getStorageStack();
			IModuleContainer contrainer = ModuleManager.getContainerFromItem(stack);
			if(contrainer != null && contrainer.getModule() instanceof IModuleModuleStorage){
				String positionComplexity = Translator.translateToLocal("modular.assembler.complexity.position");
				this.fontRendererObj.drawString(Translator.translateToLocal(positionComplexity), -65 - (fontRendererObj.getStringWidth(positionComplexity) / 2), 83 + 36, Color.WHITE.getRGB());
				this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity.current") + this.positionComplexity, -124, 83 + 48, Color.WHITE.getRGB());
				this.fontRendererObj.drawString(Translator.translateToLocal("modular.assembler.complexity.allowed") + this.positionComplexityAllowed, -124, 83 + 57, Color.WHITE.getRGB());
			}
		}

		if(page != null){
			page.drawForeground(getFontRenderer(), mouseX, mouseY);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if(page != null){
			page.updateGui();
		}

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
				assembleTab.setProvider(ModularManager.saveModularToItem(new ItemStack(BlockManager.blockModular), modular, player));
			}catch(AssemblerException exception){
				lastException = exception;
				modular = null;
				assembleTab.setProvider(null);
			}
			complexity = assembler.getComplexity(true, null);
			complexityAllowed = assembler.getAllowedComplexity(null);
			positionComplexity = assembler.getComplexity(false, assembler.getSelectedPosition());
			positionComplexityAllowed = assembler.getAllowedComplexity(assembler.getSelectedPosition());
		}
	}

	@Override
	public void initGui() {
		super.initGui();

		if(page != null){
			page.initGui();
		}

		hasChange = true;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, mouseX, mouseY);
		if(page != null){
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			page.drawBackground(mouseX, mouseY);
			widgetManager.drawWidgets();
			page.drawFrontBackground(mouseX, mouseY);
		}

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
		if(page != null){
			page.addButtons();
		}
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}

	@Nonnull
	public List<Rectangle> getExtraGuiAreas() {
		return Collections.singletonList(new Rectangle(guiLeft + 180, guiTop + 2, 126, 158));
	}

	@Override
	public void setHasChange() {
		hasChange = true;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float p_73863_3_) {
		super.drawScreen(mouseX, mouseY, p_73863_3_);
		if(page != null){
			page.drawTooltips(mouseX, mouseY);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(page != null){
			page.handleMouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	public IPage getPage() {
		return page;
	}
}