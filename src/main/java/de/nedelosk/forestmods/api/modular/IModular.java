package de.nedelosk.forestmods.api.modular;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.integration.IWailaProvider;
import de.nedelosk.forestmods.api.modular.managers.IModularManager;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.utils.ModularException;
import net.minecraft.nbt.NBTTagCompound;

public interface IModular {

	/**
	 * @return The tier from of the modular
	 */
	int getTier();

	void update(boolean isServer);

	void initModular();

	/**
	 * @return The name of the modular for the registry
	 */
	String getName();

	IModulePage getCurrentPage();

	IModularTileEntity getTile();

	void setTile(IModularTileEntity tile);

	<M extends IModularManager> M getManager(Class<? extends M> managerClass);

	/* BUILD */
	void assemble() throws ModularException;

	boolean isAssembled();

	void assembleModular();

	/* NBT */
	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	/* Renderer */
	@SideOnly(Side.CLIENT)
	ISimpleRenderer getRenderer(IRenderState state);

	/* Waila */
	IWailaProvider getWailaProvider(IModularTileEntity tile);

	ModularException getLastException();
}
