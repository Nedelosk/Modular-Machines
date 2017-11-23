package modularmachines.common.modules.data;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.positions.IModulePosition;

public class ModuleData extends IForgeRegistryEntry.Impl<IModuleData> implements IModuleData {
	
	@SideOnly(Side.CLIENT)
	private IModelData modelData;
	private int complexity = 0;
	private int allowedComplexity = 0;
	private float dropChance = 1.0F;
	private String unlocalizedName = "null";
	private IModulePosition[] positions;
	@Nullable
	private ResourceLocation wallModelLocation;
	private IModuleDefinition definition = (d, f) -> {
	};
	
	public ModuleData(IModulePosition... positions) {
		this.positions = positions;
	}
	
	/**
	 * A description of this module. It would be displayed in jei and the item tooltip.
	 */
	public String getDescription() {
		return I18n.translateToLocal(getUnlocalizedDescription());
	}
	
	/**
	 * @return The translation kay of a description that describes the module.
	 */
	public String getUnlocalizedDescription() {
		return "module." + unlocalizedName + ".description";
	}
	
	/**
	 * Sets the unlocalized name of this module.
	 */
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
	
	/**
	 * @return The complexity that a module with this data has.
	 */
	public int getComplexity() {
		return complexity;
	}
	
	public IModuleData setComplexity(int complexity) {
		this.complexity = complexity;
		return this;
	}
	
	/**
	 * The chance that the module drops if a player breaks the block that contains this module.
	 */
	public float getDropChance() {
		return dropChance;
	}
	
	public void setDropChance(float dropChance) {
		this.dropChance = dropChance;
	}
	
	/**
	 * Checks if the position is a valid position for this module.
	 */
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
	
	@Nullable
	@Override
	public ResourceLocation getWallModelLocation() {
		return wallModelLocation;
	}
	
	@Override
	public IModuleData setWallModelLocation(ResourceLocation WallModelLocation) {
		this.wallModelLocation = WallModelLocation;
		return this;
	}
	
	/* ITEM INFO */
	public void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleDataContainer container) {
		if (I18n.canTranslate(getUnlocalizedDescription())) {
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
