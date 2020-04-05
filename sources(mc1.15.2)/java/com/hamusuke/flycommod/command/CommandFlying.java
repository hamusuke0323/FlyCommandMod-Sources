package com.hamusuke.flycommod.command;

import java.util.Collection;

import com.hamusuke.flycommod.command.type.FlyType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandFlying {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
	   LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("fly").requires((permission) -> {
	      return permission.hasPermissionLevel(2);
	   });

	   for(FlyType flytype : FlyType.values()) {
	      if (flytype != FlyType.NOT_SET) {
	         literalargumentbuilder.then(Commands.argument("target", EntityArgument.players()).then(Commands.literal(flytype.getName()).executes((flycommandsrc) -> {
	        	 if(flytype == FlyType.ALLOW || flytype == FlyType.NOALLOW || flytype == FlyType.NOGRAVITY || flytype == FlyType.GRAVITY || flytype == FlyType.GETFLYSPEED || flytype == FlyType.GETWALKSPEED) {
	        		return executeflycommand1st(flycommandsrc, EntityArgument.getPlayers(flycommandsrc, "target"), flytype);
	        	 }else return 0;
	         }).then(Commands.argument("speed(decimals) or true | false", StringArgumentType.greedyString()).executes((flycommandsrc2) -> {
	        	 if(flytype == FlyType.SETFLYSPEED || flytype == FlyType.SETWALKSPEED || flytype == FlyType.ALLOWEDIT || flytype == FlyType.DISABLEDAMAGE || flytype == FlyType.ISFLYING) {
	        		 return executeflycommand2nd(flycommandsrc2, EntityArgument.getPlayers(flycommandsrc2, "target"), flytype, StringArgumentType.getString(flycommandsrc2, "speed(decimals) or true | false"));
	        	 }else return 0;
	         }))));
	      }
	   }
	   dispatcher.register(literalargumentbuilder);
	}

	private static int executeflycommand1st(CommandContext<CommandSource> source, Collection<ServerPlayerEntity> players, FlyType type) {
		int i = 0;

		for(ServerPlayerEntity serverplayerentity : players) {
			if(FlyType.ALLOW == type) {
				serverplayerentity.abilities.allowFlying = true;
				serverplayerentity.sendPlayerAbilities();
				if(source.getSource().getEntity() == players) {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.allow.self"), true);
				}else {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.allow.other", serverplayerentity.getDisplayName()), true);
				}
				++i;
			}

			if(FlyType.NOALLOW == type) {
				serverplayerentity.abilities.allowFlying = false;
				serverplayerentity.abilities.isFlying = false;
				serverplayerentity.sendPlayerAbilities();
				if(source.getSource().getEntity() == players) {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.noallow.self"), true);
				}else {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.noallow.other", serverplayerentity.getDisplayName()), true);
				}
				++i;
			}

			if(FlyType.NOGRAVITY == type) {
				serverplayerentity.setNoGravity(true);
				if(source.getSource().getEntity() == players) {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.nogravity.self"), true);
				}else {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.nogravity.other", serverplayerentity.getDisplayName()), true);
				}
				++i;
			}

			if(FlyType.GRAVITY == type) {
				serverplayerentity.setNoGravity(false);
				if(source.getSource().getEntity() == players) {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.gravity.self"), true);
				}else {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.gravity.other", serverplayerentity.getDisplayName()), true);
				}
				++i;
			}

			if(FlyType.GETFLYSPEED == type) {
				if(source.getSource().getEntity() == players) {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.getflyspeed.self", serverplayerentity.abilities.getFlySpeed()), true);
				}else {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.getflyspeed.other", serverplayerentity.getDisplayName(), serverplayerentity.abilities.getFlySpeed()), true);
				}
				++i;
			}

			if(FlyType.GETWALKSPEED == type) {
				if(source.getSource().getEntity() == players) {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.getwalkspeed.self", serverplayerentity.abilities.getWalkSpeed()), true);
				}else {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.getwalkspeed.other", serverplayerentity.getDisplayName(), serverplayerentity.abilities.getWalkSpeed()), true);
				}
				++i;
			}
		}

		return i;
	}

	private static int executeflycommand2nd(CommandContext<CommandSource> source, Collection<ServerPlayerEntity> players, FlyType type, String args) {
		int i = 0;

		for(ServerPlayerEntity serverPlayerEntity : players) {
			if(FlyType.SETFLYSPEED == type && serverPlayerEntity.server.isSinglePlayer()) {
				try {
					Float flyspeed = Float.parseFloat(args);
				}catch (NumberFormatException e) {
					source.getSource().sendErrorMessage(new TranslationTextComponent("hamusuke.command.fly.failed.setflyspeed"));
					return 1;
				}

				serverPlayerEntity.abilities.setFlySpeed(Float.parseFloat(args));
				serverPlayerEntity.sendPlayerAbilities();
				if(source.getSource().getEntity() == players) {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.setflyspeed.self", args), true);
				}else {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.setflyspeed.other", serverPlayerEntity.getDisplayName(), args), true);
				}
				++i;
			}

			if(FlyType.SETFLYSPEED == type && !serverPlayerEntity.server.isSinglePlayer()) {
				source.getSource().sendErrorMessage(new TranslationTextComponent("hamusuke.command.fly.error.setflyspeed"));
				++i;
			}

			if(FlyType.SETWALKSPEED == type && serverPlayerEntity.server.isSinglePlayer()) {
				try {
					Float walkspeed = Float.parseFloat(args);
				}catch (NumberFormatException e) {
					source.getSource().sendErrorMessage(new TranslationTextComponent("hamusuke.command.fly.failed.setwalkspeed"));
					return 1;
				}
				serverPlayerEntity.abilities.setWalkSpeed(Float.parseFloat(args));
				serverPlayerEntity.sendPlayerAbilities();
				if(source.getSource().getEntity() == players) {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.setwalkspeed.self", args), true);
				}else {
					source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.setwalkspeed.other", serverPlayerEntity.getDisplayName(), args), true);
				}
				++i;
			}

			if(FlyType.SETWALKSPEED == type && !serverPlayerEntity.server.isSinglePlayer()) {
				source.getSource().sendErrorMessage(new TranslationTextComponent("hamusuke.command.fly.error.setwalkspeed"));
				++i;
			}

			if(FlyType.ALLOWEDIT == type) {
				if(args.equalsIgnoreCase("true")) {
					serverPlayerEntity.abilities.allowEdit = true;
					serverPlayerEntity.sendPlayerAbilities();
					if(source.getSource().getEntity() == players) {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.allowedit.true.self"), true);
					}else {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.allowedit.true.other", serverPlayerEntity.getDisplayName()), true);
					}
				}else if(args.equalsIgnoreCase("false")) {
					serverPlayerEntity.abilities.allowEdit = false;
					serverPlayerEntity.sendPlayerAbilities();
					if(source.getSource().getEntity() == players) {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.allowedit.false.self"), true);
					}else {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.allowedit.false.other", serverPlayerEntity.getDisplayName()), true);
					}
				}else {
					source.getSource().sendErrorMessage(new TranslationTextComponent("hamusuke.command.fly.error.allowedit.self"));
				}
				++i;
			}

			if(FlyType.DISABLEDAMAGE == type) {
				if(args.equalsIgnoreCase("true")) {
					serverPlayerEntity.abilities.disableDamage = true;
					serverPlayerEntity.sendPlayerAbilities();
					if(source.getSource().getEntity() == players) {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.disabledamage.true.self"), true);
					}else {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.disabledamage.true.other", serverPlayerEntity.getDisplayName()), true);
					}
				}else if(args.equalsIgnoreCase("false")) {
					serverPlayerEntity.abilities.disableDamage = false;
					serverPlayerEntity.sendPlayerAbilities();
					if(source.getSource().getEntity() == players) {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.disabledamage.false.self"), true);
					}else {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.disabledamage.false.other", serverPlayerEntity.getDisplayName()), true);
					}
				}else {
					source.getSource().sendErrorMessage(new TranslationTextComponent("hamusuke.command.fly.error.disabledamage.self"));
				}
				++i;
			}

			if(FlyType.ISFLYING == type) {
				if(args.equalsIgnoreCase("true")) {
					serverPlayerEntity.abilities.isFlying = true;
					serverPlayerEntity.sendPlayerAbilities();
					if(source.getSource().getEntity() == players) {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.isflying.true.self"), true);
					}else {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.isflying.true.other", serverPlayerEntity.getDisplayName()), true);
					}
				}else if(args.equalsIgnoreCase("false")) {
					serverPlayerEntity.abilities.isFlying = false;
					serverPlayerEntity.sendPlayerAbilities();
					if(source.getSource().getEntity() == players) {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.isflying.false.self"), true);
					}else {
						source.getSource().sendFeedback(new TranslationTextComponent("hamusuke.command.fly.success.isflying.false.other", serverPlayerEntity.getDisplayName()), true);
					}
				}else {
					source.getSource().sendErrorMessage(new TranslationTextComponent("hamusuke.command.fly.error.isflying.self"));
				}
				++i;
			}
		}
		return i;
	}
}
