package de.nedelosk.forestmods.api.transport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.nedelosk.forestcore.utils.BlockPos;
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

	Set<ITransportPart> checkForDisconnections();

	Collection<? extends ITransportPart> detachAllBlocks();

	BlockPos getReferenceCoord();
}
