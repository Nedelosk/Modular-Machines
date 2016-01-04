package nedelosk.modularmachines.common.multiblock.blastfurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.inventory.IGuiHandler;
import nedelosk.forestcore.library.multiblock.MultiblockControllerBase;
import nedelosk.forestcore.library.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public abstract class TileBlastFurnacePart extends RectangularMultiblockTileEntityBase implements IGuiHandler {

	public TileBlastFurnacePart() {
		super();
	}

	public BlastFurnaceController getBlastFurnaceController() {
		return (BlastFurnaceController) this.getMultiblockController();
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new BlastFurnaceController(this.worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return BlastFurnaceController.class;
	}

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
