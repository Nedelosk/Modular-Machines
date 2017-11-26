package modularmachines.common.modules.components;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;

import modularmachines.api.EnumIOMode;
import modularmachines.api.IScrewdriver;
import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.IIOComponent;
import modularmachines.api.modules.components.IInteractionComponent;

public class IOComponent extends ModuleComponent implements IIOComponent, IInteractionComponent, INBTWritable, INBTReadable, INetworkComponent {
	private final Map<EnumFacing, EnumIOMode> faceModes;
	private EnumIOMode mode;
	
	public IOComponent() {
		this.faceModes = new EnumMap<>(EnumFacing.class);
		this.mode = EnumIOMode.NONE;
		for (EnumFacing facing : EnumFacing.VALUES) {
			faceModes.put(facing, EnumIOMode.NONE);
		}
	}
	
	@Override
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
		if (heldItem.getItem() instanceof IScrewdriver) {
			IScrewdriver screwdriver = (IScrewdriver) heldItem.getItem();
			if (player.isSneaking()) {
				clearAllModes();
				if (!player.world.isRemote) {
					player.sendStatusMessage(new TextComponentTranslation("mm.message.screwdriver.reset", new TextComponentTranslation(EnumIOMode.NONE.getUnlocalizedName())), true);
				}
				return true;
			}
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
		this.mode = EnumIOMode.NONE;
		for (EnumFacing facing : EnumFacing.VALUES) {
			faceModes.put(facing, EnumIOMode.NONE);
		}
	}
	
	@Override
	public boolean supportsMode(@Nullable EnumFacing facing, EnumIOMode mode) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
	
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return compound;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
	
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
	
	}
}
