package com.gmail.picono435.picojobs.mod.command;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.mod.PicoJobsExpected;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;

import java.util.ArrayList;
import java.util.List;

public class ModJobsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        System.out.println("REGISTERING: " + PicoJobsAPI.getSettingsManager().getCommandAction());
        if(PicoJobsAPI.getSettingsManager().getCommandAction() == 2) {
            dispatcher.register(Commands.literal("jobs").requires(source -> ModSender.hasPermissionStatic(source.source, "picojobs.use.basic"))
                    .then(
                            Commands.literal("help").executes(ModJobsCommand::runCommand)
                    ).then(
                            Commands.literal("choose").executes(ModJobsCommand::runCommand).then(
                                    Commands.argument("job", StringArgumentType.word())
                                            .suggests((context, builder) -> context.getSource() != null ? SharedSuggestionProvider.suggest(PicoJobsCommon.getMainInstance().getJobsCommand().getChooseCommand().getTabCompletions("jobs", new String[]{"choose", ""}, new ModSender(context.getSource().source)), builder) : Suggestions.empty())
                                            .executes(ModJobsCommand::runCommand)
                            )
                    ).then(
                            Commands.literal("work").executes(ModJobsCommand::runCommand)
                    ).then(
                            Commands.literal("salary").executes(ModJobsCommand::runCommand)
                    ).then(
                            Commands.literal("withdraw").executes(ModJobsCommand::runCommand)
                    ).then(
                            Commands.literal("leave").executes(ModJobsCommand::runCommand)
                    )
            );
        } else if(PicoJobsAPI.getSettingsManager().getCommandAction() > 2) {
            dispatcher.register(Commands.literal("jobs").requires(source -> ModSender.hasPermissionStatic(source.source, "picojobs.use.basic")).executes(ModJobsCommand::runCommand));
        }
    }

    public static int runCommand(CommandContext<CommandSourceStack> context) {
        try {
            List<String> nodes = new ArrayList<>();
            boolean b = false;
            int i = 0;
            for(String node : context.getNodes().stream().map(parsedCommandNode -> parsedCommandNode.getNode().getName()).toList()) {
                if(!b) {
                    b = true;
                    continue;
                }
                if(i > 0) {
                    nodes.add(context.getArgument(node, String.class));
                } else {
                    nodes.add(node);
                }
                i++;
            }
            System.out.println(nodes);
            PicoJobsCommon.getMainInstance().getJobsCommand().onCommand(context.getNodes().get(0).getNode().getName(), nodes.toArray(new String[nodes.size()]), new ModSender(context.getSource().source));
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();;
            return 0;
        }
    }

}
