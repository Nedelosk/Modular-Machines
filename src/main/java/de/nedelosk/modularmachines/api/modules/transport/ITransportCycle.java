package de.nedelosk.modularmachines.api.modules.transport;

public interface ITransportCycle<H> {

	int getComplexity();

	int getTime();

	void work();

	boolean canWork();

	int getProperty();

	ITransportHandlerWrapper<H> getStartHandler();

	int[] getStartSlots();

	ITransportHandlerWrapper<H> getEndHandler();

	int[] getEndSlots();
}
