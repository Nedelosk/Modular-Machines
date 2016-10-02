package de.nedelosk.modularmachines.api.gui;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;

public interface IContainerBase<T extends IGuiHandler> {

	T getHandler();

	List<Slot> getSlots();

	EntityPlayer getPlayer();

	List<IContainerListener> getListeners();
}
