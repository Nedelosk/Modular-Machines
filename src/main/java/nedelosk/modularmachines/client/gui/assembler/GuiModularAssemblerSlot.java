package nedelosk.modularmachines.client.gui.assembler;

import nedelosk.modularmachines.api.basic.modular.module.ModuleEntry;
import nedelosk.modularmachines.client.gui.assembler.button.GuiButtonModularAssemblerSlotBack;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.inventory.ContainerModularAssemblerSlot;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssembler;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiModularAssemblerSlot extends GuiBase {
	
	public InventoryPlayer inventory;
	
	public GuiModularAssemblerSlot(TileBaseInventory tile, InventoryPlayer inventory, ModuleEntry entry) {
		super(tile, inventory);
		inventorySlots = new ContainerModularAssemblerSlot(tile, inventory, entry);
		this.inventory = inventory;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		buttonList.add(new GuiButtonModularAssemblerSlotBack(0, guiLeft + 78, guiTop + 70));
	}
	
	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		return "modular_assembler_slot";
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if(button instanceof GuiButtonModularAssemblerSlotBack)
		{
		PacketHandler.INSTANCE.sendToServer(new PacketModularAssembler((TileModularAssembler) tile));
		Minecraft.getMinecraft().displayGuiScreen(new GuiModularAssembler((TileBaseInventory) this.tile, inventory));
		}
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}

}
