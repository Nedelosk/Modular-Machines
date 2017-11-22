package modularmachines.common.modules.engines;

/*public abstract class ModuleEngine extends IModule implements ITickable {
	
	protected final int materialPerWork;
	protected final double kineticModifier;
	public boolean isWorking;
	public KineticBuffer buffer;
	
	public ModuleEngine(int capacity, int maxTransfer, int materialPerWork, double kineticModifier) {
		buffer = new KineticBuffer(capacity, maxTransfer);
		this.materialPerWork = materialPerWork;
		this.kineticModifier = kineticModifier;
	}
	
	protected int getMaterialPerWork() {
		return materialPerWork;
	}
	
	protected double getKineticModifier() {
		return kineticModifier;
	}
	
	protected abstract boolean canWork();
	
	protected abstract boolean removeMaterial();
	
	@Override
	public void update() {
		if (ModuleUtil.getUpdate(container).updateOnInterval(2)) {
			boolean needNetworkUpdate = false;
			if (canWork()) {
				if (removeMaterial()) {
					if (!isWorking) {
						isWorking = true;
					}
					buffer.increaseKineticEnergy(getKineticModifier() * 2);
					needNetworkUpdate = true;
				}
			} else if (isWorking) {
				if (buffer.getStored() > 0) {
					buffer.reduceKineticEnergy(getKineticModifier() * 2);
				} else {
					isWorking = false;
				}
				needNetworkUpdate = true;
			}
			if (needNetworkUpdate) {
				sendModuleUpdate();
			}
		}
	}
	
	@Override
	public void sendModuleUpdate() {
		ILocatable locatable = container.getLocatable();
		if (locatable != null) {
			PacketHandler.sendToNetwork(new PacketSyncModule(this), locatable.getCoordinates(), locatable.getWorldObj());
		}
	}
	
	//TODO:model system
	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		return new ModelHandlerDefault(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "engines", container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		Map<ResourceLocation, ResourceLocation> locations = new HashMap<>();
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "engines", container.getSize()),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "engines", container.getSize()));
		return locations;
	}

	@Override
	public EnumWallType getWallType() {
		return EnumWallType.WINDOW;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation() {
		return ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "windows", container.getSize());
	}
}*/