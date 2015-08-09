package nedelosk.nedeloskcore.common.network.packets;

import nedelosk.nedeloskcore.common.core.Log;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

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

	  @Override
	public void toBytes(ByteBuf buf) {
	    buf.writeInt(x);
	    buf.writeInt(y);
	    buf.writeInt(z);
		buf.writeInt(tileClass.getName().getBytes().length);
		buf.writeBytes(tileClass.getName().getBytes());

	  }

	  @Override
	  public void fromBytes(ByteBuf buf) {
	    x = buf.readInt();
	    y = buf.readInt();
	    z = buf.readInt();
	    String str = null;
		try
		{
		byte[] bytes = new byte[buf.readInt()];
		buf.readBytes(bytes);
		str = new String(bytes, StandardCharsets.UTF_8);
		}catch(Exception e){
			
		}
	    try {
	      tileClass = (Class<TileEntity>) Class.forName(str);
	    } catch (Exception e) {
	      Log.err("Not load tile entity class: " + str);
	    }
	  }

	  public T getTileEntity(World worldObj) {
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
