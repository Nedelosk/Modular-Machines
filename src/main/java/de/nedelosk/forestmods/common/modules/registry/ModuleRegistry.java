package de.nedelosk.forestmods.common.modules.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import de.nedelosk.forestmods.common.items.ItemModule;
import de.nedelosk.forestmods.library.material.IMaterial;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleRegistry;
import de.nedelosk.forestmods.library.modules.ModuleEvents;
import de.nedelosk.forestmods.library.modules.ModuleType;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.utils.ClassUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry implements IModuleRegistry {

	// Module Registry
	private Map<IMaterial, Map<ModuleUID, ModuleType>> modules = Maps.newHashMap();
	private ArrayList<IModuleContainer> moduleContainers = Lists.newArrayList();
	private HashMap<IModuleContainer, IModule> fakeModuleCache = Maps.newHashMap();

	@Override
	public <M extends IModule> boolean registerModule(IMaterial material, ModuleUID UID, Class<? extends IModule> moduleClass, Object... objects) {
		if (material == null || UID == null || moduleClass == null) {
			return false;
		}
		if (!modules.containsKey(material)) {
			modules.put(material, Maps.newHashMap());
		}
		if (modules.get(material).containsKey(UID)) {
			return false;
		}
		Object[] newObjects = new Object[objects == null ? 2 : objects.length + 2];
		for(int i = 0; i < objects.length; i++) {
			newObjects[i+2] = objects[i];
		}
		ModuleType type = new ModuleType(moduleClass, newObjects);
		if (modules.get(material).put(UID, type) != null) {
			return true;
		}
		return false;
	}

	@Override
	public Class<? extends IModule> getModule(IMaterial material, ModuleUID UID) {
		if (material == null || UID == null) {
			return null;
		}
		if (modules.containsKey(material)) {
			if (modules.get(material).containsKey(UID)) {
				return modules.get(material).get(UID).moduleClass;
			}
		}
		return null;
	}

	@Override
	public Map<IMaterial, Map<ModuleUID, ModuleType>> getModules() {
		return modules;
	}

	@Override
	public <M extends IModule> M createModule(IModular modular, IModuleContainer container) {
		if (container == null) {
			return null;
		}
		IMaterial material = container.getMaterial(); 
		ModuleUID UID = container.getUID();
		if (material == null || UID == null) {
			return null;
		}
		if (modules.containsKey(material)) {
			if (modules.get(material).containsKey(UID)) {
				ModuleType moduleType = modules.get(material).get(UID);
				Object[] parameters = moduleType.parameters.clone();
				parameters[0] = modular;
				parameters[1] = container;
				Class[] classes = new Class[parameters.length];
				classes[0] = IModular.class;
				classes[1] = IModuleContainer.class;
				for(int i = 2; i < parameters.length; i++) {
					Object object = parameters[i];
					classes[i] = ClassUtil.classToPrimitive(object.getClass());
				}
				try {
					return (M) moduleType.moduleClass.getConstructor(classes).newInstance(parameters);
				} catch (Exception e) {
					FMLCommonHandler.instance().raiseException(e, "", true);
				}
			}
		}
		return null;
	}

	@Override
	public <M extends IModule> M getFakeModule(IModuleContainer container) {
		if(!fakeModuleCache.containsKey(container)){
			IModule module = createModule(null, container);
			fakeModuleCache.put(container, module);
			return (M) module;
		}
		return (M) fakeModuleCache.get(container);
	}

	// Items for modules
	@Override
	public void registerItemForModule(ItemStack itemStack, IModuleContainer moduleContainer) {
		registerItemForModule(itemStack, moduleContainer, false);
	}

	@Override
	public void registerItemForModule(ItemStack itemStack, IModuleContainer moduleContainer, boolean ignorNBT) {
		if (itemStack == null) {
			throw new NullPointerException("The itemstack form the module with the UID " + moduleContainer.getUID() + " from the mod "
					+ Loader.instance().activeModContainer().getModId() + " is null.");
		}
		if (!moduleContainers.equals(moduleContainer)) {
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleItemRegisterEvent(moduleContainer));
			moduleContainers.add(moduleContainer);
		}
	}

	@Override
	public ItemStack createDefaultModuleItem(IModuleContainer moduleStack) {
		return ItemModule.createItem(moduleStack);
	}

	@Override
	public IModuleContainer getContainerFromItem(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		for(IModuleContainer container : moduleContainers) {
			if (container.getItemStack() != null && container.getItemStack().getItem() != null && stack.getItem() == container.getItemStack().getItem()
					&& stack.getItemDamage() == container.getItemStack().getItemDamage()) {
				if (container.ignorNBT() || ItemStack.areItemStackTagsEqual(stack, container.getItemStack())) {
					return container;
				}
			}
		}
		return null;
	}

	@Override
	public <M extends IModule> boolean registerModuleAndItem(IMaterial material, ModuleUID UID, ItemStack itemStack, Class<? extends M> moduleClass, boolean ignorNBT, Object... objects) {
		boolean registered = registerModule(material, UID, moduleClass, objects);

		registerItemForModule(itemStack, new ModuleContainer(itemStack, UID, material, moduleClass, ignorNBT), ignorNBT);
		return registered;
	}

	@Override
	public List<IModuleContainer> getModuleContainers() {
		return Collections.unmodifiableList(moduleContainers);
	}
}
