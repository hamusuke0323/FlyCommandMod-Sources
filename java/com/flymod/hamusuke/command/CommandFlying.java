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
        }else if(args.length == 2) {
        	return args.length == 2 ? getListOfStringsMatchingLastWord(args, new String[] {"allow", "noallow", "nogravity", "gravity", "setflyspeed", "setwalkspeed", "allowedit", "disabledamage", "isflying", "getflyspeed", "getwalkspeed"}) : Collections.emptyList();
        }else {
        	return args[1].equalsIgnoreCase("allowedit") || args[1].equalsIgnoreCase("disabledamage") || args[1].equalsIgnoreCase("isflying") ? getListOfStringsMatchingLastWord(args, new String[] {"true", "false"}) : Collections.emptyList();
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

			if(args[1].equalsIgnoreCase("allowedit")) {
				if(args[2].equalsIgnoreCase("true")) {
					entityplayer.capabilities.allowEdit = true;
					entityplayer.sendPlayerAbilities();
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.allowedit.true.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.allowedit.true.other", new Object[] {entityplayer.getName()});
		            }
				}else if (args[2].equalsIgnoreCase("false")) {
					entityplayer.capabilities.allowEdit = false;
					entityplayer.sendPlayerAbilities();
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.allowedit.false.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.allowedit.false.other", new Object[] {entityplayer.getName()});
		            }
				}else {
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.error.allowedit.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.error.allowedit.other");
		            }
				}
				return;
			}

			if(args[1].equalsIgnoreCase("disabledamage")) {
				if(args[2].equalsIgnoreCase("true")) {
					entityplayer.capabilities.disableDamage = true;
					entityplayer.sendPlayerAbilities();
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.disabledamage.true.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.disabledamage.true.other", new Object[] {entityplayer.getName()});
		            }
				}else if(args[2].equalsIgnoreCase("false")) {
					entityplayer.capabilities.disableDamage = false;
					entityplayer.sendPlayerAbilities();
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.disabledamage.false.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.disabledamage.false.other", new Object[] {entityplayer.getName()});
		            }
				}else {
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.error.disabledamage.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.error.disabledamage.other");
		            }
				}
				return;
			}

			if(args[1].equalsIgnoreCase("isflying")) {
				if(args[2].equalsIgnoreCase("true")) {
					entityplayer.capabilities.isFlying = true;
					entityplayer.sendPlayerAbilities();
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.isflying.true.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.isflying.true.other", new Object[] {entityplayer.getName()});
		            }
				}else if(args[2].equalsIgnoreCase("false")) {
					entityplayer.capabilities.isFlying = false;
					entityplayer.sendPlayerAbilities();
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.isflying.false.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.isflying.false.other", new Object[] {entityplayer.getName()});
		            }
				}else {
					if (entityplayer == sender) {
						entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.error.isflying.self"));
		            }else {
		                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.error.isflying.other");
		            }
				}
				return;
			}

			if(args[1].equalsIgnoreCase("getflyspeed")) {
				if (entityplayer == sender) {
					entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.getflyspeed.self", new Object[] {entityplayer.capabilities.getFlySpeed()}));
	            }else {
	                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.getflyspeed.other", new Object[] {entityplayer.getName(), entityplayer.capabilities.getFlySpeed()});
	            }
				return;
			}

			if(args[1].equalsIgnoreCase("getwalkspeed")) {
				if (entityplayer == sender) {
					entityplayer.sendMessage(new TextComponentTranslation("hamusuke.command.fly.success.getwalkspeed.self", new Object[] {entityplayer.capabilities.getWalkSpeed()}));
	            }else {
	                notifyCommandListener(sender, this, 1, "hamusuke.command.fly.success.getwalkspeed.other", new Object[] {entityplayer.getName(), entityplayer.capabilities.getWalkSpeed()});
	            }
				return;
			}

		}

	}

}
