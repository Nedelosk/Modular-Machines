package nedelosk.modularmachines.client.gui.machine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGui;
import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGuiWithButtons;
import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.basic.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModular;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.modularmachines.common.network.packets.saver.ModularTileEntitySave;
import nedelosk.nedeloskcore.api.INBTTagable;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiModularMachine extends GuiBase<TileModular> implements INBTTagable {

	public InventoryPlayer inventory;
	
	public GuiModularMachine(TileModular tile, InventoryPlayer inventory) {
		super(tile, inventory);
		this.inventory = inventory;
		widgetManager = new WidgetManagerModular(this);
		if(tile.getModular().getGuiManager().getModuleWithGuis() != null && tile.getModular().getGuiManager().getModuleWithGui().getModule() != null && tile.getModular().getGuiManager().getModuleWithGui().getModule() instanceof IModuleGuiWithWidgets)
			((IModuleGuiWithWidgets)tile.getModular().getGuiManager().getModuleWithGui().getModule()).addWidgets(this, tile.modular);
		ySize = ((IModuleGui)tile.getModular().getGuiManager().getModuleWithGui().getModule()).getGuiTop(tile.modular);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		((IModuleGui)tile.getModular().getGuiManager().getModuleWithGui().getModule()).renderString(fontRenderer, guiLeft, guiTop, x, y);
	}

	@Override
	protected void renderProgressBar() {
		if(tile.getModular().getGuiManager().getModuleWithGui().getModule() instanceof IModuleInventory){
			for(Slot slot : (ArrayList<Slot>)inventorySlots.inventorySlots)
			{
				if(slot.slotNumber < ((IModuleInventory)tile.getModular().getGuiManager().getModuleWithGui().getModule()).getSizeInventory())
				{
					RenderUtils.drawTexturedModalRect(guiLeft + slot.xDisplayPosition - 1, guiTop + slot.yDisplayPosition - 1, 1, 56, 238, 18, 18);
				}
			}
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int id = 0;
		for(int i = 0;i < tile.getModular().getGuiManager().getModuleWithGuis().size();i++)
		{
			buttonList.add(new GuiBookmarkModular(i, (i >= 7) ? guiLeft + 166 : guiLeft + -28, (i >= 7) ? guiTop + 8 + 22 * (i - 7) : guiTop + 8 + 22 * i, tile.getModular().getGuiManager().getModuleWithGuis().get(i), i >= 7));
			id++;
		}
		
		if(tile.getModular().getGuiManager().getModuleWithGuis() != null && tile.getModular().getGuiManager().getModuleWithGui().getModule() != null && tile.getModular().getGuiManager().getModuleWithGui().getModule() instanceof IModuleGuiWithButtons)
			((IModuleGuiWithButtons)tile.getModular().getGuiManager().getModuleWithGui().getModule()).addButtons(this, this.tile.modular);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button instanceof GuiBookmarkModular)
		{
			if(!this.tile.getModular().getGuiManager().getPage().equals(((GuiBookmarkModular)button).stack.getModule().getName()))
			{
			this.tile.getModular().getGuiManager().setPage(((GuiBookmarkModular)button).stack.getModule().getName());
			EntityPlayer entityPlayer = inventory.player;
			if(entityPlayer.getExtendedProperties(ModularSaveModule.class.getName()) != null)
				if(((ModularSaveModule)entityPlayer.getExtendedProperties(ModularSaveModule.class.getName())).getSave(tile.xCoord, tile.yCoord, tile.zCoord) != null)
					((ModularSaveModule)entityPlayer.getExtendedProperties(ModularSaveModule.class.getName())).getSave(tile.xCoord, tile.yCoord, tile.zCoord).page = tile.getModular().getGuiManager().getPage();
				else
					((ModularSaveModule)entityPlayer.getExtendedProperties(ModularSaveModule.class.getName())).saver.add(new ModularTileEntitySave(tile.getModular().getGuiManager().getPage(), tile.xCoord, tile.yCoord, tile.zCoord));
			else
				entityPlayer.registerExtendedProperties(ModularSaveModule.class.getName(), new ModularSaveModule(new ModularTileEntitySave(tile.getModular().getGuiManager().getPage(), tile.xCoord, tile.yCoord, tile.zCoord)));
			PacketHandler.INSTANCE.sendToServer(new PacketModular(this.tile, ((GuiBookmarkModular) button).stack.getModule().getName()));
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
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(tile.getModular().getGuiManager().getModuleWithGuis() != null && ((IModuleGui)tile.getModular().getGuiManager().getModuleWithGui().getModule()).getCustomGui(tile.modular) != null)
			RenderUtils.bindTexture(((IModuleGui)tile.getModular().getGuiManager().getModuleWithGui().getModule()).getCustomGui(tile.modular));
		else
			RenderUtils.bindTexture(guiTexture);	    
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    
        RenderUtils.bindTexture(new ResourceLocation(getModName(), "textures/gui/inventory_player.png"));
	    drawTexturedModalRect(this.guiLeft + 7, this.guiTop + ySize - 83, 7, 83, 162, 76);
		
		RenderUtils.bindTexture(guiTexture);
	    renderProgressBar();
		((IModuleGui)tile.getModular().getGuiManager().getModuleWithGui().getModule()).updateGui(this, guiLeft, guiTop);
        widgetManager.drawWidgets();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
	}

}
