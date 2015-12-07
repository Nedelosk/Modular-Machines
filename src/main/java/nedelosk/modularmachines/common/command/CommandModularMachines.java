package nedelosk.modularmachines.common.command;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;

public class CommandModularMachines extends CommandBase {
	private List aliases;

	public CommandModularMachines() {
		this.aliases = new ArrayList();
		this.aliases.add("modularmachines");
		this.aliases.add("modular");
		this.aliases.add("mm");
	}

	@Override
	public String getCommandName() {
		return "modularmachines";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/modularmachines <action> [<player> [<params>]]";
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return i == 1;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (astring.length == 0) {
			icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));

			icommandsender.addChatMessage(
					new ChatComponentTranslation("§cUse /modularmachines help to get help", new Object[0]));

			return;
		}
		if (astring[0].equalsIgnoreCase("help")) {
			icommandsender.addChatMessage(new ChatComponentTranslation(
					"§3You can also use /modular or /mm instead of /modularmachines.", new Object[0]));

			icommandsender.addChatMessage(
					new ChatComponentTranslation("§3Use this to give entry to a player.", new Object[0]));

			icommandsender.addChatMessage(new ChatComponentTranslation(
					"  /modularmachines entry <list|player> <all|reset|<entry>>", new Object[0]));

			icommandsender.addChatMessage(
					new ChatComponentTranslation("§3Use this to give techpoints to a player.", new Object[0]));

			icommandsender.addChatMessage(new ChatComponentTranslation(
					"/modularmachines techpoint <player> <pointType|all> <amount>", new Object[0]));
		} else {
			icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));

			icommandsender.addChatMessage(
					new ChatComponentTranslation("§cUse /modularmachines help to get help", new Object[0]));
		}
	}
}
