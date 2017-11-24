package modularmachines.api.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fml.common.eventhandler.Event;

import modularmachines.api.modules.container.IModuleContainer;

public class ModuleEvents extends Event {
	/**
	 * Fired at the creation of a module. Can be used to add components.
	 */
	public static class CreationEvent extends ModuleEvents {
		private final IModule module;
		
		public CreationEvent(IModule module) {
			this.module = module;
		}
		
		public IModule getModule() {
			return module;
		}
	}
	
	/**
	 * Fired at the extraction of a module from a module container or if a player breaks the block that contains the
	 * module container.
	 */
	public static class DropEvent extends Event {
		private final IModule module;
		private final NonNullList<ItemStack> drops;
		
		public DropEvent(IModule module, NonNullList<ItemStack> drops) {
			this.module = module;
			this.drops = drops;
		}
		
		public IModule getModule() {
			return module;
		}
		
		public NonNullList<ItemStack> getDrops() {
			return drops;
		}
	}
	
	/**
	 * Fired at the creation of a module container.
	 */
	public static class ContainerCreationEvent extends ModuleEvents {
		private final IModuleContainer container;
		
		public ContainerCreationEvent(IModuleContainer container) {
			this.container = container;
		}
		
		public IModuleContainer getContainer() {
			return container;
		}
	}
}
