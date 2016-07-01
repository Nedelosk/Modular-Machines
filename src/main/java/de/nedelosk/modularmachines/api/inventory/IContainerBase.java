package de.nedelosk.modularmachines.api.inventory;

public interface IContainerBase<T extends IGuiHandler> {

	T getHandler();
}
