package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemFlintAxe extends ItemAxe {

	public ItemFlintAxe(ToolMaterial mat) {
		super(mat);
		this.setCreativeTab(Tabs.tabForestdayItems);
		this.setTextureName("forestday:tools/axe_flint");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ForestdayRegistry.setUnlocalizedItemName("axe.flint");
	}

}
