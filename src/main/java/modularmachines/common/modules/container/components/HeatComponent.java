package modularmachines.common.modules.container.components;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ITickable;

import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.IFirebox;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.container.IHeatSource;
import modularmachines.api.modules.events.Events;
import modularmachines.api.modules.listeners.IModuleListener;
import modularmachines.common.energy.HeatManager;
import modularmachines.common.energy.HeatStep;
import modularmachines.common.utils.TickHelper;

public class HeatComponent extends ContainerComponent implements IHeatSource, INetworkComponent, INBTReadable,
		INBTWritable, ITickable, IModuleListener {
	public static final double HEAT_STEP = 0.05D;
	
	private final Set<IFirebox> fireboxes = new HashSet<>();
	private final TickHelper tickHelper = new TickHelper();
	
	private double heat;
	private double capacity;
	
	public HeatComponent() {
		this.capacity = 0;
		this.heat = HeatManager.COLD_TEMP;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setDouble("Heat", heat);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("Heat")) {
			heat = compound.getDouble("Heat");
			heat = Math.max(heat, HeatManager.COLD_TEMP);
		}
	}
	
	@Override
	public void update() {
		tickHelper.onTick();
		if (tickHelper.updateOnInterval(40)) {
			boolean activeFirebox = fireboxes.stream().anyMatch(IFirebox::isActive);
			if (!activeFirebox || heat > capacity) {
				reduceHeat(1);
			}
		}
	}
	
	@Override
	public Collection<IFirebox> getFireboxes() {
		return fireboxes;
	}
	
	@Override
	public double getMaxHeat() {
		return capacity;
	}
	
	@Override
	public void onModuleRemoved(IModule module) {
		Collection<IFirebox> fireboxes = module.getComponents(IFirebox.class);
		if (!fireboxes.isEmpty()) {
			this.fireboxes.removeAll(fireboxes);
			calculateMaxHat();
		}
	}
	
	@Override
	public void onModuleAdded(IModule module) {
		Collection<IFirebox> fireboxes = module.getComponents(IFirebox.class);
		if (!fireboxes.isEmpty()) {
			this.fireboxes.addAll(fireboxes);
			calculateMaxHat();
		}
	}
	
	private void calculateMaxHat() {
		double maxHeat = 0.0F;
		for (IFirebox firebox : fireboxes) {
			maxHeat += firebox.getMaxHeat();
		}
		this.capacity = maxHeat / (double) fireboxes.size();
	}
	
	public double getHeatLevel() {
		return heat / capacity;
	}
	
	@Override
	public void increaseHeat(int heatModifier) {
		if (heat >= capacity) {
			return;
		}
		double change = HEAT_STEP + (((capacity - heat) / capacity) * HEAT_STEP * heatModifier);
		heat += change;
		heat = Math.min(heat, capacity);
		onChangeHeat(change);
	}
	
	@Override
	public void reduceHeat(int heatModifier) {
		if (heat == HeatManager.COLD_TEMP) {
			return;
		}
		double change = HEAT_STEP + ((heat / capacity) * HEAT_STEP * heatModifier);
		heat -= change;
		heat = Math.max(heat, HeatManager.COLD_TEMP);
		onChangeHeat(change);
	}
	
	private void onChangeHeat(double change) {
		container.sendToClient();
		container.getLocatable().markLocatableDirty();
		container.receiveEvent(new Events.HeatChangeEvent(change));
	}
	
	public HeatStep getHeatStep() {
		return HeatManager.getHeatStep(heat);
	}
	
	@Override
	public double getHeat() {
		return heat;
	}
	
	@Override
	public void setHeatStored(double heatBuffer) {
		this.heat = heatBuffer;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		data.writeDouble(heat);
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		heat = data.readDouble();
	}
}
