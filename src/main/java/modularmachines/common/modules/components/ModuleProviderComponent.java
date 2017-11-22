package modularmachines.common.modules.components;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.IDropComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.modules.ModuleComponent;
import modularmachines.common.modules.ModuleHandler;

public abstract class ModuleProviderComponent extends ModuleComponent implements IModuleProvider, INBTWritable, INBTReadable, IDropComponent {
	
	protected final ModuleHandler moduleHandler;
	
	public ModuleProviderComponent(IModulePosition... positions) {
		moduleHandler = new ModuleHandler(this, positions);
	}
	
	@Override
	public IModuleHandler getHandler() {
		return moduleHandler;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		moduleHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		moduleHandler.readFromNBT(compound);
	}
	
	@Override
	public IModuleContainer getContainer() {
		return provider.getContainer();
	}
	
	@Override
	public List<ItemStack> getDrops() {
		List<ItemStack> drops = new LinkedList<>();
		moduleHandler.getModules().stream().map(m -> m.getInterfaces(IDropComponent.class)).flatMap(Collection::stream).forEach(c -> drops.addAll(c.getDrops()));
		return drops;
	}
}