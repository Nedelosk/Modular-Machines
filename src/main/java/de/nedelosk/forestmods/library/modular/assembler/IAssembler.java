package de.nedelosk.forestmods.library.modular.assembler;

import java.util.List;
import java.util.Map;

import de.nedelosk.forestmods.library.inventory.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IAssembler extends IGuiHandler {

	Map<Integer, IAssemblerGroup> getGroups();

	IAssemblerGroup addGroup(IAssemblerGroup group);

	IAssemblerGroup getCurrentGroup();

	IAssemblerSlot getSlot(int index);
	
	void setCurrentGroup(IAssemblerGroup currentGroup, EntityPlayer player);

	ItemStack getStack(int index);

	void setStack(int index, ItemStack stack);

	int getNextIndex(IAssemblerGroup group);

	int getMaxControllers();

	ItemStack getCasingStack();
	
	TileEntity getTile();
	
	void assemble();
	
	void reload();
	
	void updateControllerSlots();
	
	void updateActivitys(EntityPlayer player, boolean moveItem);
}
