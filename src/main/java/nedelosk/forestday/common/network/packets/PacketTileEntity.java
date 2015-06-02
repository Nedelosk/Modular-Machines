package nedelosk.forestday.common.network.packets;

import nedelosk.nedeloskcore.common.core.Log;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketTileEntity<T extends TileEntity> implements IMessage {
	
	 protected int x;
	  protected int y;
	  protected int z;

	  private Class<? extends TileEntity> tileClass;

	  protected PacketTileEntity() {
	  }

	  protected PacketTileEntity(T tile) {
	    tileClass = tile.getClass();
	    x = tile.xCoord;
	    y = tile.yCoord;
	    z = tile.zCoord;
	  }

	  public void toBytes(ByteBuf buf) {
	    buf.writeInt(x);
	    buf.writeInt(y);
	    buf.writeInt(z);
	    ByteBufUtils.writeUTF8String(buf, tileClass.getName());

	  }

	  @Override
	  public void fromBytes(ByteBuf buf) {
	    x = buf.readInt();
	    y = buf.readInt();
	    z = buf.readInt();
	    String str = ByteBufUtils.readUTF8String(buf);
	    try {
	      tileClass = (Class<TileEntity>) Class.forName(str);
	    } catch (Exception e) {
	      Log.err("Not load tile entity class: " + str);
	    }
	  }

	  protected T getTileEntity(World worldObj) {
	    if(worldObj == null) {
	      return null;
	    }
	    TileEntity te = worldObj.getTileEntity(x, y, z);
	    if(te == null) {
	      return null;
	    }
	    if(tileClass.isAssignableFrom(te.getClass())) {
	      return (T) te;
	    }
	    return null;
	  }

	  protected World getWorld(MessageContext ctx) {
	      return ctx.getServerHandler().playerEntity.worldObj;
	  }
}
