package de.nedelosk.forestmods.api.transport.node;

public interface INodeType {

	boolean canHandelItems();

	boolean canHandelFluids();

	boolean canHandelEnergy();

	boolean handleInput(ITransportNode node, Object input);

	boolean handleoutput(ITransportNode node, Object output);
}
