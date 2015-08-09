package nedelosk.modularmachines.client.gui.machine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.api.modular.module.IModuleGui;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularMachine;
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

public class GuiModularMachine extends GuiBase implements INBTTagable {

	public InventoryPlayer inventory;
	
	public GuiModularMachine(TileModularMachine tile, InventoryPlayer inventory) {
		super(tile, inventory);
		this.inventory = inventory;
		if(tile.getModuleGuis() != null && tile.getModuleGui() != null)
		((IModuleGui)tile.getModuleGui().getModule()).addWidgets(this, ((TileModularMachine)this.tile).machine);
		ySize = ((Module)tile.getModuleGui().getModule()).getGuiTop(tile.machine);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		
	}

	@Override
	protected void renderProgressBar() {
		
		for(Slot slot : (ArrayList<Slot>)inventorySlots.inventorySlots)
		{
			if(slot.slotNumber >= 36)
			{
				RenderUtils.drawTexturedModalRect(guiLeft + slot.xDisplayPosition - 1, guiTop + slot.yDisplayPosition - 1, 1, 56, 238, 18, 18);
			}
		}
		
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int id = 0;
		for(int i = 0;i < ((TileModularMachine)tile).getModuleGuis().size();i++)
		{
			buttonList.add(new GuiButtonModularMachineBookmark(i, (i >= 7) ? guiLeft + 166 : guiLeft + -28, (i >= 7) ? guiTop + 8 + 21 * (i - 7) : guiTop + 8 + 21 * i, ((TileModularMachine)tile).getModuleGuis().get(i)));
			id++;
		}
		
		if(((TileModularMachine)tile).getModuleGuis() != null && ((TileModularMachine)tile).getModuleGui() != null)
			((IModuleGui)((TileModularMachine)tile).getModuleGui().getModule()).addButtons(this, ((TileModularMachine)this.tile).machine);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button instanceof GuiButtonModularMachineBookmark)
		{
			if(!((TileModularMachine)this.tile).page.equals(((GuiButtonModularMachineBookmark)button).stack.getModule().getName()))
			{
			((TileModularMachine)this.tile).page = ((GuiButtonModularMachineBookmark)button).stack.getModule().getName();
			EntityPlayer entityPlayer = inventory.player;
			if(entityPlayer.getExtendedProperties(ModularSaveModule.class.getName()) != null)
				if(((ModularSaveModule)entityPlayer.getExtendedProperties(ModularSaveModule.class.getName())).getSave(tile.xCoord, tile.yCoord, tile.zCoord) != null)
					((ModularSaveModule)entityPlayer.getExtendedProperties(ModularSaveModule.class.getName())).getSave(tile.xCoord, tile.yCoord, tile.zCoord).page = ((TileModularMachine)tile).page;
				else
					((ModularSaveModule)entityPlayer.getExtendedProperties(ModularSaveModule.class.getName())).saver.add(new ModularTileEntitySave(((TileModularMachine)tile).page, tile.xCoord, tile.yCoord, tile.zCoord));
			else
				entityPlayer.registerExtendedProperties(ModularSaveModule.class.getName(), new ModularSaveModule(new ModularTileEntitySave(((TileModularMachine)tile).page, tile.xCoord, tile.yCoord, tile.zCoord)));
			PacketHandler.INSTANCE.sendToServer(new PacketModularMachine((TileModularMachine) this.tile, ((GuiButtonModularMachineBookmark) button).stack.getModule().getName()));
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
		if(((TileModularMachine)tile).getModuleGuis() != null && ((Module)((TileModularMachine)tile).getModuleGui().getModule()).getCustomGui(((TileModularMachine)tile).machine) != null)
			RenderUtils.bindTexture(((Module)((TileModularMachine)tile).getModuleGui().getModule()).getCustomGui(((TileModularMachine)tile).machine));
		else
			RenderUtils.bindTexture(guiTexture);	    
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    
        RenderUtils.bindTexture(new ResourceLocation(getModName(), "textures/gui/inventory_player.png"));
	    drawTexturedModalRect(this.guiLeft + 7, this.guiTop + ySize - 83, 0, 0, 162, 76);
		
		RenderUtils.bindTexture(guiTexture);
	    renderProgressBar();
        
        widgetManager.drawWidgets();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
	}

}
