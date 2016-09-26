package de.nedelosk.modularmachines.common.modular;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerEntity;
import de.nedelosk.modularmachines.api.modular.handlers.ModularHandler;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class ModularHandlerEntity extends ModularHandler implements IModularHandlerEntity<NBTTagCompound> {

	protected final Entity entity;

	public ModularHandlerEntity(Entity entity, List<IStoragePosition> positions) {
		super(entity.getEntityWorld(), positions);

		this.entity = entity;
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void markDirty() {
	}
}
