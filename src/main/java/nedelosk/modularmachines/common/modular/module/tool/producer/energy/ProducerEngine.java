package nedelosk.modularmachines.common.modular.module.tool.producer.energy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.module.tool.producer.Producer;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketProducerEngine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerEngine extends Producer implements IProducerEngine {

	public int speedModifier;
	public String mode;
	public float progress;
	
	public ProducerEngine(String modifier, int speedModifier, String mode) {
		super(modifier);
		this.speedModifier = speedModifier;
		this.mode = mode;
	}
	
	public ProducerEngine(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModular modular, ModuleStack stack) {
		if(ModularUtils.getModuleStackMachine(modular).getProducer() != null && ModularUtils.getModuleStackMachine(modular).getProducer().isWorking()){
			progress += 0.05;
	
			if (progress > 1) {
				progress = 0;
			}
			PacketHandler.INSTANCE.sendToServer(new PacketProducerEngine(modular.getMachine(), progress));
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.readFromNBT(nbt, modular, stack);
		speedModifier = nbt.getInteger("SpeedModifier");
		mode = nbt.getString("Mode");
		progress = nbt.getFloat("Progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("SpeedModifier", speedModifier);
		nbt.setString("Mode", mode);
		nbt.setFloat("Progress", progress);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.EngineRenderer(moduleStack);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.EngineRenderer(moduleStack);
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
	public float getProgress() {
		return progress;
	}
	
	@Override
	public void setProgress(float progress) {
		this.progress = progress;
	}

}
