package modularmachines.api.modules.storage;

import javax.annotation.Nullable;

import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modules.position.IStoragePosition;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IItemHandlerStorage extends IItemHandlerModifiable {

	void setAssembler(@Nullable IModularAssembler assembler);

	void setPosition(@Nullable IStoragePosition position);

	@Nullable
	IModularAssembler getAssembler();

	@Nullable
	IStoragePosition getPosition();
}
