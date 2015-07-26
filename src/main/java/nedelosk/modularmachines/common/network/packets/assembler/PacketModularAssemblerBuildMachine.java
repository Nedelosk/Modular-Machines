package nedelosk.modularmachines.common.network.packets.assembler;

import java.util.ArrayList;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.modular.ModularMachine;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.nedeloskcore.common.network.packets.PacketTileEntity;
import nedelosk.nedeloskcore.utils.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularAssemblerBuildMachine extends PacketTileEntity<TileModularAssenbler> implements IMessageHandler<PacketModularAssemblerBuildMachine, IMessage> {
	
	public PacketModularAssemblerBuildMachine() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
	}

	public PacketModularAssemblerBuildMachine(TileModularAssenbler tile) {
		super(tile);
	}
	
	@Override
	public IMessage onMessage(PacketModularAssemblerBuildMachine message, MessageContext ctx) {
		TileModularAssenbler tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
		ArrayList<String> requiredModule = new ArrayList<String>(ModularMachinesApi.getRequiredModule());
		ModularMachine machine = tile.buildMachine();
		if(machine != null)
		{
			ItemStack stack = MMBlocks.Modular_Machine.getItemStack();
			stack.setTagCompound(new NBTTagCompound());
			machine.writeToNBT(stack.getTagCompound());
			for(int i = 0;i < ctx.getServerHandler().playerEntity.inventory.mainInventory.length;i++)
			{
				if(ctx.getServerHandler().playerEntity.inventory.mainInventory[i] == null)
				{
					ctx.getServerHandler().playerEntity.inventory.mainInventory[i] = stack.copy();
					stack = null;
					break;
				}
				
			}
			if(stack != null)
				ItemUtils.dropItem(ctx.getServerHandler().playerEntity.worldObj, message.x, message.y, message.z, stack);
			for(Map.Entry<String, ItemStack[]> entry : tile.slot.entrySet())
			{
				for(int i = 0;i < entry.getValue().length;i++)
					tile.setInventorySlotContents(entry.getKey(), i, null);
			}
			getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
		}
		else
		{
			//tile.buildMachine(requiredModule);
			ctx.getServerHandler().playerEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Fail to build machine."));
			/*for(String s : requiredModule)
			{
				ctx.getServerHandler().playerEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Fail to find " + s + "."));
			}*/
		}
		return null;
	}

}
