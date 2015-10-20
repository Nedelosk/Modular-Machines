package nedelosk.modularmachines.common.modular.module.tool.producer.energy;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.module.tool.producer.Producer;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerEngine extends Producer implements IProducerEngine {

	public int speedModifier;
	public String mode;
	
	public ProducerEngine(String modifier, int speedModifier, String mode) {
		super(modifier);
		this.speedModifier = speedModifier;
		this.mode = mode;
	}
	
	public ProducerEngine(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		speedModifier = nbt.getInteger("SpeedModifier");
		mode = nbt.getString("Mode");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("SpeedModifier", speedModifier);
		nbt.setString("Mode", mode);
	}

	@Override
	public int getSpeedModifier(int tier) {
		return speedModifier;
	}

	@Override
	public String getMode() {
		return mode;
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.EngineRenderer(moduleStack);
	}
	
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.EngineRenderer(moduleStack);
	}

}
