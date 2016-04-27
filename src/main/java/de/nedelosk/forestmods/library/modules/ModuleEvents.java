package de.nedelosk.forestmods.library.modules;

import cpw.mods.fml.common.eventhandler.Event;

public class ModuleEvents {

	public static class ModuleItemRegisterEvent extends Event {

		private final IModuleContainer item;

		public ModuleItemRegisterEvent(IModuleContainer item) {
			this.item = item;
		}
	}
}
