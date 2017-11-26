package modularmachines.common.modules.components;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
		provider.sendToClient();
	}
	
	@Override
	public boolean supportsMode(@Nullable EnumFacing facing, EnumIOMode mode) {
		EnumIOMode componentMode = getMode(facing);
		if (componentMode == EnumIOMode.DISABLED) {
			return false;
		}
		switch (mode) {
			case DISABLED:
				return false;
			case INPUT:
				return componentMode.acceptsInput();
			case OUTPUT:
				return componentMode.acceptsOutput();
			case PUSH:
				return componentMode.pushes();
			case PULL:
				return componentMode.pulls();
			case NONE:
			case PUSH_PULL:
				return componentMode == mode;
		}
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList tagList = compound.getTagList("Facings", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte facing = tagCompound.getByte("Facing");
			byte mode = tagCompound.getByte("Mode");
			faceModes.put(EnumFacing.getFront(facing), EnumIOMode.get(mode));
		}
		mode = EnumIOMode.get(compound.getByte("Mode"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList tagList = new NBTTagList();
		for (Map.Entry<EnumFacing, EnumIOMode> entry : faceModes.entrySet()) {
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setByte("Facing", (byte) entry.getKey().ordinal());
			tagCompound.setByte("Mode", (byte) entry.getValue().ordinal());
			tagList.appendTag(tagCompound);
		}
		compound.setByte("Mode", (byte) mode.ordinal());
		return compound;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		for (Map.Entry<EnumFacing, EnumIOMode> entry : faceModes.entrySet()) {
			data.writeByte(entry.getValue().ordinal());
		}
		data.writeByte(mode.ordinal());
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		for (EnumFacing facing : EnumFacing.VALUES) {
			faceModes.put(facing, EnumIOMode.get(data.readByte()));
		}
		mode = EnumIOMode.get(data.readByte());
	}
}
