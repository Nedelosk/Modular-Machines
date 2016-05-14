package de.nedelosk.forestmods.common.modules.heater;

import java.util.List;

import de.nedelosk.forestmods.common.modular.assembler.AssemblerGroup;
import de.nedelosk.forestmods.common.modular.assembler.AssemblerSlot;
import de.nedelosk.forestmods.common.modules.handlers.ModulePage;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.library.inventory.IContainerBase;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.library.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.library.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.library.modules.heater.IModuleHeaterBurning;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class ModuleHeaterBurning extends ModuleHeater implements IModuleHeaterBurning {

	protected int burnTime;

	public ModuleHeaterBurning(IModular modular, IModuleContainer container, int maxHeat) {
		super(modular, container, maxHeat);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		heat = nbt.getInteger("Heat");
		burnTime = nbt.getInteger("BurnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		super.writeToNBT(nbt, modular);
		nbt.setInteger("Heat", heat);
		nbt.setInteger("BurnTime", burnTime);
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
	public void addBurnTime(int burnTime) {
		this.burnTime += burnTime;
	}

	@Override
	public void updateServer() {
		if (getBurnTime() > 0) {
			if(heat < maxHeat){
				addHeat(1);
			}
			addBurnTime(-10);
			PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), this));
		} else {
			ItemStack input = inventory.getStackInSlot(0);
			if(input == null){
				if(heat > 0){
					addHeat(-1);
				}
			}else if (input != null) {
				inventory.decrStackSize(0, 1);
				setBurnTime(TileEntityFurnace.getItemBurnTime(input));
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
		group.addSlot(new AssemblerSlot(group, 4, 4, assembler.getNextIndex(group), "heater", IModuleHeater.class));
		return group;
	}

	@Override
	protected IModulePage[] createPages() {
		return new IModulePage[]{new HeaterBurningPage(0, modular, this)};
	}

	public static class HeaterBurningPage extends ModulePage<IModuleHeaterBurning>{

		public HeaterBurningPage(int pageID, IModular modular, IModuleHeaterBurning module) {
			super(pageID, modular, module);
		}

		@Override
		public void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder) {
			invBuilder.initSlot(0, true, new ItemFliterBurning());
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(module, 0, 80, 35));
		}

	}
}
