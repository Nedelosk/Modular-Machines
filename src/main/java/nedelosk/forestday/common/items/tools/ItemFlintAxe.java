package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.api.Tabs;
import nedelosk.nedeloskcore.common.core.registry.NCRegistry;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemFlintAxe extends ItemAxe {

	public ItemFlintAxe(ToolMaterial mat) {
		super(mat);
		this.setCreativeTab(Tabs.tabForestday);
		this.setTextureName("forestday:tools/axe_flint");
		setUnlocalizedName(NCRegistry.setUnlocalizedItemName("axe.flint", "fd"));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return NCRegistry.setUnlocalizedItemName("axe.flint", "fd");
	}

}
