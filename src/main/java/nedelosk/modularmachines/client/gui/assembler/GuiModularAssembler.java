package nedelosk.modularmachines.client.gui.assembler;

import java.util.ArrayList;

import nedelosk.forestbotany.common.blocks.tile.TileInfuserBase;
import nedelosk.forestday.client.machines.base.gui.widget.WidgetFuelBar;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.machines.base.fluid.heater.TileFluidHeater;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import nedelosk.modularmachines.common.inventory.slots.SlotModule;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssembler;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssemblerBookmark;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssemblerBuildMachine;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetEnergyBar;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiModularAssembler extends GuiBase {

	protected ResourceLocation guiTextureOverlay = RenderUtils.getResourceLocation(getModName(), "modular_assembler_overlay", "gui");
	protected ResourceLocation guiTextureOverlay2 = RenderUtils.getResourceLocation(getModName(), "modular_assembler_overlay_2", "gui");
	//protected WidgetEnergyBar energyBar = new WidgetEnergyBar(((TileModularAssenbler)tile).getStorage(), 279, 60);
	
	public InventoryPlayer inventory;
	public int guiX;
	public int guiY;
	
	public GuiModularAssembler(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
		xSize = 314;
		ySize = 228;
		this.inventory = inventory;
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
        //if(energyBar != null)
        //	energyBar.draw(this);
        //if (energyBar != null)
        //     if (func_146978_c(energyBar.posX, energyBar.posY, 12, 69, x, y)) {
        //    	energyBar.drawTooltip(x - this.guiLeft, y
        //                - this.guiTop);
        //    }
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		RenderUtils.bindTexture(guiTexture);	    
	    drawTexturedModalRect(this.guiLeft + 12, this.guiTop + 12, 0, 0, 232, 204);
		
		RenderUtils.bindTexture(guiTextureOverlay);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 228);
		RenderUtils.bindTexture(guiTextureOverlay2);
		drawTexturedModalRect(guiLeft + 256, guiTop + 25, 0, 122, 58, 134);
		this.guiX = guiLeft;
		this.guiY = guiTop;
	}
	
	@Override
	protected void renderProgressBar() {
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int id = 0;
		for(int i = 0;i < ModularMachinesApi.moduleEntrys.size();i++)
		{
			buttonList.add(new GuiButtonModularAssemblerBookmark(i, guiLeft + -28, guiTop + 8 + 21 * i, (String) ModularMachinesApi.moduleEntrys.keySet().toArray()[i]));
			id++;
		}
		for(ModuleEntry entry : ((TileModularAssenbler)tile).moduleEntrys.get(((TileModularAssenbler)tile).page))
		{
			if(entry != null)
				if(entry.isChain)
					buttonList.add(new GuiButtonModularAssemblerSlot(5 + entry.ID, entry.x + guiLeft, entry.y + guiTop, entry.ID, entry.page, this, entry.parent != null ? entry.parent.ID : 0, ((entry.parent != null) ? entry.parent.page : (String) ModularMachinesApi.moduleEntrys.keySet().toArray()[0]), entry.parent != null, entry.rendererSides, entry.isChain, entry.chainTile));
				else
					buttonList.add(new GuiButtonModularAssemblerSlot(5 + entry.ID, entry.x + guiLeft, entry.y + guiTop, entry.ID, entry.page, this, entry.parent != null ? entry.parent.ID : 0, ((entry.parent) != null ? entry.parent.page : (String) ModularMachinesApi.moduleEntrys.keySet().toArray()[0]), entry.parent != null, entry.rendererSides));
			id++;
		}
		buttonList.add(new GuiButtonModularAssemblerBuildMachine(id, guiLeft + 261, guiTop + 35));
		updateButtons();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if(button instanceof GuiButtonModularAssemblerBookmark)
		{
			((TileModularAssenbler)tile).page = ((GuiButtonModularAssemblerBookmark) button).page;
			PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerBookmark((TileModularAssenbler) tile, ((GuiButtonModularAssemblerBookmark) button).page));
			Minecraft.getMinecraft().displayGuiScreen(new GuiModularAssembler((TileBaseInventory) tile, inventory));
			//updateButtons();
		}
		else if(button instanceof GuiButtonModularAssemblerSlot)
		{
			if(((GuiButtonModularAssemblerSlot) button).active)
			{
				if(((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())) != null)
					((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).entry = ((TileModularAssenbler)tile).getModuleEntry(((TileModularAssenbler)tile).page, (((GuiButtonModularAssemblerSlot) button).entryID));
				else
					inventory.player.registerExtendedProperties(ModularSaveModule.class.getName(), new ModularSaveModule(((TileModularAssenbler)tile).getModuleEntry(((TileModularAssenbler)tile).page, (((GuiButtonModularAssemblerSlot) button).entryID))));
			PacketHandler.INSTANCE.sendToServer(new PacketModularAssembler((TileModularAssenbler) tile, ((GuiButtonModularAssemblerSlot) button).entryID, false));
			Minecraft.getMinecraft().displayGuiScreen(new GuiModularAssemblerSlot((TileBaseInventory) this.tile, inventory, ((TileModularAssenbler)tile).getModuleEntry(((TileModularAssenbler)tile).page, (((GuiButtonModularAssemblerSlot) button).entryID))));
			}
			updateButtons();
		}
		else if(button instanceof GuiButtonModularAssemblerBuildMachine)
		{
			PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerBuildMachine((TileModularAssenbler) tile));
		}
	}
	
    
	public void closeModule(GuiButtonModularAssemblerSlot buttonBase)
	{
		buttonBase.active = false;
	}
	
	public void closeModules(GuiButtonModularAssemblerSlot buttonBase)
	{
		for(GuiButton button : (ArrayList<GuiButton>)buttonList)
		{
			if(button instanceof GuiButtonModularAssemblerSlot)
				if(((GuiButtonModularAssemblerSlot)button).hasParent && ((GuiButtonModularAssemblerSlot)button).parentID  == buttonBase.entryID && ((GuiButtonModularAssemblerSlot)button).parentPage == ((GuiButtonModularAssemblerSlot)buttonBase).page)
				{
					closeModule(((GuiButtonModularAssemblerSlot)button));
					//closeModules(((GuiButtonModularAssemblerSlot)button));
				}
		}
	}
	
	public void activeModule(GuiButtonModularAssemblerSlot buttonBase)
	{
		((TileModularAssenbler)tile).getModuleEntry(((TileModularAssenbler)tile).page, ((GuiButtonModularAssemblerSlot) buttonBase).entryID).setAssembler((TileModularAssenbler) tile);
		if(buttonBase.isChain && ((TileModularAssenbler)tile).getModuleEntry(((TileModularAssenbler)tile).page, ((GuiButtonModularAssemblerSlot) buttonBase).entryID).getTier() > 0 || !buttonBase.isChain)
			buttonBase.active = true;

	}
	
	public void activeModules(GuiButtonModularAssemblerSlot buttonBase)
	{
		for(GuiButton button : (ArrayList<GuiButton>)buttonList)
		{
			if(button instanceof GuiButtonModularAssemblerSlot)
				if(((GuiButtonModularAssemblerSlot)button).hasParent && ((GuiButtonModularAssemblerSlot)button).parentID  == buttonBase.entryID)
					activeModule(((GuiButtonModularAssemblerSlot)button));
		}

	}

	@Override
	protected String getGuiName() {
		return "modular_assembler";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}
	
	public void updateButtons()
	{
		for(GuiButton button : (ArrayList<GuiButton>)buttonList)
		{
			if(button != null && button instanceof GuiButtonModularAssemblerSlot)
			{
				if(((GuiButtonModularAssemblerSlot)button).entryID == 0)
					if(((GuiButtonModularAssemblerSlot)button).parentPage == ((GuiButtonModularAssemblerSlot)button).page)
						activeModule(((GuiButtonModularAssemblerSlot)button));
					else if(((TileModularAssenbler)tile).getStackInSlot(((GuiButtonModularAssemblerSlot)button).parentPage, ((GuiButtonModularAssemblerSlot)button).parentID) != null)
						activeModule(((GuiButtonModularAssemblerSlot)button));
				if(((GuiButtonModularAssemblerSlot)button).active)
					if(((TileModularAssenbler)tile).getStackInSlot(((GuiButtonModularAssemblerSlot)button).page, ((GuiButtonModularAssemblerSlot)button).entryID) != null)
						activeModules(((GuiButtonModularAssemblerSlot)button));
					else
						closeModules(((GuiButtonModularAssemblerSlot)button));
			}
				
		}
	}
    
    public int getGuiX() {
		return guiX;
	}
    
    public int getGuiY() {
		return guiY;
	}

}
