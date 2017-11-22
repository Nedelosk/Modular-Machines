package modularmachines.common.modules.energy;

/*public class ModuleEnergyInput extends Module {
	
	@Override
	protected AxisAlignedBB getBoundingBox() {
		return new AxisAlignedBB(3.0F / 16.0F, 3.0F / 16.0F, 15.0F / 16F, 13.0F / 16.0F, 13.0F / 16.0F, 1.0F);
	}
	
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (isFacing(facing)) {
			return null;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		if (isFacing(facing)) {
			Collection<ContainerComponent> components = container.getComponents(EnumComponentTag.ENERGY);
			return components.stream().anyMatch(containerComponent -> containerComponent.hasCapability(capability, facing));
		}
		return super.hasCapability(capability, facing);
	}
	
	
}*/
