package de.nedelosk.forestmods.common.items;

import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestmods.api.Tabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemFlintAxe extends ItemAxe {

	public ItemFlintAxe(ToolMaterial mat) {
		super(mat);
		this.setCreativeTab(Tabs.tabForestMods);
		this.setTextureName("forestday:tools/axe_flint");
		setUnlocalizedName(Registry.setUnlocalizedItemName("axe.flint"));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName("axe.flint");
	}
}
