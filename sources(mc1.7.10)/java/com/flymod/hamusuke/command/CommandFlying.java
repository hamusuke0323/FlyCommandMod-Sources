package com.flymod.hamusuke.command;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandFlying extends CommandBase {

	@Override
	public String getCommandName() {
		return "fly";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "hamusuke.command.fly.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length <= 0 || args.length <= 1) {
            throw new WrongUsageException("hamusuke.command.fly.usage", new Object[0]);
        }else {

			EntityPlayerMP entityplayermp = args.length >= 2 ? getPlayer(sender, args[0]) : getCommandSenderAsPlayer(sender);

			if(args[1].equalsIgnoreCase("allow")) {
				entityplayermp.capabilities.allowFlying = true;
				entityplayermp.sendPlayerAbilities();

	            if(entityplayermp == sender) {
	            	func_152373_a(sender, this, "hamusuke.command.fly.success.allow.self");
	            }else {
	            	func_152374_a(sender, this, 1, "hamusuke.command.fly.success.allow.other", new Object[] {entityplayermp.getCommandSenderName()});
	            }

				return;
			}

			if(args[1].equalsIgnoreCase("noallow")) {
				entityplayermp.capabilities.allowFlying = false;
				entityplayermp.capabilities.isFlying = false;
				entityplayermp.sendPlayerAbilities();

				if(entityplayermp == sender) {
					func_152373_a(sender, this, "hamusuke.command.fly.success.noallow.self");
				}else {
					func_152374_a(sender, this, 1, "hamusuke.command.fly.success.noallow.other", new Object[] {entityplayermp.getCommandSenderName()});
				}

				return;
			}

			if(args[1].equalsIgnoreCase("nogravity")) {
				if(entityplayermp == sender) {
					func_152373_a(sender, this, "hamusuke.command.fly.error.gravity");
				}else {
					func_152374_a(sender, this, 1, "hamusuke.command.fly.error.gravity");
				}

				return;
			}

			if(args[1].equalsIgnoreCase("gravity")) {
				if(entityplayermp == sender) {
					func_152373_a(sender, this, "hamusuke.command.fly.error.gravity");
				}else {
					func_152374_a(sender, this, 1, "hamusuke.command.fly.error.gravity");
				}

				return;
			}

			if(args[1].equalsIgnoreCase("setflyspeed")) {

				if(!MinecraftServer.getServer().isSinglePlayer()) {
					if(entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.error.setflyspeed");
					}else {
						func_152374_a(sender, this, 1, "hamusuke.command.fly.error.setflyspeed");
					}
					return;
				}

				try {
					float flyspeed = Float.parseFloat(args[2]);
				}catch (NumberFormatException e) {
					if(entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.failed.setflyspeed");
					}else {
						func_152374_a(sender, this, 1, "hamusuke.command.fly.failed.setflyspeed");
					}
					return;
				}

				entityplayermp.capabilities.setFlySpeed(Float.parseFloat(args[2]));
				entityplayermp.sendPlayerAbilities();

				if(entityplayermp == sender) {
					func_152373_a(sender, this, "hamusuke.command.fly.success.setflyspeed.self", new Object[] {args[2]});
				}else {
					func_152374_a(sender, this, 1, "hamusuke.command.fly.success.setflyspeed.other", new Object[] {entityplayermp.getCommandSenderName(), args[2]});
				}

				return;
			}

			if(args[1].equalsIgnoreCase("setwalkspeed")) {

				if(!MinecraftServer.getServer().isSinglePlayer()) {
					if(entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.error.setwalkspeed");
					}else {
						func_152374_a(sender, this, 1, "hamusuke.command.fly.error.setwalkspeed");
					}
					return;
				}

				try {
					float flyspeed = Float.parseFloat(args[2]);
				}catch (NumberFormatException e) {
					if(entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.failed.setwalkspeed");
					}else {
						func_152374_a(sender, this, 1, "hamusuke.command.fly.failed.setwalkspeed");
					}
					return;
				}

				entityplayermp.capabilities.setPlayerWalkSpeed(Float.parseFloat(args[2]));
				entityplayermp.sendPlayerAbilities();

				if(entityplayermp == sender) {
					func_152373_a(sender, this, "hamusuke.command.fly.success.setwalkspeed.self", new Object[] {args[2]});
				}else {
					func_152374_a(sender, this, 1, "hamusuke.command.fly.success.setwalkspeed.other", new Object[] {entityplayermp.getCommandSenderName(), args[2]});
				}

				return;
			}

			if(args[1].equalsIgnoreCase("allowedit")) {
				if(args[2].equalsIgnoreCase("true")) {
					entityplayermp.capabilities.allowEdit = true;
					entityplayermp.sendPlayerAbilities();
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.success.allowedit.true.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.success.allowedit.true.other", new Object[] {entityplayermp.getCommandSenderName()});
		            }
				}else if (args[2].equalsIgnoreCase("false")) {
					entityplayermp.capabilities.allowEdit = false;
					entityplayermp.sendPlayerAbilities();
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.success.allowedit.false.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.success.allowedit.false.other", new Object[] {entityplayermp.getCommandSenderName()});
		            }
				}else {
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.error.allowedit.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.error.allowedit.other");
		            }
				}
				return;
			}

			if(args[1].equalsIgnoreCase("disabledamage")) {
				if(args[2].equalsIgnoreCase("true")) {
					entityplayermp.capabilities.disableDamage = true;
					entityplayermp.sendPlayerAbilities();
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.success.disabledamage.true.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.success.disabledamage.true.other", new Object[] {entityplayermp.getCommandSenderName()});
		            }
				}else if(args[2].equalsIgnoreCase("false")) {
					entityplayermp.capabilities.disableDamage = false;
					entityplayermp.sendPlayerAbilities();
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.success.disabledamage.false.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.success.disabledamage.false.other", new Object[] {entityplayermp.getCommandSenderName()});
		            }
				}else {
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.error.disabledamage.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.error.disabledamage.other");
		            }
				}
				return;
			}

			if(args[1].equalsIgnoreCase("isflying")) {
				if(args[2].equalsIgnoreCase("true")) {
					entityplayermp.capabilities.isFlying = true;
					entityplayermp.sendPlayerAbilities();
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.success.isflying.true.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.success.isflying.true.other", new Object[] {entityplayermp.getCommandSenderName()});
		            }
				}else if(args[2].equalsIgnoreCase("false")) {
					entityplayermp.capabilities.isFlying = false;
					entityplayermp.sendPlayerAbilities();
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.success.isflying.false.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.success.isflying.false.other", new Object[] {entityplayermp.getCommandSenderName()});
		            }
				}else {
					if (entityplayermp == sender) {
						func_152373_a(sender, this, "hamusuke.command.fly.error.isflying.self");
		            }else {
		                func_152374_a(sender, this, 1, "hamusuke.command.fly.error.isflying.other");
		            }
				}
				return;
			}

			if(args[1].equalsIgnoreCase("getflyspeed")) {
				if (entityplayermp == sender) {
					func_152373_a(sender, this, "hamusuke.command.fly.success.getflyspeed.self", new Object[] {entityplayermp.capabilities.getFlySpeed()});
	            }else {
	                func_152374_a(sender, this, 1, "hamusuke.command.fly.success.getflyspeed.other", new Object[] {entityplayermp.getCommandSenderName(), entityplayermp.capabilities.getFlySpeed()});
	            }
				return;
			}

			if(args[1].equalsIgnoreCase("getwalkspeed")) {
				if (entityplayermp == sender) {
					func_152373_a(sender, this, "hamusuke.command.fly.success.getwalkspeed.self", new Object[] {entityplayermp.capabilities.getWalkSpeed()});
	            }else {
	                func_152374_a(sender, this, 1, "hamusuke.command.fly.success.getwalkspeed.other", new Object[] {entityplayermp.getCommandSenderName(), entityplayermp.capabilities.getWalkSpeed()});
	            }
				return;
			}
        }
	}

    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
    	if (args.length == 1) {
			return args.length == 1 ? getListOfStringsMatchingLastWord(args, this.getListOfPlayerUsernames()) : Collections.emptyList();
        }else if(args.length == 2) {
        	return args.length == 2 ? getListOfStringsMatchingLastWord(args, new String[] {"allow", "noallow", "setflyspeed", "setwalkspeed", "allowedit", "disabledamage", "isflying", "getflyspeed", "getwalkspeed"}) : Collections.emptyList();
        }else {
        	return args[1].equalsIgnoreCase("allowedit") || args[1].equalsIgnoreCase("disabledamage") || args[1].equalsIgnoreCase("isflying") ? getListOfStringsMatchingLastWord(args, new String[] {"true", "false"}) : Collections.emptyList();
        }
    }

    protected String[] getListOfPlayerUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    public boolean isUsernameIndex(String[] args, int index) {
        return index == 1;
    }

}
