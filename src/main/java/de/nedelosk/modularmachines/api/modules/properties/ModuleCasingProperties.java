package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.block.BlockModificator;
import de.nedelosk.modularmachines.api.modules.handlers.block.IBlockModificator;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.module.ModuleModuleStorageProperties;
import net.minecraftforge.common.config.Configuration;

public class ModuleCasingProperties extends ModuleModuleStorageProperties implements IModuleBlockModificatorProperties {

	protected final int defaultMaxHeat;
	protected final float defaultResistance;
	protected final float defaultHardness;
	protected final int defaultHarvestLevel;
	protected final String defaultHarvestTool;
	protected int maxHeat;
	protected float resistance;
	protected float hardness;
	protected int harvestLevel;
	protected String harvestTool;

	public ModuleCasingProperties(int complexity, EnumModuleSizes size, int allowedComplexity, int maxHeat, float resistance, float hardness, int harvestLevel, String harvestTool) {
		super(complexity, size, allowedComplexity, EnumModulePositions.CASING);
		this.defaultMaxHeat = maxHeat;
		this.defaultResistance = resistance;
		this.defaultHardness = hardness;
		this.defaultHarvestLevel = harvestLevel;
		this.defaultHarvestTool = harvestTool;
		this.maxHeat = maxHeat;
		this.resistance = resistance;
		this.hardness = hardness;
		this.harvestLevel = harvestLevel;
		this.harvestTool = harvestTool;
	}

	@Override
	public <B extends IBlockModificator & IModuleContentHandler> B createBlockModificator(IModuleState state) {
		return (B) new BlockModificator(state, maxHeat, resistance, hardness, harvestTool, harvestLevel);
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		maxHeat = config.getInt("maxHeat", "modules." + container.getRegistryName(), defaultMaxHeat, 1, 10000, "");
		resistance = config.getFloat("resistance", "modules." + container.getRegistryName(), defaultResistance, 1.0F, 15.0F, "");
		hardness = config.getFloat("hardness", "modules." + container.getRegistryName(), defaultHardness, 1.0F, 15.0F, "");
		harvestLevel = config.getInt("harvestLevel", "modules." + container.getRegistryName(), defaultHarvestLevel, 0, 5, "");
		harvestTool = config.getString("harvestTool", "modules." + container.getRegistryName(), defaultHarvestTool, "");
	}
}
