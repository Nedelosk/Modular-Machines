package de.nedelosk.modularmachines.api.transport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.nedelosk.modularmachines.api.transport.node.ITransportNode;
import de.nedelosk.modularmachines.common.utils.AdvancedBlockPos;
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

	AdvancedBlockPos getReferenceCoord();
}
