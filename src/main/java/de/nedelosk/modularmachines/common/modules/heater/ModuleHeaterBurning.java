package de.nedelosk.modularmachines.common.modules.heater;

import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeaterBurning;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.PropertyInteger;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerGroup;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerSlot;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class ModuleHeaterBurning extends ModuleHeater implements IModuleHeaterBurning {

	public static final PropertyInteger BURNTIME = new PropertyInteger("burnTime");

	public ModuleHeaterBurning(int maxHeat, int size) {
		super(maxHeat, size);
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).add(BURNTIME, 0);
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
			PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) moduleState.getModular().getTile(), moduleState));
		} else {
			IModuleInventory inventory = (IModuleInventory) moduleState.getContentHandler(ItemStack.class);
			ItemStack input = inventory.getStackInSlot(HeaterBurningPage.BURNSLOT);
			if(input == null){
				if(casingState.getModule().getHeat(casingState) > 0){
					casingState.getModule().addHeat(casingState, -1);
				}
			}else if (input != null) {
				if(inventory.extractItem(HeaterBurningPage.BURNSLOT, 1, false) != null){
					setBurnTime(moduleState, TileEntityFurnace.getItemBurnTime(input));
				}
			}
		}
	}

	@Override
	public boolean canAssembleGroup(IAssemblerGroup group) {
		return true;
	}

	@Override
	public IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID) {
		IAssemblerGroup group = new AssemblerGroup(assembler, groupID);
		group.addSlot(new AssemblerSlot(group, 4, 4, assembler.getNextIndex(), "heater", IModuleHeater.class));
		return group;
	}

	@Override
	public IModulePage[] createPages(IModuleState moduleState) {
		return new IModulePage[]{new HeaterBurningPage(0, moduleState)};
	}

	public static class HeaterBurningPage extends ModulePage<IModuleHeaterBurning>{

		public static int BURNSLOT;

		public HeaterBurningPage(int pageID, IModuleState<IModuleHeaterBurning> heaterState) {
			super(pageID, heaterState);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			BURNSLOT = invBuilder.addInventorySlot(true, new ItemFliterBurning());
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0, 80, 35));
		}

	}
}
