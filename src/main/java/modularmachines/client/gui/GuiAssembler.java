package modularmachines.client.gui;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.assemblers.SlotModule;
import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.gui.widgets.WidgetAssembleTab;
import modularmachines.client.gui.widgets.WidgetAssemblerTab;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

public class GuiAssembler extends GuiBase<IAssembler, IAssembler> {

	protected static final ResourceLocation modularWdgets = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public final IStoragePage page;
	public AssemblerError lastError;
	public boolean hasChange = false;
	public int complexity;
	public int complexityAllowed;
	public int positionComplexity;
	public int positionComplexityAllowed;
	public final WidgetAssembleTab assembleTab;

	public GuiAssembler(IAssembler assembler, InventoryPlayer inventory) {
		super(assembler, assembler, inventory);
		this.page = assembler.getPage(assembler.getSelectedPosition());
		if (!page.isEmpty()) {
			//page.setGui(this);
			//page.initGui();
		}
		WidgetAssembleTab assembleTab = null;
		int xPosLeft = 8;
		int xPosRight = 140;
		int pageSize = 69;
		int yStartPos = 3;
		List<IStoragePosition> positions = source.getPositions();
		double positionsPerSize = (positions.size() + 1) / 2;
		double spacePerTab = pageSize / positionsPerSize;
		int positionIndex = 0;
		for (int side = 0; side < 2; side++) {
			for (int index = 0; index < positionsPerSize; index++) {
				boolean isRight = side == 1;
				int xPos = side == 0 ? xPosLeft : xPosRight;
				int yPos = (int) Math.round(yStartPos + (index * spacePerTab));
				if (positionIndex < positions.size()) {
					widgetManager.add(new WidgetAssemblerTab(xPos, yPos, positions.get(positionIndex), isRight));
					positionIndex++;
				} else {
					widgetManager.add(assembleTab = new WidgetAssembleTab(xPos, yPos, isRight));
				}
			}
		}
		if (assembleTab == null) {
			widgetManager.add(assembleTab = new WidgetAssembleTab(140, 49, true));
		}
		this.assembleTab = assembleTab;
		onUpdate();
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (!page.isEmpty()) {
			//page.setGui(null);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		String s = Translator.translateToLocal("module.storage." + page.getPosition().getName() + ".name");
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		String exceptionText;
		if (lastError != null) {
			exceptionText = lastError.getMessage();
		} else {
			exceptionText = Translator.translateToLocal("modular.assembler.info");
		}
		this.fontRenderer.drawSplitString(exceptionText, 186, 8, 117, Color.WHITE.getRGB());
		String complexity = Translator.translateToLocal("modular.assembler.complexity");
		this.fontRenderer.drawString(Translator.translateToLocal("modular.assembler.complexity"), -65 - (fontRenderer.getStringWidth(complexity) / 2), 83, Color.WHITE.getRGB());
		this.fontRenderer.drawString(Translator.translateToLocal("modular.assembler.complexity.current") + this.complexity, -124, 83 + 12, Color.WHITE.getRGB());
		this.fontRenderer.drawString(Translator.translateToLocal("modular.assembler.complexity.allowed") + this.complexityAllowed, -124, 83 + 21, Color.WHITE.getRGB());
		if (!page.isEmpty()) {
			if (positionComplexityAllowed > 0) {
				ItemStack stack = page.getStorageStack();
				IModuleDataContainer container = ModuleHelper.getContainerFromItem(stack);
				if (container != null) {
					ModuleData data = container.getData();
					if (data.isStorage(page.getPosition())) {
						String positionComplexity = Translator.translateToLocal("modular.assembler.complexity.position");
						this.fontRenderer.drawString(Translator.translateToLocal(positionComplexity), -65 - (fontRenderer.getStringWidth(positionComplexity) / 2), 83 + 36, Color.WHITE.getRGB());
						this.fontRenderer.drawString(Translator.translateToLocal("modular.assembler.complexity.current") + this.positionComplexity, -124, 83 + 48, Color.WHITE.getRGB());
						this.fontRenderer.drawString(Translator.translateToLocal("modular.assembler.complexity.allowed") + this.positionComplexityAllowed, -124, 83 + 57, Color.WHITE.getRGB());
					}
				}
			}
			//page.drawForeground(getFontRenderer(), mouseX, mouseY);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!page.isEmpty()) {
			//page.updateGui();
		}
		if (hasChange) {
			onUpdate();
			hasChange = false;
		}
	}

	protected void onUpdate() {
		if (source != null) {
			List<AssemblerError> errors = source.canAssemble();
			if(errors.isEmpty()){
				lastError = null;
			}else{
				lastError = errors.get(0);
			}
			/*if(source.canAssemble().isEmpty()){
				try {
					lastError = null;
					modular = source.createModular();
					assembleTab.setProvider(ModularManager.saveModularToItem(new ItemStack(BlockManager.blockModular), modular, player));
				} catch (AssemblerException exception) {
					lastError = exception;
					modular = null;
					assembleTab.setProvider(null);
				}
			}*/
			complexity = source.getComplexity();
			complexityAllowed = source.getAllowedComplexity();
			positionComplexity = page.getComplexity();
			positionComplexityAllowed = page.getAllowedComplexity();
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		if (page != null) {
			//page.setGui(this);
			//page.initGui();
		}
		hasChange = true;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		if (page != null) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			//page.drawBackground(mouseX, mouseY);
			widgetManager.drawWidgets(guiLeft, guiTop);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.texture(modularWdgets);
		drawTexturedModalRect(this.guiLeft + 180, this.guiTop + 2, 0, 0, 126, 158);
		drawTexturedModalRect(this.guiLeft + -130, this.guiTop + 77, 130, 0, 126, 83);
	}

	@Override
	public boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
		if (slot instanceof SlotModule) {
			if (!((SlotModule) slot).isActive()) {
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
		RenderUtil.texture(modularWdgets);
		
		if (slot instanceof SlotModule) {
			SlotModule slotModule = (SlotModule) slot;
			if(slotModule.isStorageSlot()){
				drawTexturedModalRect(slot.xPos - 1, slot.yPos - 1, 56, 238, 18, 18);
			}else {
				drawTexturedModalRect(slot.xPos - 1, slot.yPos - 1, 56, slotModule.isActive() ? 238 : 220, 18, 18);
			}
		}
		RenderUtil.texture(guiTexture);
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		if (slot instanceof SlotModule) {
			if (!((SlotModule) slot).isActive()) {
				return;
			}
		}
		super.drawSlot(slot);
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}

	@Nonnull
	public List<Rectangle> getExtraGuiAreas() {
		return Collections.singletonList(new Rectangle(guiLeft + 180, guiTop + 2, 126, 158));
	}

	public void setHasChange() {
		hasChange = true;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float p_73863_3_) {
		super.drawScreen(mouseX, mouseY, p_73863_3_);
		if (page != null) {
			//page.drawTooltips(mouseX, mouseY);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (page != null) {
			//page.handleMouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	public IStoragePage getPage() {
		return page;
	}
}