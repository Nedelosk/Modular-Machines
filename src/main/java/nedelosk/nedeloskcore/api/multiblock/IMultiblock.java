package nedelosk.nedeloskcore.api.multiblock;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public interface IMultiblock extends INBTTagable{

	String getMultiblockName();
	
	MultiblockPattern createPattern();
	
	ArrayList<MultiblockPattern> createPatterns();
	
	void updateMultiblock();
	
	@SideOnly(Side.CLIENT)
	TileEntitySpecialRenderer getRenderer();
	
	boolean testBlock();
	
	float[] getBlockBounds();
	
	IIcon getIcon(int side, ITileMultiblock tile);
	
	boolean isPatternBlockValid(int x, int y, int z, char pattern, ITileMultiblock tile);

	void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side);
	
	Container getContainer(ITileMultiblock base, InventoryPlayer inventory);

	Object getGUIContainer(ITileMultiblock  base, InventoryPlayer inventory);

	void updateClient(ITileMultiblock  base);
	
	void updateServer(ITileMultiblock  base);
	
	void registerBlockIcons(IIconRegister IIconRegister);
	
	boolean hasBlockActivatedFunction();
	
}
