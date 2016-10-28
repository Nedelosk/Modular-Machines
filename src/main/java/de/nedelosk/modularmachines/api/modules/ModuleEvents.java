package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

public class ModuleEvents {

	public static abstract class ModuleStateEvent extends Event {

		private final IModuleState state;

		public ModuleStateEvent(IModuleState state) {
			this.state = state;
		}

		public IModuleState getState() {
			return state;
		}
	}

	public static abstract class ModularEvent extends Event {

		private final IModular modular;

		public ModularEvent(IModular modular) {
			this.modular = modular;
		}

		public IModular getModular() {
			return modular;
		}
	}

	public static class ModularAssembledEvent extends ModularEvent {

		public ModularAssembledEvent(IModular modular) {
			super(modular);
		}
	}

	public static class ModuleStateCreateEvent extends ModuleStateEvent {

		public ModuleStateCreateEvent(IModuleState state) {
			super(state);
		}
	}

	public static class ModuleStateLoadItemEvent extends ModuleStateEvent {

		private final ItemStack stack;

		public ModuleStateLoadItemEvent(IModuleState state, ItemStack stack) {
			super(state);
			this.stack = stack;
		}

		public ItemStack getStack() {
			return stack;
		};
	}

	public static class ModuleStateLoadEvent extends ModuleStateEvent {

		private final NBTTagCompound nbtTag;

		public ModuleStateLoadEvent(IModuleState state, NBTTagCompound nbtTag) {
			super(state);
			this.nbtTag = nbtTag;
		}

		public NBTTagCompound getNBTTag() {
			return nbtTag;
		}
	}

	public static class ModuleStateSaveEvent extends ModuleStateEvent {

		private final NBTTagCompound nbtTag;

		public ModuleStateSaveEvent(IModuleState state, NBTTagCompound nbtTag) {
			super(state);
			this.nbtTag = nbtTag;
		}

		public NBTTagCompound getNBTTag() {
			return nbtTag;
		}
	}

	public static class ModulePageUpdateEvent extends ModuleStateEvent {

		private final Side updateSide;
		private final IModulePage modulePage;

		public ModulePageUpdateEvent(IModuleState state, IModulePage modulePage, Side updateSide) {
			super(state);
			this.modulePage = modulePage;
			this.updateSide = updateSide;
		}

		public IModulePage getModulePage() {
			return modulePage;
		}

		public Side getUpdateSide() {
			return updateSide;
		}
	}

	public static class ModuleUpdateEvent extends ModuleStateEvent {

		private final Side updateSide;

		public ModuleUpdateEvent(IModuleState state, Side updateSide) {
			super(state);
			this.updateSide = updateSide;
		}

		public Side getUpdateSide() {
			return updateSide;
		}
	}
}
