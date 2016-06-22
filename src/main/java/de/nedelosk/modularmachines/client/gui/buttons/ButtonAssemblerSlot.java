package de.nedelosk.modularmachines.client.gui.buttons;

import java.awt.Rectangle;
import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.client.gui.Button;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModularAssemblerSyncSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ButtonAssemblerSlot extends Button<IAssembler> {

	public IAssemblerSlot slot;

	public ButtonAssemblerSlot(int ID, int posX, int posY, int width, int height, IAssemblerSlot slot) {
		super(ID, posX, posY, width, height, "");

		this.slot = slot;
	}

	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		slot.renderSlot(gui, new Rectangle(xPosition, yPosition, width, height));
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase<IAssembler> gui) {
		ArrayList<String> list = new ArrayList<String>();
		if(slot.getStack() != null){
			list.add(TextFormatting.ITALIC + slot.getStack().getDisplayName());
		}
		return list;
	}

	@Override
	public void onButtonClick(IGuiBase<IAssembler> gui) {
		EntityPlayer player = gui.getPlayer();
		ItemStack heldItem = player.inventory.getItemStack();

		if(heldItem != null){
			IModuleContainer container = ModuleManager.getContainerFromItem(heldItem);
			if(container != null && slot.canInsertItem(heldItem)){
				ItemStack newStack = heldItem.copy();
				newStack.stackSize = 1;
				gui.getHandler().setStack(slot.getIndex(), newStack);
				PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerSyncSlot((TileModularAssembler) gui.getHandler().getTile(), slot.getIndex(), slot.getStack().copy()));
				gui.getHandler().getTile().markDirty();
				heldItem.stackSize--;
				if(heldItem.stackSize == 0){
					player.inventory.setItemStack(null);
				}
			}
		}else{
			if(slot.getStack() != null){
				player.inventory.setItemStack(slot.getStack().copy());
				PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerSyncSlot((TileModularAssembler) gui.getHandler().getTile(), slot.getIndex(), slot.getStack()));
				gui.getHandler().setStack(slot.getIndex(), null);
			}
		}
		gui.getHandler().update(gui.getPlayer(), true);
	}
}
