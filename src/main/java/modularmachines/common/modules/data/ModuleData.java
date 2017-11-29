package modularmachines.common.modules.data;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.utils.Translator;

public class ModuleData extends IForgeRegistryEntry.Impl<IModuleData> implements IModuleData {
	private static final IModuleDefinition DEFAULT_DEFINITION = (d, f) -> {
	};
	
	@Nullable
	@SideOnly(Side.CLIENT)
	private IModelData modelData;
	
	private int complexity = 0;
	private int allowedComplexity = 0;
	private String unlocalizedName = "null";
	private IModulePosition[] positions;
	private IModuleDefinition definition = DEFAULT_DEFINITION;
	
	public ModuleData(IModulePosition... positions) {
		this.positions = positions;
	}
	
	public String getDescription() {
		return I18n.translateToLocal(getUnlocalizedDescription());
	}
	
	public String getUnlocalizedDescription() {
		return "module." + unlocalizedName + ".description";
	}
	
	public void setUnlocalizedName(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}
	
	public String getDisplayName() {
		return I18n.translateToLocal("module." + unlocalizedName + ".name");
	}
	
	public int getAllowedComplexity() {
		return allowedComplexity;
	}
	
	public IModuleData setAllowedComplexity(int allowedComplexity) {
		this.allowedComplexity = allowedComplexity;
		return this;
	}
	
	public int getComplexity() {
		return complexity;
	}
	
	public IModuleData setComplexity(int complexity) {
		this.complexity = complexity;
		return this;
	}
	
	public boolean isValidPosition(IModulePosition position) {
		for (IModulePosition otherPosition : positions) {
			if (position == otherPosition) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public IModuleData setPositions(IModulePosition... positions) {
		this.positions = positions;
		return this;
	}
	
	/* CONTAINERS */
	
	@Override
	public IModuleData registerType(ItemStack itemStack) {
		ModuleManager.registry.registerType(new ModuleType(itemStack, this));
		return this;
	}
	
	/* ITEM INFO */
	public void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleType container) {
		tooltip.add(Translator.translateToLocalFormatted("mm.tooltip.module.name", getDisplayName()));
		if (complexity > 0) {
			tooltip.add(Translator.translateToLocalFormatted("mm.tooltip.module.complexity", complexity));
		}
		if (allowedComplexity > 0) {
			tooltip.add(Translator.translateToLocalFormatted("mm.tooltip.module.complexity.allowed", complexity));
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
	public IModuleData setDefinition(IModuleDefinition definition) {
		this.definition = definition;
		return this;
	}
	
	@Override
	public IModuleDefinition getDefinition() {
		return definition;
	}
	
	/* MODEL */
	@SideOnly(Side.CLIENT)
	@Nullable
	public IModelData getModel() {
		return modelData;
	}
	
	@SideOnly(Side.CLIENT)
	public IModuleData setModel(IModelData modelData) {
		this.modelData = modelData;
		return this;
	}
}
