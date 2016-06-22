package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.modularmachines.common.core.Registry;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTool extends Item {

	private String name;

	public ItemTool(String name, int maxDamage, int tier, Material material) {
		this.setMaxDamage(maxDamage);
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setFull3D();
		this.tier = tier;
		this.material = material;
		this.maxStackSize = 1;
		this.setMaxStackSize(1);
		this.name = name;
		setUnlocalizedName(Registry.setUnlocalizedItemName("tool." + name));
	}

	protected int tier;
	protected Material material;

	@Override
	public String getUnlocalizedName(ItemStack p_77667_1_) {
		return getUnlocalizedName().replace("item.", "");
	}

	public int getTier() {
		return this.tier;
	}

	@Override
	public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
		aList.add(Translator.translateToLocal("forestmods.tooltip.damage") + (aStack.getMaxDamage() - getDamage(aStack)) + "/" + aStack.getMaxDamage());
		aList.add(Translator.translateToLocal("forestmods.tooltip.tier") + (getTier()));
		aList.add(Translator.translateToLocal("forestmods.tooltip.material") + (material.getMaterial()));
	}

	public static enum Material {
		Wood, Stone, Iron, Steel, Dark_Steel, Copper, Tin, Bronze, Gold, Diamond;

		public String material;

		public String getMaterial() {
			return material = name();
		}
	}
}
