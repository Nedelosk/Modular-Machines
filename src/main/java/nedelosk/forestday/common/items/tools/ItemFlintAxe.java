package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.api.Tabs;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemFlintAxe extends ItemAxe {

	public ItemFlintAxe(ToolMaterial mat) {
		super(mat);
		this.setCreativeTab(Tabs.tabForestdayItems);
		this.setTextureName("forestday:tools/axe_flint");
		setUnlocalizedName(NRegistry.setUnlocalizedItemName("axe.flint", "fd"));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return NRegistry.setUnlocalizedItemName("axe.flint", "fd");
	}

}
