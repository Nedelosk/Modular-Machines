package modularmachines.common.modules.components;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.IActivatableComponent;
import modularmachines.api.modules.components.IFuelComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.modules.container.components.HeatComponent;

public class FireboxComponent extends TickableComponent implements INetworkComponent, IActivatableComponent, INBTWritable,
		INBTReadable {
	protected final double maxHeat;
	protected final int heatModifier;
	private boolean isActive;
	
	public FireboxComponent(double maxHeat, int heatModifier) {
		this.maxHeat = maxHeat;
		this.heatModifier = heatModifier;
	}
	
	@Override
	public void update() {
		super.update();
		IFuelComponent fuelComponent = provider.getInterface(IFuelComponent.class);
		IModuleContainer container = provider.getContainer();
		if (fuelComponent == null || !tickHelper.updateOnInterval(20)) {
			return;
		}
		if (fuelComponent.hasFuel()) {
			HeatComponent heatComponent = container.getComponent(HeatComponent.class);
			heatComponent.increaseHeat(maxHeat, heatModifier);
			fuelComponent.removeFuel();
			setActive(true);
		} else {
			fuelComponent.updateFuel();
			setActive(fuelComponent.hasFuel());
		}
	}
	
	@Override
	public void setActive(boolean active) {
		if (active != isActive) {
			isActive = active;
			provider.sendToClient();
		}
	}
	
	@Override
	public boolean isActive() {
		return isActive;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("active", isActive);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		isActive = compound.getBoolean("active");
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		data.writeBoolean(isActive);
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		isActive = data.readBoolean();
	}
}
