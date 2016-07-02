package de.nedelosk.modularmachines.api.modular;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public interface IAssemblerSlot {

	Slot getSlot();

	boolean isActive();

	void setUpdated(boolean isUpdated);

	boolean isUpdated();

	IAssemblerSlot[] getSiblings();

	List<IAssemblerSlot> getParents();

	Class<? extends IModule> getModuleClass();

	EntityPlayer getPlayer();

	Container getContainer();

	boolean isController();

	void addParent(IAssemblerSlot slot);

	void changeStatus(boolean isActive);
}
