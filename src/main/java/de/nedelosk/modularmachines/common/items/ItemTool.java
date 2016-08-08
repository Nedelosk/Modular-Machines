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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTool extends Item implements IItemModelRegister {

	private String name;

	public ItemTool(String name, int maxDamage) {
		this.setMaxDamage(maxDamage);
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.maxStackSize = 1;
		this.setMaxStackSize(1);
		this.name = name;
		setUnlocalizedName(Registry.setUnlocalizedItemName("tool." + name));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		ModelResourceLocation location = ModelManager.getInstance().getModelLocation("tools/" + name.replace(".", "_"));
		manager.registerItemModel(item,  new ToolMeshDefinition(location));
		ModelBakery.registerItemVariants(item, location);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return getUnlocalizedName().replace("item.", "");
	}

	@Override
	public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean advanced) {
		aList.add(Translator.translateToLocal("mm.tooltip.damage") + (aStack.getMaxDamage() - getDamage(aStack)) + "/" + aStack.getMaxDamage());
	}

	@SideOnly(Side.CLIENT)
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
}
