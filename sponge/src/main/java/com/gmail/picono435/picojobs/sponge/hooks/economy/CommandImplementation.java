package com.gmail.picono435.picojobs.sponge.hooks.economy;

import com.gmail.picono435.picojobs.api.EconomyImplementation;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.api.field.type.StringRequiredFieldType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.exception.CommandException;

import java.util.List;
import java.util.UUID;

public class CommandImplementation extends EconomyImplementation {

    protected RequiredField<String, String> requiredField;

    public CommandImplementation() {
        this.requiredField = new RequiredField<>("commands", new StringRequiredFieldType(), true);
    }

    @Override
    public String getName() {
        return "COMMAND";
    }

    @Override
    public double getBalance(UUID player) {
        return 0D;
    }

    @Override
    public void deposit(UUID player, double amount) {
        JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        List<String> commands = this.requiredField.getValueList(jp.getJob());
        for(String command : commands) {
            try {
                Sponge.server().commandManager().process(Sponge.systemSubject(), command
                        .replace("%amount%", String.valueOf(amount))
                        .replace("%player%", Sponge.server().player(player).get().name())
                );
            } catch (CommandException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void withdraw(UUID player, double amount) {}

    @Override
    public RequiredField<String, String> getRequiredField() {
        return requiredField;
    }

}