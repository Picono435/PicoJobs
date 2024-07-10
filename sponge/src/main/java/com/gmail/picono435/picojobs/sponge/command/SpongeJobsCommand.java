package com.gmail.picono435.picojobs.sponge.command;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.sponge.platform.SpongeSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.api.command.*;
import org.spongepowered.api.command.parameter.ArgumentReader;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpongeJobsCommand implements Command.Raw {

    @Override
    public CommandResult process(CommandCause cause, ArgumentReader.Mutable arguments) {
        if(!(cause.audience() instanceof Player)) return CommandResult.error(Component.text("Player Only."));
        if(PicoJobsCommon.getMainInstance().getJobsCommand().onCommand("jobs", arguments.remaining().split(" "), new SpongeSender(cause.audience()))) {
            return CommandResult.success();
        } else {
            return CommandResult.error(Component.text("Unexpected error."));
        }
    }

    @Override
    public List<CommandCompletion> complete(CommandCause cause, ArgumentReader.Mutable arguments) {
        List<String> argumentList = new ArrayList<>(Arrays.asList(arguments.remaining().split(" ")));
        if(arguments.remaining().endsWith(" ")) argumentList.add("");
        return PicoJobsCommon.getMainInstance().getJobsCommand().getTabCompletions("jobs", argumentList.toArray(new String[0]), new SpongeSender(cause.audience()))
                .stream()
                .map(CommandCompletion::of)
                .collect(Collectors.toList());
    }

    @Override
    public boolean canExecute(CommandCause cause) {
        return cause.hasPermission("picojobs.use.basic");
    }

    @Override
    public Optional<Component> shortDescription(CommandCause cause) {
        return Optional.of(Component.text("Basic main command of PicoJobs"));
    }

    @Override
    public Optional<Component> extendedDescription(CommandCause cause) {
        return shortDescription(cause);
    }

    @Override
    public Component usage(CommandCause cause) {
        if(cause.audience() instanceof Player) return Component.text("Player Only");
        Player player = (Player) cause.audience();
        return LegacyComponentSerializer.legacyAmpersand().deserialize(LanguageManager.getMessage("member-commands", player.uniqueId()));
    }
}
