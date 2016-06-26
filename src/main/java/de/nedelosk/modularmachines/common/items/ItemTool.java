package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.modularmachines.client.core.ModelManager;
import de.nedelosk.modularmachines.common.core.Registry;
import de.nedelosk.modularmachines.common.utils.Translator;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTool extends Item implements IItemModelRegister {

	private String name;
	protected int tier;
	protected Material material;

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

	@Override
	public void registerModel(Item item, IModelManager manager) {
		ModelResourceLocation location = ModelManager.getInstance().getModelLocation("tools/" + name.replace(".", "_"));
		manager.registerItemModel(item,  new ToolMeshDefinition(location));
		ModelBakery.registerItemVariants(item, location);
	}
	
	private class ToolMeshDefinition implements ItemMeshDefinition{

		ModelResourceLocation location;
		
		public ToolMeshDefinition(ModelResourceLocation location) {
			this.location = location;
		}
		
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return location;
		}
		
	}
	
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
