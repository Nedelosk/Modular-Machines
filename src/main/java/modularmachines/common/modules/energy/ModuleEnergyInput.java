package modularmachines.common.modules.energy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.common.capabilities.Capability;

import modularmachines.api.components.EnumComponentTag;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.common.modules.Module;

public class ModuleEnergyInput extends Module {
	
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
	
	
}
