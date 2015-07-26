package nedelosk.nedeloskcore.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.common.inventory.slots.SlotModule;
import nedelosk.nedeloskcore.api.machines.Button;
import nedelosk.nedeloskcore.api.machines.IButtonManager;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.api.machines.IWidgetManager;
import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.common.inventory.slots.SlotPlan;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public abstract class GuiBase extends GuiContainer implements IGuiBase {

	protected ResourceLocation guiTexture = RenderUtils.getResourceLocation(getModName(), getGuiName(), "gui");
	protected TileBase tile;
	protected ButtonManager buttonManager;
	protected WidgetManager widgetManager;
	
	public GuiBase(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile.getContainer(inventory));
		this.tile = tile;
		widgetManager = new WidgetManager(this);
		buttonManager = new ButtonManager(this);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.addAll(buttonManager.getButtons());
	}
	
	public IButtonManager getButtonManager() {
		return buttonManager;
	}
	
	public IWidgetManager getWidgetManager() {
		return widgetManager;
	}
	
	public TileBase getTile() {
		return tile;
	}
	
	@Override
	protected void mouseClicked(int mouseX,  int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		widgetManager.handleMouseClicked(mouseX, mouseY, mouseButton);
	}
	
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {

        //fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        
        renderStrings(fontRendererObj, param1, param2);
        widgetManager.drawTooltip(param1, param2);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.bindTexture(guiTexture);	    
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	    renderProgressBar();
        
        widgetManager.drawWidgets();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button instanceof Button) 
			((Button) button).onButtonClick(this);
	}
	
	protected abstract void renderStrings(FontRenderer fontRenderer, int x, int y);
	
	protected abstract void renderProgressBar();
    
    protected abstract String getGuiName();
    
    protected abstract String getModName();

	public static RenderItem getItemRenderer() {
		return itemRender;
	}

	public void setZLevel(float zLevel) {
		this.zLevel = zLevel;
	}

	public int getGuiLeft() {
		return this.guiLeft;
	}
	
	public int getGuiTop() {
		return this.guiTop;
	}

}
