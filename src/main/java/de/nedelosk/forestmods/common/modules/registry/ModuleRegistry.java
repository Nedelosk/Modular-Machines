package de.nedelosk.forestmods.common.modules.registry;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import de.nedelosk.forestcore.utils.ClassUtil;
import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.IModuleHandler;
import de.nedelosk.forestmods.api.utils.IModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleEvents;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleType;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.items.ItemModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry implements IModuleRegistry {

	// Module Registry
	private Map<IMaterial, Map<ModuleUID, ModuleType>> modules = Maps.newHashMap();
	private static ArrayList<IModuleHandler> moduleHandlers = Lists.newArrayList();

	@Override
	public <M extends IModule> M registerModule(IMaterial material, ModuleUID UID, Class<? extends M> moduleClass, Object... objects) {
		if (material == null || UID == null || moduleClass == null) {
			return null;
		}
		if (!modules.containsKey(material)) {
			modules.put(material, Maps.newHashMap());
		}
		if (modules.get(material).containsKey(UID)) {
			return createModule(material, UID);
		}
		Object[] newObjects = new Object[objects == null ? 1 : objects.length + 1];
		newObjects[0] = UID.getModuleUID();
		for(int i = 0; i < objects.length; i++) {
			newObjects[i + 1] = objects[i];
		}
		ModuleType type = new ModuleType(moduleClass, newObjects);
		if (modules.get(material).put(UID, type) != null) {
			M module = createModule(material, UID);
			return module;
		}
		return null;
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
	public <M extends IModule> M createModule(IMaterial material, ModuleUID UID) {
		if (material == null || UID == null) {
			return null;
		}
		if (modules.containsKey(material)) {
			if (modules.get(material).containsKey(UID)) {
				ModuleType moduleType = modules.get(material).get(UID);
				Class[] classes = new Class[moduleType.parameters.length];
				for(int i = 0; i < moduleType.parameters.length; i++) {
					Object object = moduleType.parameters[i];
					classes[i] = ClassUtil.classToPrimitive(object.getClass());
				}
				boolean integer = int.class.isPrimitive();
				try {
					return (M) moduleType.moduleClass.getConstructor(classes).newInstance(moduleType.parameters);
				} catch (Exception e) {
					FMLCommonHandler.instance().raiseException(e, "", true);
				}
			}
		}
		return null;
	}

	// Items for modules
	@Override
	public void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack) {
		registerItemForModule(itemStack, moduleStack, false);
	}

	@Override
	public void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack, boolean ignorNBT) {
		if (itemStack == null) {
			throw new NullPointerException("The itemstack form the module with the UID " + moduleStack.getUID() + " from the mod "
					+ Loader.instance().activeModContainer().getModId() + " is null.");
		}
		ModuleContainer moduleHandler = new ModuleContainer(itemStack, moduleStack, ignorNBT);
		if (!moduleHandlers.equals(moduleHandler)) {
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleItemRegisterEvent(moduleHandler));
			moduleHandlers.add(moduleHandler);
		}
	}

	@Override
	public <M extends IModule> ItemStack createDefaultModuleItem(ModuleStack<M> moduleStack) {
		return ItemModule.createItem(moduleStack);
	}

	@Override
	public ModuleStack getModuleFromItem(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		for(IModuleHandler handler : moduleHandlers) {
			if (handler.getStack() != null && handler.getStack().getItem() != null && stack.getItem() == handler.getStack().getItem()
					&& stack.getItemDamage() == handler.getStack().getItemDamage()) {
				if (handler.ignorNBT() || ItemStack.areItemStackTagsEqual(stack, handler.getStack())) {
					return handler.getModuleStack();
				}
			}
		}
		return null;
	}

	@Override
	public <M extends IModule> M registerModuleAndItem(IMaterial material, ModuleUID UID, ItemStack itemStack, Class<? extends M> moduleClass, boolean ignorNBT,
			Object... objects) {
		M module = registerModule(material, UID, moduleClass, objects);
		registerItemForModule(itemStack, new ModuleStack(UID, material), ignorNBT);
		return module;
	}
}
