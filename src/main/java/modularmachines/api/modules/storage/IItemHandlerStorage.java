package modularmachines.api.modules.storage;

import javax.annotation.Nullable;

import net.minecraftforge.items.IItemHandlerModifiable;

import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modules.position.IStoragePosition;

public interface IItemHandlerStorage extends IItemHandlerModifiable {

	void setAssembler(@Nullable IModularAssembler assembler);

	void setPosition(@Nullable IStoragePosition position);

	@Nullable
	IModularAssembler getAssembler();

	@Nullable
	IStoragePosition getPosition();
}
