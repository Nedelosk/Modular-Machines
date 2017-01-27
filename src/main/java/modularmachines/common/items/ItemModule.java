package modularmachines.common.items;

/*public class ItemModule extends Item implements IColoredItem, IItemModelRegister {

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
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (IModuleItemContainer container : ModuleManager.getModulesWithDefaultItem()) {
			subItems.add(ModuleManager.createDefaultStack(container));
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if (stack.hasTagCompound()) {
			IModuleContainer container = ModuleHelper.getContainerFromItem(stack);
			if (container != null) {
				if (tintIndex == 0) {
					IMaterial material = container.getMaterial();
					if (material instanceof IColoredMaterial) {
						return ((IColoredMaterial) material).getColor();
					}
				} else if (tintIndex == 1) {
					ModuleData data = container.getContainer(0);
					IModule module = data.getModule();
					if (module instanceof IModuleColoredItem) {
						return ((IModuleColoredItem) module).getColor(data);
					}
				}
			}
		}
		return 16777215;
	}
}*/
