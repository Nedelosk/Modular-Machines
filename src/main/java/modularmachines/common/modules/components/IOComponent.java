package modularmachines.common.modules.components;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;

import modularmachines.api.EnumIOMode;
import modularmachines.api.IScrewdriver;
import modularmachines.api.modules.components.IIOComponent;
import modularmachines.api.modules.components.IInteractionComponent;

public class IOComponent extends ModuleComponent implements IIOComponent, IInteractionComponent {
	private final Map<EnumFacing, EnumIOMode> faceModes;
	private EnumIOMode mode;
	
	public IOComponent() {
		this.faceModes = new EnumMap<>(EnumFacing.class);
		this.mode = EnumIOMode.IN_OUT;
		for (EnumFacing facing : EnumFacing.VALUES) {
			faceModes.put(facing, EnumIOMode.IN_OUT);
		}
	}
	
	@Override
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
		if (heldItem.getItem() instanceof IScrewdriver) {
			IScrewdriver screwdriver = (IScrewdriver) heldItem.getItem();
			EnumFacing facing = screwdriver.getSelectedFacing(heldItem);
			EnumIOMode newMode;
			if (facing == null) {
				mode = newMode = EnumIOMode.getNext(mode);
			} else {
				newMode = EnumIOMode.getNext(faceModes.get(facing));
				faceModes.put(facing, newMode);
			}
			if (!player.world.isRemote) {
				player.sendStatusMessage(new TextComponentTranslation("mm.message.screwdriver.module", new TextComponentTranslation(newMode.getUnlocalizedName())), true);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public EnumIOMode getMode(@Nullable EnumFacing facing) {
		if (facing == null) {
			return mode;
		}
		return faceModes.get(facing);
	}
	
	@Override
	public void setMode(@Nullable EnumFacing facing, EnumIOMode mode) {
		if (facing == null) {
			this.mode = mode;
			return;
		}
		faceModes.put(facing, mode);
	}
	
	@Override
	public void clearAllModes() {
		this.mode = EnumIOMode.IN_OUT;
		for (EnumFacing facing : EnumFacing.VALUES) {
			faceModes.put(facing, EnumIOMode.IN_OUT);
		}
	}
	
	@Override
	public boolean supportsMode(@Nullable EnumFacing facing, EnumIOMode mode) {
		return false;
	}
}
