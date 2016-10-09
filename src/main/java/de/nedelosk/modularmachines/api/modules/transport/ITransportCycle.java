package de.nedelosk.modularmachines.api.modules.transport;

public interface ITransportCycle<H> {

	int getTime();

	void work();

	void canWork();

	H getHandler();

}
