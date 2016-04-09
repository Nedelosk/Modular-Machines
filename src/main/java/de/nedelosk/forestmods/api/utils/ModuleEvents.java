package de.nedelosk.forestmods.api.utils;

import cpw.mods.fml.common.eventhandler.Event;

public class ModuleEvents {

	public static class ModuleItemRegisterEvent extends Event {

		private final IModuleHandler item;

		public ModuleItemRegisterEvent(IModuleHandler item) {
			this.item = item;
		}
	}
}
