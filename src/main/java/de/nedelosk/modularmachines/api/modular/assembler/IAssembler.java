package de.nedelosk.modularmachines.api.modular.assembler;

import java.util.Map;

import de.nedelosk.modularmachines.api.inventory.IGuiHandler;
import de.nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IAssembler extends IGuiHandler {

	Map<Integer, IAssemblerGroup> getGroups();

	IAssemblerGroup addGroup(IAssemblerGroup group);

	IAssemblerGroup getCurrentGroup();

	void setCurrentGroup(IAssemblerGroup currentGroup, EntityPlayer player);

	ItemStack getStack(int index);

	void setStack(int index, ItemStack stack);

	int getNextIndex();

	int getMaxControllers();

	ItemStack getCasingStack();

	TileEntity getTile();

	IModular assemble(boolean withItem);

	void reload();

	void updateControllerSlots(EntityPlayer player);

	void update(EntityPlayer player, boolean moveItem);
}
