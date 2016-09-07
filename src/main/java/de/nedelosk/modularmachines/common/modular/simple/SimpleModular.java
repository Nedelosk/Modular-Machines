package de.nedelosk.modularmachines.common.modular.simple;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.ISimpleModular;
import de.nedelosk.modularmachines.api.modular.ISimpleModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modular.Modular;
import de.nedelosk.modularmachines.common.utils.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class SimpleModular extends Modular implements ISimpleModular {

	protected final List<IModuleState> moduleStates = new ArrayList<>();
	protected final IAssemblerLogic logic;

	public SimpleModular(IAssemblerLogic logic){
		super();

		this.logic = logic;
	}

	public SimpleModular(NBTTagCompound nbt, IModularHandler handler, IAssemblerLogic logic) {
		super(nbt, handler);

		this.logic = logic;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		NBTTagList nbtList = new NBTTagList();
		for(IModuleState module : moduleStates) {
			NBTTagCompound nbtTag = module.serializeNBT();
			nbtTag.setString("Container", module.getContainer().getRegistryName().toString());
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateSaveEvent(module, nbtTag));
			nbtList.appendTag(nbtTag);
		}
		nbt.setTag("Modules", nbtList);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		NBTTagList nbtList = nbt.getTagList("Modules", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound moduleTag = nbtList.getCompoundTagAt(i);
			ResourceLocation loc = new ResourceLocation(moduleTag.getString("Container"));
			IModuleContainer container = ModularMachinesApi.MODULE_CONTAINERS.getValue(loc);
			if(container != null){
				IModuleState state = ModularMachinesApi.createModuleState(this, container);
				state.deserializeNBT(moduleTag);
				MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadEvent(state, moduleTag));
				moduleStates.add(state);
			}else{
				Log.err("Remove module from modular, because the item of the module don't exist any more.");
			}
		}
	}

	@Override
	public List<IModuleState> getModules() {
		return moduleStates;
	}

	@Override
	public IModuleState addModule(ItemStack itemStack, IModuleState state) {
		if (state == null) {
			return null;
		}

		if (moduleStates.add(state)) {
			state.setIndex(getNextIndex());
			return state;
		}
		return null;
	}

	@Override
	public ISimpleModularAssembler disassemble() {
		if(modularHandler instanceof IModularHandlerTileEntity){
			((IModularHandlerTileEntity)modularHandler).invalidate();
		}
		ItemStack[] moduleStacks = new ItemStack[26];
		int index = 0;
		for(IModuleState state : moduleStates) {
			moduleStacks[index] = ModularMachinesApi.saveModuleState(state);
			index++;
		}
		return new SimpleModularAssembler(modularHandler, moduleStacks, logic);
	}

	@Override
	public ISimpleModular copy(IModularHandler handler) {
		return new SimpleModular(serializeNBT(), handler, logic);
	}
}
