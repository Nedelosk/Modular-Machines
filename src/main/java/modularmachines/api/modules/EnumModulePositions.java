package modularmachines.api.modules;

public enum EnumModulePositions implements IModulePosition{
	CASING;
	
	
	@Override
	public boolean isValidModule(ModuleData moduleData) {
		return moduleData.isValidPosition(this);
	}
}
