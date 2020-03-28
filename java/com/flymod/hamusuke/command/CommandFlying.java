package com.flymod.hamusuke.command;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandFlying extends CommandBase {
	//private final List<String> aliases = Lists.newArrayList(Main.MODID, "allow", "noallow", "nogravity", "gravity", "setflyspeed");

	@Override
	public String getName() {
		return "fly";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "hamusuke.command.fly.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (args.length == 1) {
			return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
        }else {
        	return getListOfStringsMatchingLastWord(args, new String[] {"allow", "noallow", "nogravity", "gravity", "setflyspeed", "setwalkspeed"});
        }
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if(args.length <= 0 || args.length <= 1) {
            throw new WrongUsageException("hamusuke.command.fly.usage", new Object[0]);
        }else {

			EntityPlayer entityplayer = args.length >= 1 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

			if(args[1].equalsIgnoreCase("allow")) {
				entityplayer.capabilities.allowFlying = true;
				entityplayer.sendPlayerAbilities();

	            if (entityplayer == sender) {
	                entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.allow.self"));
	            }else {
	                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.allow.other", new Object[] {entityplayer.getName()});
	            }

				return;
			}

			if(args[1].equalsIgnoreCase("noallow")) {
				entityplayer.capabilities.allowFlying = false;
				entityplayer.capabilities.isFlying = false;
				entityplayer.sendPlayerAbilities();

				if (entityplayer == sender) {
					entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.noallow.self"));
	            }else {
	                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.noallow.other", new Object[] {entityplayer.getName()});
	            }

				return;
			}

			if(args[1].equalsIgnoreCase("nogravity")) {
				entityplayer.setNoGravity(true);

				if (entityplayer == sender) {
					entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.nogravity.self"));
	            }else {
	                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.nogravity.other", new Object[] {entityplayer.getName()});
	            }

				return;
			}

			if(args[1].equalsIgnoreCase("gravity")) {
				entityplayer.setNoGravity(false);

				if (entityplayer == sender) {
					entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.gravity.self"));
	            }else {
	                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.gravity.other", new Object[] {entityplayer.getName()});
	            }

				return;
			}

			if(args[1].equalsIgnoreCase("setflyspeed")) {

				if(!sender.getServer().isSinglePlayer()) {
					sender.sendMessage(new TextComponentTranslation("hamusuke.command.fly.error.setflyspeed"));
					return;
				}

				try {
					float flyspeed = Float.parseFloat(args[2]);
				}catch (NumberFormatException e) {
					sender.sendMessage(new TextComponentTranslation("hamusuke.command.fly.failed.setflyspeed"));
					return;
				}

				entityplayer.capabilities.setFlySpeed(Float.parseFloat(args[2]));
				entityplayer.sendPlayerAbilities();

				if (entityplayer == sender) {
					entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.setflyspeed.self", new Object[] {args[2]}));
	            }else {
	                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.setflyspeed.other", new Object[] {entityplayer.getName(), args[2]});
	            }

				return;
			}

			if(args[1].equalsIgnoreCase("setwalkspeed")) {

				if(!sender.getServer().isSinglePlayer()) {
					sender.sendMessage(new TextComponentTranslation("hamusuke.command.fly.error.setwalkspeed"));
					return;
				}

				try {
					float walkspeed = Float.parseFloat(args[2]);
				}catch (NumberFormatException e) {
					sender.sendMessage(new TextComponentTranslation("hamusuke.command.fly.failed.setwalkspeed"));
					return;
				}

				entityplayer.capabilities.setPlayerWalkSpeed(Float.parseFloat(args[2]));
				entityplayer.sendPlayerAbilities();

				if (entityplayer == sender) {
					entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.setwalkspeed.self", new Object[] {args[2]}));
	            }else {
	                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.setwalkspeed.other", new Object[] {entityplayer.getName(), args[2]});
	            }

				return;
			}

		}

	}

}
