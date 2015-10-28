package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.registry.FRegistry;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemFlintAxe extends ItemAxe {

	public ItemFlintAxe(ToolMaterial mat) {
		super(mat);
		this.setCreativeTab(Tabs.tabForestday);
		this.setTextureName("forestday:tools/axe_flint");
		setUnlocalizedName(FRegistry.setUnlocalizedItemName("axe.flint", "fd"));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return FRegistry.setUnlocalizedItemName("axe.flint", "fd");
	}

}
