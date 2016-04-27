package de.nedelosk.forestmods.library.transport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.nedelosk.forestmods.library.transport.node.ITransportNode;
import de.nedelosk.forestmods.library.utils.BlockPos;
import net.minecraft.world.World;

public interface ITransportSystem {

	World getWorldObj();

	boolean isEmpty();

	boolean shouldConsume(ITransportSystem system);

	void attachPart(ITransportPart transportPart);

	void detachPart(ITransportPart part, boolean chunkUnloading);

	void auditParts();

	String getPartsListString();

	HashSet<ITransportPart> getParts();

	HashSet<ITransportNode> getNodes();

	Set<ITransportPart> checkForDisconnections();

	Collection<? extends ITransportPart> detachAllBlocks();

	BlockPos getReferenceCoord();
}
