package modularmachines.common.modules.storages.modules;

/*public abstract class ModuleContainer extends Module implements IModuleProvider {
	
	public ModuleHandler moduleHandler;
	
	public ModuleContainer(IModulePosition... positions) {
		moduleHandler = new ModuleHandler(this, positions);
	}
	
	@Override
	public IModuleHandler getHandler() {
		return moduleHandler;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		moduleHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		moduleHandler.readFromNBT(compound);
	}
	
	@Override
	public boolean isModelNeedReload() {
		return super.isModelNeedReload() || moduleHandler.getModules().stream().allMatch(Module::isModelNeedReload);
	}
	
	@Override
	public List<ItemStack> getDrops() {
		List<ItemStack> drops = new LinkedList<>();
		drops.addAll(super.getDrops());
		moduleHandler.getModules().forEach(m -> drops.addAll(m.getDrops()));
		return drops;
	}
}*/
