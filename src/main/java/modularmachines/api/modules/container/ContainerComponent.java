package modularmachines.api.modules.container;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import modularmachines.api.components.IComponent;

public class ContainerComponent implements ICapabilityProvider, IComponent<IModuleContainer> {
	public static final String ENERGY = "energy";
	public static final String HEAT = "heat";
	public static final String UPDATE = "update";
	
	protected IModuleContainer container;
	
	
	public ContainerComponent() {
	}
	
	public Class<?>[] getComponentInterfaces() {
		return container.getComponentInterfaces(this.getClass());
	}
	
	public void setProvider(IModuleContainer provider) {
		this.container = provider;
	}
	
	public IModuleContainer getProvider() {
		return container;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return compound;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return false;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return null;
	}
}
