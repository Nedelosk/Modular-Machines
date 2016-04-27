package de.nedelosk.forestmods.common.modules.producers.generator;

import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.ModularHelper;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleController;
import de.nedelosk.forestmods.library.modules.ModuleUIDs;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.library.modules.generator.IModuleGenerator;
import de.nedelosk.forestmods.library.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleHeatGenerator extends ModuleAddable implements IModuleGenerator {

	public ModuleHeatGenerator(String name) {
		super(ModuleUIDs.GENERATOR, "Heat" + name);
	}

	@Override
	public int getColor() {
		return 0xC9DC59;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		IModularState<IModularDefault> tile = modular.getTile();
		IModuleGeneratorType generatorType = (IModuleGeneratorType) stack.getType();
		if (modular.getManager(IModularUtilsManager.class).getEnergyHandler() != null) {
			ModuleStack<IModuleHeater, IModuleHeaterSaver> heater = ModularHelper.getHeater(modular).getItemStack();
			if (heater.getSaver().getHeat() > 20) {
				int energy = modular.getManager(IModularUtilsManager.class).getEnergyHandler().receiveEnergy(ForgeDirection.UNKNOWN, generatorType.getEnergy(),
						true);
				if (energy >= generatorType.getEnergy()) {
					modular.getManager(IModularUtilsManager.class).getEnergyHandler().receiveEnergy(ForgeDirection.UNKNOWN, generatorType.getEnergy(), false);
					heater.getSaver().addHeat(-10);
					PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularState) modular.getTile(), heater, true));
				}
			}
		}
	}

	@Override
	public void updateClient(IModular modular, ModuleStack stack) {
	}

	@Override
	public void addRequiredModules(@Nonnull List<Class<? extends IModule>> requiredModules) {
		requiredModules.add(IModuleBattery.class);
		requiredModules.add(IModuleHeater.class);
		requiredModules.add(IModuleCasing.class);
	}

	@Override
	public boolean canAssembleModular(IModular modular, ModuleStack<IModuleController, IModuleSaver, IModuleType> moduleStack) {
		return true;
	}

	@Override
	public ISimpleRenderer getRenderer(ModuleStack moduleStack, IRenderState state) {
		return null;
	}
}
