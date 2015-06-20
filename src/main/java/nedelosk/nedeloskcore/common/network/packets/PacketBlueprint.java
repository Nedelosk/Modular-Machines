package nedelosk.nedeloskcore.common.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketBlueprint implements IMessage {

	public ItemStack blueprint;
	
	public PacketBlueprint() {
		super();
	}
	
	public PacketBlueprint(ItemStack blueprint) {
		this.blueprint = blueprint;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		blueprint = new ItemStack(Item.getItemById(buf.readInt()), buf.readShort(), buf.readInt());
		try {
			blueprint.stackTagCompound = readNBTTagCompound(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(Item.getIdFromItem(blueprint.getItem()));
		buf.writeShort(blueprint.stackSize);
		buf.writeInt(blueprint.getItemDamage());
		try {
			writeNBTTagCompound(blueprint.stackTagCompound, buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static void writeNBTTagCompound(
			   NBTTagCompound par0NBTTagCompound, ByteBuf buf)
			   throws IOException {
			  if (par0NBTTagCompound == null) {
			   buf.writeShort(-1);
			  } else {
			   byte[] abyte = CompressedStreamTools.compress(par0NBTTagCompound);
			   buf.writeShort((short) abyte.length);
			   buf.writeBytes(abyte);
			   System.out
				 .println("[SENDING]abyte length: " + (short) abyte.length);
			   System.out.println("[SENDING]abyte: " + abyte);
			  }
	}
	
	public static NBTTagCompound readNBTTagCompound(ByteBuf buf)
			   throws IOException {
			  short short1 = buf.readShort();
			  System.out.println("[RECIEVING]abyte length: " + short1);
			  if (short1 < 0) {
			   return null;
			  } else {
			   byte[] abyte = new byte[short1];
			   buf.readBytes(abyte);
			   return CompressedStreamTools.func_152457_a(abyte, NBTSizeTracker.field_152451_a);
			  }
			}

}
