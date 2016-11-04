package modularmachines.api.modular;

import javax.annotation.Nullable;

import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.storage.IItemHandlerStorage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AssemblerItemHandler extends ItemStackHandler implements IItemHandlerStorage {

	protected IModularAssembler assembler;
	protected IStoragePosition position;

	public AssemblerItemHandler(ItemStack[] stacks, @Nullable IModularAssembler assembler, @Nullable IStoragePosition position) {
		super(stacks);
		this.assembler = assembler;
		this.position = position;
	}

	public AssemblerItemHandler(int size, @Nullable IModularAssembler assembler) {
		this(size, assembler, null);
	}

	public AssemblerItemHandler(int size, @Nullable IModularAssembler assembler, @Nullable IStoragePosition position) {
		super(size);
		this.assembler = assembler;
		this.position = position;
	}

	public ItemStack[] getStacks() {
		return stacks;
	}

	@Override
	public void setAssembler(IModularAssembler assembler) {
		this.assembler = assembler;
	}

	@Override
	public void setPosition(IStoragePosition position) {
		this.position = position;
	}

	@Override
	public IStoragePosition getPosition() {
		return position;
	}

	@Override
	public IModularAssembler getAssembler() {
		return assembler;
	}

	@Override
	protected int getStackLimit(int slot, ItemStack stack) {
		return 1;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if (assembler != null) {
			assembler.updatePages(position);
		}
	}
}
