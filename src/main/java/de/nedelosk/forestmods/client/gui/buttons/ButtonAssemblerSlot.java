package de.nedelosk.forestmods.client.gui.buttons;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerCreateGroup;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerSyncSlot;
import de.nedelosk.forestmods.library.gui.Button;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.gui.Widget;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleController;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

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
			list.add(EnumChatFormatting.ITALIC + slot.getStack().getDisplayName());
		}
		return list;
	}
	
	@Override
	public void onButtonClick(IGuiBase<IAssembler> gui) {
		EntityPlayer player = gui.getPlayer();
		ItemStack heldItem = player.inventory.getItemStack();

		if(heldItem != null){
			IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(heldItem);
			if(container != null && slot.getModuleClass().isAssignableFrom(container.getModuleClass())){
				ItemStack newStack = heldItem.copy();
				newStack.stackSize = 1;
				gui.getHandler().setStack(slot.getIndex(), newStack);
				PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerSyncSlot((TileModularAssembler) gui.getHandler().getTile(), slot.getIndex(), slot.getStack().copy()));
				heldItem.stackSize--;
				if(heldItem.stackSize == 0){
					player.inventory.setItemStack(null);
				}
				gui.getHandler().getTile().markDirty();
			}
		}else{
			if(slot.getStack() != null){
				player.inventory.setItemStack(slot.getStack().copy());
				PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerSyncSlot((TileModularAssembler) gui.getHandler().getTile(), slot.getIndex(), slot.getStack()));
				gui.getHandler().setStack(slot.getIndex(), null);
			}
		}
		gui.getHandler().updateActivitys(gui.getPlayer(), true);
	}
}
