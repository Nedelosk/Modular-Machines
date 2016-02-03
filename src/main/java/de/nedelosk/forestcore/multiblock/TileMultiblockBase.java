package de.nedelosk.forestcore.multiblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.inventory.IGuiHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public abstract class TileMultiblockBase<M extends MultiblockControllerBase> extends RectangularMultiblockTileEntityBase implements IGuiHandler {

	public TileMultiblockBase() {
		super();
	}

	public M getController() {
		return (M) this.getMultiblockController();
	}

	@Override
	public void onMachineAssembled(MultiblockControllerBase controller) {
		super.onMachineAssembled(controller);
		// Re-render this block on the client
		if (worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void onMachineBroken() {
		super.onMachineBroken();
		// Re-render this block on the client
		if (worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public abstract M createNewMultiblock();

	@Override
	public abstract Class<? extends M> getMultiblockControllerType();

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return null;
	}
}
