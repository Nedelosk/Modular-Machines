package de.nedelosk.forestcore.modules;

import java.util.List;

import de.nedelosk.forestcore.modules.manager.IBlockManager;
import de.nedelosk.forestcore.modules.manager.IItemManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public interface IModuleManager {

	void register(IBlockManager manager, Block object, Class<? extends ItemBlock> item, Object... objects);

	void register(IItemManager manager, Item object, Object... objects);

	List<IModule> getModules();

	IModule registerModule(IModule module);

	void registerModules();

	void preInit();

	void init();

	void postInit();
}
