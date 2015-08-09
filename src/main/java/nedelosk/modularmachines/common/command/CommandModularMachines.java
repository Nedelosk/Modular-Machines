package nedelosk.modularmachines.common.command;

import nedelosk.modularmachines.api.techtree.TechPointTypes;
import nedelosk.modularmachines.api.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.techtree.TechTreeCategoryList;
import nedelosk.modularmachines.api.techtree.TechTreeData;
import nedelosk.modularmachines.api.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.techtree.TechTreeManager;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.techtree.PacketSyncData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
public class CommandModularMachines
  extends CommandBase
{
  private List aliases;
  
  public CommandModularMachines()
  {
    this.aliases = new ArrayList();
    this.aliases.add("modularmachines");
    this.aliases.add("modular");
    this.aliases.add("mm");
  }
  
  @Override
public String getCommandName()
  {
    return "modularmachines";
  }
  
  @Override
public String getCommandUsage(ICommandSender icommandsender)
  {
    return "/modularmachines <action> [<player> [<params>]]";
  }
  
  @Override
public List getCommandAliases()
  {
    return this.aliases;
  }
  
  @Override
public int getRequiredPermissionLevel()
  {
    return 2;
  }
  
  @Override
public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
  {
    return null;
  }
  
  @Override
public boolean isUsernameIndex(String[] astring, int i)
  {
    return i == 1;
  }
  
  @Override
public void processCommand(ICommandSender icommandsender, String[] astring)
  {
    if (astring.length == 0)
    {
      icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));
      
      icommandsender.addChatMessage(new ChatComponentTranslation("§cUse /modularmachines help to get help", new Object[0]));
      
      return;
    }
    if (astring[0].equalsIgnoreCase("help"))
    {
      icommandsender.addChatMessage(new ChatComponentTranslation("§3You can also use /modular or /mm instead of /modularmachines.", new Object[0]));
      

      icommandsender.addChatMessage(new ChatComponentTranslation("§3Use this to give entry to a player.", new Object[0]));
      
      icommandsender.addChatMessage(new ChatComponentTranslation("  /modularmachines entry <list|player> <all|reset|<entry>>", new Object[0]));
      

      icommandsender.addChatMessage(new ChatComponentTranslation("§3Use this to give techpoints to a player.", new Object[0]));
      

      icommandsender.addChatMessage(new ChatComponentTranslation("/modularmachines techpoint <player> <pointType|all> <amount>", new Object[0]));
    }
    else if (astring.length >= 2)
    {
      if ((astring[0].equalsIgnoreCase("entry")) && (astring[1].equalsIgnoreCase("list")))
      {
        listEntrys(icommandsender);
      }
      else
      {
        EntityPlayerMP entityplayermp = getPlayer(icommandsender, astring[1]);
        if (astring[0].equalsIgnoreCase("entry"))
        {
          if (astring.length == 3)
          {
            if (astring[2].equalsIgnoreCase("all")) {
              giveAllEntrys(icommandsender, entityplayermp);
            } else if (astring[2].equalsIgnoreCase("reset")) {
              resetEntry(icommandsender, entityplayermp);
            } else {
              giveEntry(icommandsender, entityplayermp, astring[2]);
            }
          }
          else
          {
            icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));
            

            icommandsender.addChatMessage(new ChatComponentTranslation("§cUse /modularmachines research <list|player> <all|reset|entry>>", new Object[0]));
          }
        }
        else if (astring[0].equalsIgnoreCase("techpoint"))
        {
          if (astring.length == 4)
          {
            int i = parseIntWithMin(icommandsender, astring[3], 1);
            giveTechPoints(icommandsender, entityplayermp, astring[2], i);
          }
          else
          {
            icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));
            

            icommandsender.addChatMessage(new ChatComponentTranslation("§cUse /modularmachines techpoint <player> <pointType|all> <amount>", new Object[0]));
          }
        }
        else
        {
          icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));
          
          icommandsender.addChatMessage(new ChatComponentTranslation("§cUse /modularmachines help to get help", new Object[0]));
        }
      }
    }
    else
    {
      icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));
      
      icommandsender.addChatMessage(new ChatComponentTranslation("§cUse /modularmachines help to get help", new Object[0]));
    }
  }
  
  private void giveTechPoints(ICommandSender icommandsender, EntityPlayerMP player, String string, int i)
  {
    if (string.equalsIgnoreCase("all"))
    {
      for(TechPointTypes type : TechPointTypes.values())
    	  TechTreeManager.addTechPoints(player, i, type);
      player.addChatMessage(new ChatComponentTranslation("§5" + icommandsender.getCommandSenderName() + " gave you " + i + " of all the techpoints.", new Object[0]));
      

      icommandsender.addChatMessage(new ChatComponentTranslation("§5Success!", new Object[0]));
      
      PacketHandler.INSTANCE.sendTo(new PacketSyncData(player), player);
    }
    else
    {
      TechPointTypes type = null;
      for(TechPointTypes typeT : TechPointTypes.values())
    	  if(typeT.name().toLowerCase().equals(string))
    		  type = typeT;
      if (type == null) {
        for (TechPointTypes typeT : TechPointTypes.values()) {
          if (string.equalsIgnoreCase(typeT.name()))
          {
            type = typeT;
            break;
          }
        }
      }
      if (type != null)
      {
        TechTreeManager.addTechPoints(player, i, type);
        
        PacketHandler.INSTANCE.sendTo(new PacketSyncData(player), player);
        

        player.addChatMessage(new ChatComponentTranslation("§5" + icommandsender.getCommandSenderName() + " gave you " + i + " " + type.name(), new Object[0]));
        

        icommandsender.addChatMessage(new ChatComponentTranslation("§5Success!", new Object[0]));
      }
      else
      {
        icommandsender.addChatMessage(new ChatComponentTranslation("§cTechPoint Type does not exist.", new Object[0]));
      }
    }
  }
  
  private void listEntrys(ICommandSender icommandsender)
  {
    Collection<TechTreeCategoryList> rc = TechTreeCategories.entryCategories.values();
    for (TechTreeCategoryList cat : rc)
    {
      Collection<TechTreeEntry> rl = cat.entrys.values();
      for (TechTreeEntry tte : rl) {
        icommandsender.addChatMessage(new ChatComponentTranslation("§5" + tte.key, new Object[0]));
      }
    }
  }
  
  void giveEntry(ICommandSender icommandsender, EntityPlayerMP player, String key)
  {
    if (TechTreeCategories.getEntry(key) != null)
    {
      giveRecursiveEntry(player, key);
      PacketHandler.INSTANCE.sendTo(new PacketSyncData(player), player);
      
      player.addChatMessage(new ChatComponentTranslation("§5" + icommandsender.getCommandSenderName() + " gave you " + key + " research and its requisites.", new Object[0]));
      

      icommandsender.addChatMessage(new ChatComponentTranslation("§5Success!", new Object[0]));
    }
    else
    {
      icommandsender.addChatMessage(new ChatComponentTranslation("§cEntry does not exist.", new Object[0]));
    }
  }
  
  void giveRecursiveEntry(EntityPlayerMP player, String research)
  {
    if (!TechTreeManager.isEntryComplete(player, research))
    {
      TechTreeManager.completeEntry(player, research);
      if (TechTreeCategories.getEntry(research).parents != null) {
        for (String rsi : TechTreeCategories.getEntry(research).parents) {
          giveRecursiveEntry(player, rsi);
        }
      }
      if (TechTreeCategories.getEntry(research).siblings != null) {
        for (String rsi : TechTreeCategories.getEntry(research).siblings) {
          giveRecursiveEntry(player, rsi);
        }
      }
    }
  }
  
  void giveAllEntrys(ICommandSender icommandsender, EntityPlayerMP player)
  {
    Collection<TechTreeCategoryList> ec = TechTreeCategories.entryCategories.values();
    for (TechTreeCategoryList cat : ec)
    {
      Collection<TechTreeEntry> rl = cat.entrys.values();
      for (TechTreeEntry ri : rl) {
        if (!TechTreeManager.isEntryComplete(player, ri.key)) {
        	TechTreeManager.completeEntry(player, ri.key);
        }
      }
    }
    player.addChatMessage(new ChatComponentTranslation("§5" + icommandsender.getCommandSenderName() + " has given you all research.", new Object[0]));
    

    icommandsender.addChatMessage(new ChatComponentTranslation("§5Success!", new Object[0]));
    
    PacketHandler.INSTANCE.sendTo(new PacketSyncData(player), player);
  }
  
  void resetEntry(ICommandSender icommandsender, EntityPlayerMP player)
  {
    if(player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null)
    {
    	((TechTreeData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techEntrys = new ArrayList<>();
    }
    

    Collection<TechTreeCategoryList> ec = TechTreeCategories.entryCategories.values();
    for (TechTreeCategoryList cat : ec)
    {
      Collection<TechTreeEntry> res = cat.entrys.values();
      for (TechTreeEntry tte : res) {
        if (tte.isAutoUnlock()) {
          TechTreeManager.completeEntry(player, tte.key);
        }
      }
    }
    player.addChatMessage(new ChatComponentTranslation("§5" + icommandsender.getCommandSenderName() + " has reset you entry.", new Object[0]));
    

    icommandsender.addChatMessage(new ChatComponentTranslation("§5Success!", new Object[0]));
    
    PacketHandler.INSTANCE.sendTo(new PacketSyncData(player), player);
  }
}
