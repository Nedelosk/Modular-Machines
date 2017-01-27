package modularmachines.common.items;

import java.util.List;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Registry;
import modularmachines.common.utils.Translator;
import modularmachines.common.utils.content.IItemModelRegister;

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
	public void registerItemModels(Item item, ModelManager manager) {
		ModelResourceLocation location = ModelManager.getInstance().createModelLocation("tools/" + name.replace(".", "_"));
		manager.registerItemModel(item, new ToolMeshDefinition(location));
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
	private class ToolMeshDefinition implements ItemMeshDefinition {

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
