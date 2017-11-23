package modularmachines.common.modules.filters;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemFliterFurnaceFuel implements Predicate<ItemStack> {
	
	public static final ItemFliterFurnaceFuel INSTANCE = new ItemFliterFurnaceFuel();
	
	private ItemFliterFurnaceFuel() {
	}
	
	@Override
	public boolean test(ItemStack itemStack) {
		return TileEntityFurnace.getItemBurnTime(itemStack) > 0;
	}
}
