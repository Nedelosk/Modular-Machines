package nedelosk.modularmachines.client.gui.assembler;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.api.IModularAssembler;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.client.gui.assembler.button.GuiButtonModularAssemblerBookmark;
import nedelosk.modularmachines.client.gui.assembler.button.GuiButtonModularAssemblerBuildMachine;
import nedelosk.modularmachines.client.gui.assembler.button.GuiButtonModularAssemblerSlot;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssembler;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssemblerBookmark;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssemblerBuildMachine;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetEnergyBar;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiModularAssembler extends GuiBase {

	protected ResourceLocation guiTextureOverlay = RenderUtils.getResourceLocation(getModName(), "modular_assembler_overlay", "gui");
	protected ResourceLocation guiTextureOverlay2 = RenderUtils.getResourceLocation(getModName(), "modular_assembler_overlay_2", "gui");
	
	public InventoryPlayer inventory;
	public int guiX;
	public int guiY;
	
	public GuiModularAssembler(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
		xSize = 314;
		ySize = 228;
		this.inventory = inventory;
		widgetManager.add(new WidgetEnergyBar(((TileModularAssembler)tile).storage, 278, 57));
		
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
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
		
        widgetManager.drawWidgets();
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
		for(ModuleEntry entry : ((TileModularAssembler)tile).moduleEntrys.get(((TileModularAssembler)tile).page))
		{
			if(entry != null)
					buttonList.add(new GuiButtonModularAssemblerSlot(id, entry.x + guiLeft, entry.y + guiTop, entry, (IModularAssembler) tile));
			id++;
		}
		buttonList.add(new GuiButtonModularAssemblerBuildMachine(id, guiLeft + 261, guiTop + 35));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if(button instanceof GuiButtonModularAssemblerBookmark)
		{
			((TileModularAssembler)tile).page = ((GuiButtonModularAssemblerBookmark) button).page;
			PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerBookmark((TileModularAssembler) tile, ((GuiButtonModularAssemblerBookmark) button).page));
			Minecraft.getMinecraft().displayGuiScreen(new GuiModularAssembler((TileBaseInventory) tile, inventory));
			//updateButtons();
		}
		else if(button instanceof GuiButtonModularAssemblerSlot)
		{
			GuiButtonModularAssemblerSlot slot = (GuiButtonModularAssemblerSlot) button;
			if(slot.entry.isActivate)
			{
				if(((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())) != null)
					((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).entry = slot.entry;
				else
					inventory.player.registerExtendedProperties(ModularSaveModule.class.getName(), new ModularSaveModule( slot.entry));
			PacketHandler.INSTANCE.sendToServer(new PacketModularAssembler((TileModularAssembler) tile, slot.entry));
			}
		}
		else if(button instanceof GuiButtonModularAssemblerBuildMachine)
		{
			PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerBuildMachine((TileModularAssembler) tile));
		}
	}
	
	@Override
	public void drawScreen(int mx, int my, float p_73863_3_) {
		super.drawScreen(mx, my, p_73863_3_);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting)
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		for(GuiButton button : (ArrayList<GuiButton>)buttonList){
			if(button != null && button instanceof GuiButtonModularAssemblerSlot)
				((GuiButtonModularAssemblerSlot)button).renderTooltip(mx, my);
		}
		RenderUtils.bindTexture(guiTextureOverlay2);
		for(ModuleEntry entry : ((TileModularAssembler)tile).moduleEntrys.get(((TileModularAssembler)tile).page))
		{
			ArrayList<ModuleEntry> childs = new ArrayList<ModuleEntry>();
			for(ModuleEntry entryChild : ((TileModularAssembler)tile).moduleEntrys.get(((TileModularAssembler)tile).page))
			{
				if(entryChild.parent != null)
					if(entryChild.parent.equals(entry))
						childs.add(entryChild);
			}
			
			for(ModuleEntry child : childs)
			{
				if(child.x + 36 == entry.x)
					RenderUtils.drawTexturedModalRect(entry.x + guiLeft - 18, entry.y + guiTop + 5, 1, !entry.isActivate ? 0 : 54 , 23 ,18 , 8);
				else if(child.x - 36 == entry.x)
					RenderUtils.drawTexturedModalRect(entry.x + guiLeft + 18, entry.y + guiTop + 5, 1, !entry.isActivate ? 36 : 90 , 23 ,18 , 8);
				else if(child.y - 36 == entry.y)
					RenderUtils.drawTexturedModalRect(entry.x + guiLeft + 5, entry.y + guiTop + 18, 1,!entry.isActivate ? 23 : 77 , 36 ,8 , 18);
				else if(child.y + 36 == entry.y)
					RenderUtils.drawTexturedModalRect(entry.x + guiLeft + 5, entry.y + guiTop - 18, 1,!entry.isActivate ? 23 : 77 , 0 ,8 , 18);
			}
		}
		if(!lighting)
			net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
	}

	@Override
	protected String getGuiName() {
		return "modular_assembler";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}
    
    public int getGuiX() {
		return guiX;
	}
    
    public int getGuiY() {
		return guiY;
	}

}
