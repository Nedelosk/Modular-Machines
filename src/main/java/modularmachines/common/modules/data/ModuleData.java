package modularmachines.common.modules.data;

import com.google.common.base.MoreObjects;

import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.registries.IForgeRegistryEntry;

import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDataBuilder;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.utils.Translator;

public class ModuleData extends IForgeRegistryEntry.Impl<IModuleData> implements IModuleData {
	private final int complexity;
	private int allowedComplexity = 0;
	private final String unlocalizedName;
	private final IModulePosition[] positions;
	private final IModuleDefinition definition;
	
	public ModuleData(int complexity, String unlocalizedName, IModuleDefinition definition, IModulePosition[] positions, String registryName) {
		this.complexity = complexity;
		this.unlocalizedName = unlocalizedName;
		this.positions = positions;
		this.definition = definition;
		setRegistryName(registryName);
	}
	
	public String getDescription() {
		return Translator.translateToLocal(getUnlocalizedDescription());
	}
	
	public String getUnlocalizedDescription() {
		return "module." + unlocalizedName + ".description";
	}
	
	public String getDisplayName() {
		return Translator.translateToLocal("module." + unlocalizedName + ".name");
	}
	
	public int getAllowedComplexity() {
		return allowedComplexity;
	}
	
	public int getComplexity() {
		return complexity;
	}
	
	public boolean isValidPosition(IModulePosition position) {
		for (IModulePosition otherPosition : positions) {
			if (position == otherPosition) {
				return true;
			}
		}
		return false;
	}
	
	/* ITEM INFO */
	public void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleType container) {
		tooltip.add(Translator.translateToLocalFormatted("mm.tooltip.module.name", getDisplayName()));
		if (complexity > 0) {
			tooltip.add(Translator.translateToLocalFormatted("mm.tooltip.module.complexity", complexity));
		}
		if (allowedComplexity > 0) {
			tooltip.add(Translator.translateToLocalFormatted("mm.tooltip.module.complexity.allowed", allowedComplexity));
		}
		if (positions.length > 0) {
			StringBuilder builder = new StringBuilder();
			for (IModulePosition position : this.positions) {
				if (builder.length() == 0) {
					builder.append(position.getName());
				} else {
					builder.append(", ");
					builder.append(position.getName());
				}
			}
			tooltip.add(Translator.translateToLocalFormatted("mm.tooltip.module.positions", builder.toString()));
		}
		if (Translator.canTranslateToLocal(getUnlocalizedDescription())) {
			tooltip.add(getDescription());
		}
	}
	
	@Override
	public IModuleDefinition getDefinition() {
		return definition;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(getClass()).add("registry", getRegistryName()).add("positions", positions).toString();
	}
	
	public static class Builder implements IModuleDataBuilder {
		private IModulePosition[] positions = new IModulePosition[0];
		private String registryName = "null";
		private String unlocalizedName = "null";
		private int complexity = 0;
		private IModuleDefinition definition = (m, f) -> {
		};
		
		public IModuleDataBuilder setPositions(IModulePosition... positions) {
			this.positions = positions;
			return this;
		}
		
		public IModuleDataBuilder setRegistryName(String registryName) {
			this.registryName = registryName;
			return this;
		}
		
		public IModuleDataBuilder setUnlocalizedName(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
			return this;
		}
		
		public IModuleDataBuilder setComplexity(int complexity) {
			this.complexity = complexity;
			return this;
		}
		
		public IModuleDataBuilder setDefinition(IModuleDefinition definition) {
			this.definition = definition;
			return this;
		}
		
		public IModuleData build() {
			return new ModuleData(complexity, unlocalizedName, definition, positions, registryName);
		}
	}
}
