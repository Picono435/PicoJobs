package com.gmail.picono435.picojobs.mod.command;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;

import java.util.ArrayList;
import java.util.List;

public class ModJobsAdminCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("jobsadmin").requires(source -> ModSender.hasPermissionStatic(source.source, "picojobs.admin"))
                .then(
                        Commands.literal("help").executes(ModJobsAdminCommand::runCommand)
                ).then(
                        Commands.literal("info").then(
                                Commands.argument("player", GameProfileArgument.gameProfile())
                                        .executes(ModJobsAdminCommand::runCommand)
                        )
                ).then(
                        Commands.literal("reload").executes(ModJobsAdminCommand::runCommand)
                ).then(
                        Commands.literal("update").executes(ModJobsAdminCommand::runCommand)
                ).then(
                        Commands.literal("about").executes(ModJobsAdminCommand::runCommand)
                ).then(
                        Commands.literal("set").then(
                                Commands.literal("salary").then(
                                        Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                                Commands.argument("value", IntegerArgumentType.integer())
                                                        .executes(ModJobsAdminCommand::runCommand)
                                        )
                                )
                        ).then(
                                Commands.literal("method").then(
                                        Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                                Commands.argument("value", IntegerArgumentType.integer())
                                                        .executes(ModJobsAdminCommand::runCommand)
                                        )
                                )
                        ).then(
                                Commands.literal("job").then(
                                        Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                                Commands.argument("value", StringArgumentType.word())
                                                        .suggests((context, builder) -> context.getSource() != null ? SharedSuggestionProvider.suggest(PicoJobsCommon.getMainInstance().getJobsAdminCommand().getSetCommand().getTabCompletions("jobsadmin", new String[]{"set", "job", "player", ""}, new ModSender(context.getSource().source)), builder) : Suggestions.empty())
                                                        .executes(ModJobsAdminCommand::runCommand)
                                        )
                                )
                        )
                ).then(
                        Commands.literal("editor").executes(ModJobsAdminCommand::runCommand)
                ).then(
                        Commands.literal("applyedits").then(
                                Commands.argument("editor", StringArgumentType.word())
                                        .executes(ModJobsAdminCommand::runCommand)
                        )
                )
        );
    }

    public static int runCommand(CommandContext<CommandSourceStack> context) {
        try {
            List<String> nodes = new ArrayList<>();
            for(CommandNode<?> node : context.getNodes().stream().map(ParsedCommandNode::getNode).toList()) {
                if(node instanceof ArgumentCommandNode) {
                    ArgumentCommandNode<?, ?> argument = (ArgumentCommandNode<?, ?>) node;
                    if(argument.getType() instanceof GameProfileArgument) {
                        nodes.add(GameProfileArgument.getGameProfiles(context, node.getName()).stream().toList().get(0).getName());
                    } else if(argument.getType() instanceof IntegerArgumentType) {
                        nodes.add(String.valueOf(context.getArgument(node.getName(), Integer.class)));
                    } else if(argument.getType() instanceof StringArgumentType) {
                        nodes.add(context.getArgument(node.getName(), String.class));
                    }
                } else if(node instanceof LiteralCommandNode) {
                    nodes.add(node.getName());
                }
            }
            nodes.remove(0);
            PicoJobsCommon.getMainInstance().getJobsAdminCommand().onCommand(context.getNodes().get(0).getNode().getName(), nodes.toArray(new String[nodes.size()]), new ModSender(context.getSource().source));
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();;
            return 0;
        }
    }
}
