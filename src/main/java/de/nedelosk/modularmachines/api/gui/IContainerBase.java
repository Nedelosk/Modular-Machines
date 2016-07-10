package de.nedelosk.modularmachines.api.gui;

public interface IContainerBase<T extends IGuiHandler> {

	T getHandler();
}
