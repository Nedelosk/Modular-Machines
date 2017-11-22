package modularmachines.common.modules.machine.boiler;

/*public class ModuleBoiler extends Module implements ITickable, IModuleJEI {
	
	public final ItemHandlerModule itemHandler;
	public final FluidTankHandler fluidHandler;
	public final FluidTankModule tankWater;
	public final FluidTankModule tankSteam;
	public final int waterPerWork;
	
	public ModuleBoiler(int waterPerWork) {
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addContainer(true, "liquid").addFilter(ItemFilterFluid.get(FluidRegistry.WATER));
		itemHandler.addContainer(false, "container").addFilter(OutputFilter.INSTANCE);
		itemHandler.addContainer(true, "container").addFilter(ItemFilterFluid.INSTANCE);
		itemHandler.addContainer(false, "liquid").addFilter(OutputFilter.INSTANCE);
		fluidHandler = new FluidTankHandler(this);
		tankWater = fluidHandler.addTank(true, Fluid.BUCKET_VOLUME).addFilter(FluidFilter.get(FluidRegistry.WATER));
		tankSteam = fluidHandler.addTank(false, Fluid.BUCKET_VOLUME).addFilter(OutputFilter.INSTANCE);
		this.waterPerWork = waterPerWork;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		fluidHandler.readFromNBT(compound);
		itemHandler.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		fluidHandler.writeToNBT(compound);
		itemHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void sendModuleUpdate() {
		ILocatable locatable = container.getLocatable();
		PacketHandler.sendToNetwork(new PacketSyncModule(this), locatable.getCoordinates(), locatable.getWorldObj());
		PacketHandler.sendToNetwork(new PacketSyncHeatBuffer(container), locatable.getCoordinates(), locatable.getWorldObj());
	}
	
	@Override
	public String[] getJeiRecipeCategories() {
		return new String[]{MachineCategorys.BOILER};
	}
	
	@Override
	public void update() {
		UpdateComponent update = container.getComponent(UpdateComponent.class);
		boolean needUpdate = false;
		if (update.updateOnInterval(20)) {
			ModuleUtil.tryEmptyContainer(0, 1, itemHandler, tankWater);
			ModuleUtil.tryFillContainer(2, 3, itemHandler, tankSteam);
		}
		if (update.updateOnInterval(10)) {
			HeatComponent heat = container.getComponent(HeatComponent.class);
			HeatBuffer heatSource = heat.getBuffer();
			HeatLevel heatLevel = heatSource.getHeatLevel();
			FluidStack waterStack = tankWater.getFluid();
			if (!tankWater.isEmpty() && waterStack != null && waterStack.amount > 0 && !tankSteam.isFull()) {
				if (heatSource.getHeatStored() >= HeatManager.BOILING_POINT) {
					int waterCost = (heatLevel.getIndex() - 1) * waterPerWork;
					if (waterCost <= 0) {
						return;
					}
					FluidStack water = tankWater.drainInternal(waterCost, false);
					if (water == null) {
						return;
					}
					waterCost = Math.min(waterCost, water.amount);
					FluidStack steam = new FluidStack(FluidManager.STEAM, HeatManager.STEAM_PER_UNIT_WATER / 2 * waterCost);
					steam.amount = tankSteam.fillInternal(new FluidStack(FluidManager.STEAM, HeatManager.STEAM_PER_UNIT_WATER / 2 * waterCost), false);
					if (steam.amount > 0) {
						tankWater.drainInternal(waterCost * 15, true);
						tankSteam.fillInternal(steam, true);
					}
				}
			}
		}
	}
	
	@Override
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}
	
	public FluidTankModule getTankWater() {
		return tankWater;
	}
	
	public FluidTankModule getTankSteam() {
		return tankSteam;
	}
	
	@Override
	public void createComponents() {
		super.createComponents();
		addComponent(new BoilerModuleComponent(this));
	}

	/*@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		return new ModelHandlerDefault(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "boilers", container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		return Collections.singletonMap(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "boilers", container.getSize()),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "boilers", container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleItemContainer container) {
		return ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "windows", container.getSize());
	}
}*/
