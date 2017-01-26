package modularmachines.api.modules.assemblers;

import java.util.Collection;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.Assembler;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssemblerStorage extends SlotItemHandler {

	protected final IStoragePage page;
	protected final IStoragePosition position;
	protected final Assembler assembler;
	protected final Container container;

	public SlotAssemblerStorage(Assembler assembler, Container container, int xPosition, int yPosition, IStoragePage page) {
		super(page.getItemHandler(), 0, xPosition, yPosition);
		this.assembler = assembler;
		this.container = container;
		this.position = page.getPosition();
		this.page = page;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (page != null) {
			page.onSlotChanged(container, assembler);
		}
		assembler.onStorageSlotChange();
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		IModuleContainer itemContainer = ModuleHelper.getContainerFromItem(stack);
		if (itemContainer == null) {
			return false;
		}
		Boolean isValid = null;
		DATAS: for (ModuleData data : itemContainer.getDatas()) {
			if (data.isStorageAt(position, this)) {
				if (data.isValid(assembler, position, stack, null, this)) {
					Collection<IStoragePosition> childPositions = data.getChildPositions(position);
					for(IStoragePosition position : childPositions){
						if(!assembler.getPage(position).isEmpty()){
							continue DATAS;
						}
					}
					return true;
				}
			}
		}
		if (isValid == null) {
			isValid = false;
		}
		return isValid;
	}
}
