package nedelosk.modularmachines.api.producers.engine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.client.renderer.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketProducerEngine;
import nedelosk.modularmachines.api.producers.Producer;
import nedelosk.modularmachines.api.producers.energy.IProducerBattery;
import nedelosk.modularmachines.api.producers.machines.IProducerMachine;
import nedelosk.modularmachines.api.producers.machines.recipe.IProducerMachineRecipe;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerEngine extends Producer implements IProducerEngine {

	protected int burnTime, burnTimeTotal;
	protected int timer, timerTotal;
	protected int speedModifier;
	protected boolean isWorking;

	protected String type;

	public IRecipeManager manager;

	protected float progress;

	public ProducerEngine(String modifier, int speedModifier, String type) {
		super(modifier);
		this.speedModifier = speedModifier;
		this.type = type;
	}

	public ProducerEngine(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModular modular, ModuleStack stack) {
		if (isWorking) {
			progress += 0.05;

			if (progress > 1) {
				progress = 0;
			}
			PacketHandler.INSTANCE.sendToServer(new PacketProducerEngine(modular.getMachine(), progress));
		}
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		ModuleStack<IModule, IProducerMachine> stackMachine = ModuleUtils.getModuleStackMachine(modular);
		if (timer > timerTotal) {
			modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(),
					modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
			timer = 0;
		} else {
			timer++;

			if (stackMachine.getProducer() instanceof IProducerMachineRecipe) {
				IProducerMachineRecipe machine = (IProducerMachineRecipe) stackMachine.getProducer();
				if (manager != null)
					if (manager.removeMaterial())
						burnTime++;
			} /*
				 * else if(stack.getProducer() instanceof IProducerMachine){
				 * IProducerMachine machine = stackMachine.getProducer(); }
				 */
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		speedModifier = nbt.getInteger("SpeedModifier");
		type = nbt.getString("Type");
		progress = nbt.getFloat("Progress");
		burnTime = nbt.getInteger("burnTime");
		burnTimeTotal = nbt.getInteger("burnTimeTotal");
		timer = nbt.getInteger("timer");
		timerTotal = nbt.getInteger("timerTotal");
		isWorking = nbt.getBoolean("isWorking");

		if (nbt.hasKey("Manager")) {
			manager = creatRecipeManager();
			manager = manager.readFromNBT(nbt.getCompoundTag("Manager"), modular);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("SpeedModifier", speedModifier);
		nbt.setString("Type", type);
		nbt.setFloat("Progress", progress);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnTimeTotal", burnTimeTotal);
		nbt.setInteger("timer", timer);
		nbt.setInteger("timerTotal", timerTotal);
		nbt.setBoolean("isWorking", isWorking);

		if (manager != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			manager.writeToNBT(nbtTag, modular);
			nbt.setTag("Manager", nbtTag);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.EngineRenderer(moduleStack, ModuleUtils.getModuleStackCasing(modular));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.EngineRenderer(moduleStack, ModuleUtils.getModuleStackCasing(modular));
	}

	@Override
	public int getSpeedModifier(ModuleStack stack) {
		return speedModifier;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public float getProgress() {
		return progress;
	}

	@Override
	public void setManager(IRecipeManager manager) {
		this.manager = manager;
	}

	@Override
	public IRecipeManager getManager(ModuleStack stack) {
		return manager;
	}

	@Override
	public void setProgress(float progress) {
		this.progress = progress;
	}

	@Override
	public int getBurnTime(ModuleStack stack) {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTimeTotal(ModuleStack stack) {
		return burnTimeTotal;
	}

	@Override
	public void setBurnTimeTotal(int burnTimeTotal) {
		this.burnTimeTotal = burnTimeTotal;
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
	public int getBurnTimeTotal(IModular modular, ModuleStack<IModule, IProducerMachine> stackMachine,
			ModuleStack<IModule, IProducerEngine> stackEngine) {
		int burnTimeTotal = getSpeedModifier(stackEngine) * stackMachine.getProducer().getSpeed(stackMachine) / 10;
		ModuleStack<IModule, IProducerBattery> battery = ModuleUtils.getModuleStackBattery(modular);
		return burnTimeTotal + (burnTimeTotal * battery.getProducer().getSpeedModifier() / 100);
	}

	@Override
	public int getBurnTimeTotal(IModular modular, int speedModifier,
			ModuleStack<IModule, IProducerMachine> stackMachine, ModuleStack<IModule, IProducerEngine> stackEngine) {
		int speed = getSpeedModifier(stackEngine) * stackMachine.getProducer().getSpeed(stackMachine) / 10;
		ModuleStack<IModule, IProducerBattery> battery = ModuleUtils.getModuleStackBattery(modular);
		int burnTimeTotal = speed + (speed * battery.getProducer().getSpeedModifier() / 100);
		return burnTimeTotal + (burnTimeTotal * speedModifier / 100);
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		IModularTileEntity tile = (IModularTileEntity) data.getTileEntity();
		ModuleStack<IModule, IProducerEngine> stackEngine = ModuleUtils.getModuleStackEngine(tile.getModular());
		if (stackEngine != null) {
			IProducerEngine engine = stackEngine.getProducer();
			currenttip.add(engine.getBurnTime(stackEngine) + " / " + engine.getBurnTimeTotal(stackEngine));
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}

}
