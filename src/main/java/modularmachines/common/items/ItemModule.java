package modularmachines.common.items;

import java.util.List;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.material.IColoredMaterial;
import modularmachines.api.material.IMaterial;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Registry;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.utils.content.IColoredItem;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemModule extends Item implements IColoredItem, IItemModelRegister {

	public ItemModule() {
		setUnlocalizedName("modules");
		setCreativeTab(TabModularMachines.tabModules);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemModels(Item item, ModelManager manager) {
		ModelResourceLocation[] locs = new ModelResourceLocation[] { ModelManager.getInstance().createModelLocation("module_small"), ModelManager.getInstance().createModelLocation("module_medium"),
				ModelManager.getInstance().createModelLocation("module_large") };
		manager.registerItemModel(item, new ModuleItemMeshDefinition(locs));
		ModelBakery.registerItemVariants(item, locs[0]);
		ModelBakery.registerItemVariants(item, locs[1]);
		ModelBakery.registerItemVariants(item, locs[2]);
	}

	private static class ModuleItemMeshDefinition implements ItemMeshDefinition {

		private ModelResourceLocation[] locs;

		public ModuleItemMeshDefinition(ModelResourceLocation... locs) {
			this.locs = locs;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(stack);
			if (itemContainer != null) {
				return locs[itemContainer.getSize().ordinal() - 1];
			}
			return ModelManager.getInstance().createModelLocation("module_large");
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName(super.getUnlocalizedName(stack).replace("item.", ""));
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(stack);
		if (itemContainer != null) {
			return itemContainer.getContainer(0).getDisplayName();
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (IModuleItemContainer container : ModuleManager.getModulesWithDefaultItem()) {
			subItems.add(ModuleManager.createDefaultStack(container));
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if (stack.hasTagCompound()) {
			IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(stack);
			if (itemContainer != null) {
				if (tintIndex == 0) {
					IMaterial material = itemContainer.getMaterial();
					if (material instanceof IColoredMaterial) {
						return ((IColoredMaterial) material).getColor();
					}
				} else if (tintIndex == 1) {
					IModuleContainer container = itemContainer.getContainer(0);
					IModule module = container.getModule();
					if (module instanceof IModuleColoredItem) {
						return ((IModuleColoredItem) module).getColor(container);
					}
				}
			}
		}
		return 16777215;
	}
}
