package de.nedelosk.modularmachines.api.modules;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ModuleEvents {

	public static class ModuleItemRegisterEvent extends Event {

		private final IModuleContainer item;

		public ModuleItemRegisterEvent(IModuleContainer item) {
			this.item = item;
		}
	}
}
