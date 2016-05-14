package de.nedelosk.forestmods.client.gui.buttons;

import java.awt.Rectangle;
import java.util.ArrayList;

import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerCreateGroup;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerSelectGroup;
import de.nedelosk.forestmods.library.gui.Button;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleController;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ButtonAssemblerGroup extends Button<IAssembler> {

	public int groupID;
	public IAssemblerGroup group;

	public ButtonAssemblerGroup(int ID, int posX, int posY, IAssemblerGroup group, int groupID) {
		super(ID, posX, posY, 18, 18, "");

		this.group = group;
		this.groupID = groupID;
	}

	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		if(group != null) {
			group.renderGroup(gui, new Rectangle(xPosition, yPosition, width, height));
		}
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase<IAssembler> gui) {
		ArrayList<String> list = new ArrayList<String>();
		if(group != null && group.getControllerSlot() != null && group.getControllerSlot().getStack() != null){
			list.add(EnumChatFormatting.ITALIC + group.getControllerSlot().getStack().getDisplayName());
		}
		return list;
	}

	@Override
	public void onButtonClick(IGuiBase<IAssembler> gui) {
		EntityPlayer player = gui.getPlayer();
		ItemStack heldItem = player.inventory.getItemStack();

		if(group == null){
			if(gui.getHandler().getCasingStack() != null){
				if(heldItem != null){
					IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(heldItem);
					if(container != null && IModuleController.class.isAssignableFrom(container.getModuleClass())){			
						IModuleController module = ModuleManager.moduleRegistry.getFakeModule(container);
						group = module.createGroup(gui.getHandler(), heldItem, groupID);
						if(group != null){
							ItemStack newStack = heldItem.copy();
							newStack.stackSize = 1;
							gui.getHandler().setStack(group.getControllerSlot().getIndex(), newStack);
							PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerCreateGroup((TileModularAssembler) gui.getHandler().getTile(), groupID, newStack));

							heldItem.stackSize--;
							if(heldItem.stackSize == 0){
								player.inventory.setItemStack(null);
							}
						}
					}
				}

				gui.getHandler().update(gui.getPlayer(), true);
			}
		}else{
			if(gui.getHandler().getCurrentGroup() == null || gui.getHandler().getCurrentGroup().getGroupID() != groupID){
				gui.getHandler().setCurrentGroup(group, gui.getPlayer());
				PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerSelectGroup((TileModularAssembler) gui.getHandler().getTile(), groupID));
			}
		}
	}


}