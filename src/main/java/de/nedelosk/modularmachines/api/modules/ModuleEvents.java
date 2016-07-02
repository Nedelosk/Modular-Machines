package de.nedelosk.modularmachines.api.modules;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

public class ModuleEvents {

	public static class ModuleItemRegisterEvent extends Event {

		private final IModuleContainer moduleContainer;

		public ModuleItemRegisterEvent(IModuleContainer moduleContainer) {
			this.moduleContainer = moduleContainer;
		}

		public IModuleContainer getItem() {
			return moduleContainer;
		}
	}

	public static class ModuleStateEvent extends Event {

		private final IModuleState state;

		public ModuleStateEvent(IModuleState state) {
			this.state = state;
		}

		public IModuleState getState() {
			return state;
		}
	}
	
	public static class ModulePageCreateEvent extends ModuleStateEvent {

		private final List<IModulePage> pages;
		
		public ModulePageCreateEvent(IModuleState state, List<IModulePage> pages) {
			super(state);
			this.pages = pages;
		}
		
		public List<IModulePage> getModulePages() {
			return pages;
		}

	}
	
	public static class ModuleStateCreateEvent extends ModuleStateEvent {

		public ModuleStateCreateEvent(IModuleState state) {
			super(state);
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
