package modularmachines.common.modules.components;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.IGuiProvider;
import modularmachines.api.ILocatable;
import modularmachines.api.modules.components.IGuiFactory;
import modularmachines.api.modules.components.IInteractionComponent;
import modularmachines.common.ModularMachines;

public class GuiComponent extends ModuleComponent implements IInteractionComponent, IGuiProvider {
	private final IGuiFactory guiFactory;
	
	public GuiComponent(IGuiFactory guiFactory) {
		this.guiFactory = guiFactory;
	}
	
	@Override
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		if (player.world.isRemote) {
			return true;
		}
		BlockPos pos = provider.getContainer().getLocatable().getCoordinates();
		player.openGui(ModularMachines.instance, provider.getIndex(), player.world, pos.getX(), pos.getY(), pos.getZ());
		guiFactory.onOpenGui(player);
		return true;
	}
	
	@Override
	public ILocatable getLocatable() {
		return provider.getContainer().getLocatable();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer createGui(InventoryPlayer inventory) {
		return guiFactory.createGui(inventory);
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventory) {
		return guiFactory.createContainer(inventory);
	}
}
