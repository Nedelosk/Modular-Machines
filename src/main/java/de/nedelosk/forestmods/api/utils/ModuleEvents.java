package de.nedelosk.forestmods.api.utils;

import cpw.mods.fml.common.eventhandler.Event;
import de.nedelosk.forestmods.api.modules.IModule;

public class ModuleEvents {

	public static class ModuleRegisterEvent extends Event {

		private final IModule module;

		public ModuleRegisterEvent(IModule module) {
			this.module = module;
		}

		public IModule getModule() {
			return module;
		}
	}

	public static class ModuleItemRegisterEvent extends Event {

		private final IModuleHandler item;

		public ModuleItemRegisterEvent(IModuleHandler item) {
			this.item = item;
		}
	}
}
