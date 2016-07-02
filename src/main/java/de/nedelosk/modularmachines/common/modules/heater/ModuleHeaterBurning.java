package de.nedelosk.modularmachines.common.modules.heater;

import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeaterBurning;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.PropertyInteger;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ModuleHeaterBurning extends ModuleHeater implements IModuleHeaterBurning {

	public static final PropertyInteger BURNTIME = new PropertyInteger("burnTime", 0);

	public ModuleHeaterBurning(int maxHeat, int size) {
		super(maxHeat, size);
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(BURNTIME);
	}

	@Override
	public int getBurnTime(IModuleState state) {
		return (int) state.get(BURNTIME);
	}

	@Override
	public void setBurnTime(IModuleState state, int burnTime) {
		state.add(BURNTIME, burnTime);
	}

	@Override
	public void addBurnTime(IModuleState state, int burnTime) {
		state.add(BURNTIME, (int)state.get(BURNTIME) + burnTime);
	}

	@Override
	public void updateServer(IModuleState moduleState) {
		IModuleState<IModuleCasing> casingState = ModularHelper.getCasing(moduleState.getModular());
		if (getBurnTime(moduleState) > 0) {
			if(casingState.getModule().getHeat(casingState) < maxHeat){
				casingState.getModule().addHeat(casingState, 1);
			}
			addBurnTime(moduleState, -10);
			PacketHandler.INSTANCE.sendToAll(new PacketModule(moduleState.getModular().getHandler(), moduleState));
		} else {
			List<IModulePage> pages = moduleState.getPages();
			IModuleInventory inventory = (IModuleInventory) moduleState.getContentHandler(ItemStack.class);
			ItemStack input = inventory.getStackInSlot(((HeaterBurningPage)pages.get(0)).BURNSLOT);
			if(input == null){
				if(casingState.getModule().getHeat(casingState) > 0){
					casingState.getModule().addHeat(casingState, -1);
				}
			}else if (input != null) {
				if(inventory.extractItem(((HeaterBurningPage)pages.get(0)).BURNSLOT, 1, false) != null){
					setBurnTime(moduleState, TileEntityFurnace.getItemBurnTime(input));
				}
			}
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new HeaterBurningPage("Basic", state));
		return pages;
	}

	public class HeaterBurningPage extends ModulePage<IModuleHeaterBurning>{

		public int BURNSLOT;

		public HeaterBurningPage(String pageID, IModuleState<IModuleHeaterBurning> heaterState) {
			super(pageID, heaterState);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			BURNSLOT = invBuilder.addInventorySlot(true, new ItemFliterBurning());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, BURNSLOT, 80, 35));
		}

	}
}
