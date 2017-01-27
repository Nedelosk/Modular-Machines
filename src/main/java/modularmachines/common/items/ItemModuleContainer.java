package modularmachines.common.items;

/*public class ItemModuleContainer extends Item implements IColoredItem, IItemModelRegister {

	public ItemModuleContainer() {
		setUnlocalizedName("module_container");
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
		IModuleItemProvider moduleProvider = itemStack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
		if (moduleProvider != null && moduleProvider.getItemStack() != null) {
			if (player.isSneaking()) {
				ItemStack providerStack = moduleProvider.getItemStack().copy();
				boolean dropItems = !world.isRemote;
				List<ItemStack> drops = Lists.newArrayList();
				for (IModuleState moduleState : moduleProvider) {
					if (moduleState != null) {
						if (dropItems) {
							for (IModuleContentHandler handler : moduleState.getAllContentHandlers()) {
								if (handler instanceof IAdvancedModuleContentHandler) {
									drops.addAll(((IAdvancedModuleContentHandler) handler).getDrops());
								}
							}
						}
						moduleState.getModule().saveDataToItem(itemStack, moduleState);
					}
				}
				if (dropItems) {
					for (ItemStack stack : drops) {
						ItemHandlerHelper.giveItemToPlayer(player, stack);
					}
				}
				return ActionResult.newResult(EnumActionResult.SUCCESS, providerStack);
			}
		}
		return ActionResult.newResult(EnumActionResult.PASS, itemStack);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new ModuleItemProvider();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemModels(Item item, ModelManager manager) {
		manager.registerItemModel(item, 0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName(super.getUnlocalizedName(stack).replace("item.", ""));
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		IModuleItemProvider moduleProvider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
		if (moduleProvider != null && moduleProvider.getItemStack() != null) {
			return super.getItemStackDisplayName(stack) + ": " + moduleProvider.getItemStack().getDisplayName();
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(Translator.translateToLocal("mm.tooltip.moduleContainer"));
		tooltip.add(TextFormatting.RED + Translator.translateToLocal("mm.tooltip.moduleContainer.warning"));
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		IModuleItemProvider moduleProvider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
		if (moduleProvider != null && moduleProvider.getItemStack() != null) {
			ItemStack providerStack = moduleProvider.getItemStack();
			return Minecraft.getMinecraft().getItemColors().getColorFromItemstack(providerStack, tintIndex);
		}
		return -1;
	}
}*/
