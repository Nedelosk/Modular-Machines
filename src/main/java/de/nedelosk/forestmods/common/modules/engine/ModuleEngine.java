package de.nedelosk.forestmods.common.modules.engine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.integration.IWailaState;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.managers.IModularUtilsManager;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.producers.IRecipeManager;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.IModuleAdvanced;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.EngineRenderer;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.common.producers.Producer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleEngine extends Producer implements IModuleEngine {

	protected int burnTime, burnTimeTotal;
	protected boolean isWorking;
	protected float progress;
	private final int speedModifier;

	public ModuleEngine(int speedModifier) {
		this.speedModifier = speedModifier;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		progress = nbt.getFloat("Progress");
		burnTime = nbt.getInteger("burnTime");
		burnTimeTotal = nbt.getInteger("burnTimeTotal");
		isWorking = nbt.getBoolean("isWorking");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		nbt.setFloat("Progress", progress);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnTimeTotal", burnTimeTotal);
		nbt.setBoolean("isWorking", isWorking);
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public void updateServer() {
		if (ModularUtils.getMachine(modular) == null) {
			return;
		}
		ModuleStack<IModuleAdvanced> stackMachine = ModularUtils.getMachine(modular);
		if (stackMachine.getModule() instanceof IModuleAdvanced) {
			IModuleAdvanced machine = stackMachine.getModule();
			if (machine.getRecipeManager() != null && getBurnTime() <= getBurnTimeTotal()) {
				if (removeMaterial(modular, stackMachine)) {
					addBurnTime(1);
					PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), moduleStack));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient() {
		if (isWorking()) {
			addProgress(0.05F);
			if (getProgress() > 1) {
				setProgress(0);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(ModuleStack stack, IRenderState state) {
		return new EngineRenderer(moduleStack, ModularUtils.getCasing(state.getModular()));
	}

	@Override
	public int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModuleAdvanced> stackMachine) {
		int speed = getSpeedModifier() * stackMachine.getModule().getSpeed() / 10;
		IModuleBattery battery = ModularUtils.getBattery(modular).getModule();
		int burnTimeTotal = speed + (speed * battery.getSpeedModifier() / 100);
		return burnTimeTotal + (burnTimeTotal * speedModifier / 100);
	}

	@Override
	public boolean removeMaterial(IModular modular, ModuleStack<IModuleAdvanced> machineStack) {
		IRecipeManager handler = machineStack.getModule().getRecipeManager();
		if (modular.getManager(IModularUtilsManager.class).getEnergyHandler().extractEnergy(ForgeDirection.UNKNOWN, handler.getMaterialToRemove(), false) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void addRequiredModules(List<Class<? extends IModule>> requiredModules) {
		requiredModules.add(IModuleBattery.class);
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		currenttip.add(getBurnTime() + " / " + getBurnTimeTotal());
		return currenttip;
	}

	@Override
	public float getProgress() {
		return progress;
	}

	@Override
	public void setProgress(float progress) {
		this.progress = progress;
	}

	@Override
	public void addProgress(float progress) {
		this.progress += progress;
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTimeTotal() {
		return burnTimeTotal;
	}

	@Override
	public void setBurnTimeTotal(int burnTimeTotal) {
		this.burnTimeTotal = burnTimeTotal;
	}

	@Override
	public void addBurnTime(int burntime) {
		this.burnTime += burntime;
	}

	@Override
	public boolean isWorking() {
		return isWorking;
	}

	@Override
	public void setIsWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	@Override
	protected IModulePage[] createPages() {
		return null;
	}
}