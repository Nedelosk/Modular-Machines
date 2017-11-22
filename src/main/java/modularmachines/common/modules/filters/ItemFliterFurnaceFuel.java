package modularmachines.common.modules.filters;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import modularmachines.api.modules.IModule;
import modularmachines.common.inventory.IContentFilter;

public class ItemFliterFurnaceFuel implements IContentFilter<ItemStack, IModule> {
	
	public static final ItemFliterFurnaceFuel INSTANCE = new ItemFliterFurnaceFuel();
	
	private ItemFliterFurnaceFuel() {
	}
	
	@Override
	public boolean isValid(int index, ItemStack content, IModule module) {
		if (content == null) {
			return false;
		}
		return TileEntityFurnace.getItemBurnTime(content) > 0;
	}
}
