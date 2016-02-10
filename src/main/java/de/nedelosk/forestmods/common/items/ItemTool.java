package de.nedelosk.forestmods.common.items;

import java.util.List;

import de.nedelosk.forestcore.core.Registry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemTool extends Item {

	private String name;

	public ItemTool(String name, int maxDamage, int tier, Material material) {
		this.setMaxDamage(maxDamage);
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setFull3D();
		this.tier = tier;
		this.material = material;
		this.maxStackSize = 1;
		this.setMaxStackSize(1);
		this.name = name;
		this.setTextureName("forestmods:tools/" + name);
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
		aList.add(StatCollector.translateToLocal("forestday.tooltip.damage") + (aStack.getMaxDamage() - getDamage(aStack)) + "/" + aStack.getMaxDamage());
		aList.add(StatCollector.translateToLocal("forestday.tooltip.tier") + (getTier()));
		aList.add(StatCollector.translateToLocal("forestday.tooltip.material") + (material.getMaterial()));
	}

	public static enum Material {
		Wood, Stone, Iron, Steel, Dark_Steel, Copper, Tin, Bronze, Gold, Diamond;

		public String material;

		public String getMaterial() {
			return material = name();
		}
	}
}
