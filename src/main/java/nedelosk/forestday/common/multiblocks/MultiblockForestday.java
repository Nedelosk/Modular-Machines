package nedelosk.forestday.common.multiblocks;

import java.util.ArrayList;

import nedelosk.forestday.api.multiblocks.ITileMultiblock;
import nedelosk.forestday.api.multiblocks.MultiblockPattern;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class MultiblockForestday extends AbstractMultiblock {

	@Override
	public String getMultiblockName() {
		return "multiblockForestday";
	}

	@Override
	public MultiblockPattern createPattern() {
		return null;
	}
	
	@Override
	public IIcon getIcon(int side, ITileMultiblock tile) {
		return null;
	}
	
	@Override
	public float[] getBlockBounds()
	{
		return new float[]{0,0,0,1,1,1};
	}

	@Override
	public ArrayList<MultiblockPattern> createPatterns() {
		return null;
	}

	@Override
	public void updateMultiblock() {
		
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		
	}

	@Override
	public boolean testBlock() {
		return false;
	}

	@Override
	public boolean isPatternBlockValid(int x, int y, int z, char pattern, ITileMultiblock tile) {
		return false;
	}
	
	@Override
	public TileEntitySpecialRenderer getRenderer() {
		return null;
	}

	@Override
	public void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side) {
		
	}

	@Override
	public Container getContainer(ITileMultiblock base, InventoryPlayer inventory) {
		return null;
	}

	@Override
	public Object getGUIContainer(ITileMultiblock base, InventoryPlayer inventory) {
		return null;
	}

	@Override
	public void updateClient(ITileMultiblock base) {
		
	}

	@Override
	public void updateServer(ITileMultiblock base) {
		
	}
	
	@Override
	public boolean hasBlockActivatedFunction() {
		return false;
	}

}
