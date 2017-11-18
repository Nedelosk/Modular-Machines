package modularmachines.common.modules.heaters;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.common.modules.IModuleBurning;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHeatBuffer;
import modularmachines.common.network.packets.PacketSyncModule;
import modularmachines.common.utils.ModuleUtil;

public abstract class ModuleHeater extends Module implements ITickable, IModuleBurning {
	
	protected final double maxHeat;
	protected final int heatModifier;
	protected int fuel;
	protected int fuelTotal;
	
	public ModuleHeater(double maxHeat, int heatModifier) {
		this.maxHeat = maxHeat;
		this.heatModifier = heatModifier;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("Fuel", fuel);
		compound.setInteger("FuelTotal", fuelTotal);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		fuel = compound.getInteger("Fuel");
		fuelTotal = compound.getInteger("FuelTotal");
	}
	
	@Override
	public int getFuel() {
		return fuel;
	}
	
	@Override
	public int getFuelTotal() {
		return fuelTotal;
	}
	
	@Override
	public void update() {
		if (ModuleUtil.getUpdate(container).updateOnInterval(20)) {
			boolean needUpdate = false;
			if (canAddHeat()) {
				IHeatSource buffer = ModuleUtil.getHeat(container);
				buffer.increaseHeat(maxHeat, heatModifier);
				afterAddHeat();
				needUpdate = true;
			} else {
				needUpdate = updateFuel();
			}
			if (needUpdate) {
				sendModuleUpdate();
			}
		}
	}
	
	@Override
	public void sendModuleUpdate() {
		ILocatable locatable = container.getLocatable();
		if (locatable != null) {
			PacketHandler.sendToNetwork(new PacketSyncModule(this), locatable.getCoordinates(), (WorldServer) locatable.getWorldObj());
			PacketHandler.sendToNetwork(new PacketSyncHeatBuffer(container), locatable.getCoordinates(), (WorldServer) locatable.getWorldObj());
		}
	}
	
	//TODO: rendering
	/*@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		ResourceLocation[] locations = new ResourceLocation[2];
		locations[0] = ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), true);
		locations[1] = ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), false);
		return new ModelHandlerStatus(locations);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		Map<ResourceLocation, ResourceLocation> locations = new HashMap<>();
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), true),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "heaters", container.getSize(), true));
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), false),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "heaters", container.getSize(), false));
		return locations;
	}*/
	
	protected abstract boolean canAddHeat();
	
	protected abstract boolean updateFuel();
	
	protected abstract void afterAddHeat();
}