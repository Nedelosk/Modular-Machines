package modularmachines.common.modules.logic;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.common.energy.HeatBuffer;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHeatBuffer;
import modularmachines.common.utils.ModuleUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class HeatComponent extends LogicComponent implements IHeatSource{

	protected final HeatBuffer buffer;
	
	public HeatComponent() {
		buffer = new HeatBuffer(500, 15F);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Heat", buffer.serializeNBT());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		buffer.deserializeNBT(compound.getCompoundTag("Heat"));
	}
	
	@Override
	public double extractHeat(double maxExtract, boolean simulate) {
		return buffer.extractHeat(maxExtract, simulate);
	}

	@Override
	public double receiveHeat(double maxReceive, boolean simulate) {
		return buffer.receiveHeat(maxReceive, simulate);
	}

	@Override
	public void increaseHeat(double maxHeat, int heatModifier) {
		buffer.increaseHeat(maxHeat, heatModifier);
	}
	
	@Override
	public void update() {
		super.update();
		UpdateComponent update = ModuleUtil.getUpdate(logic);
		if (update.updateOnInterval(20)) {
			double oldHeat = buffer.getHeatStored();
			buffer.reduceHeat(1);
			if (oldHeat != buffer.getHeatStored()) {
				ILocatable locatable = logic.getLocatable();
				PacketHandler.sendToNetwork(new PacketSyncHeatBuffer(logic), locatable.getCoordinates(), (WorldServer) locatable.getWorldObj());
			}
		}
	}

	@Override
	public void reduceHeat(int heatModifier) {
		buffer.reduceHeat(heatModifier);
	}

	@Override
	public void setHeatStored(double heatBuffer) {
		buffer.setHeatStored(heatBuffer);
	}

	@Override
	public double getHeatStored() {
		return buffer.getHeatStored();
	}

	@Override
	public double getCapacity() {
		return buffer.getCapacity();
	}
	
	public HeatBuffer getBuffer() {
		return buffer;
	}

}
