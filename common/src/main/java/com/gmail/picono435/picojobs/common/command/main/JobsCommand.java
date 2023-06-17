package com.gmail.picono435.picojobs.common.command.main;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobsCommand implements Command {

    private HelpCommand helpCommand;
    private ChooseCommand chooseCommand;
    private WorkCommand workCommand;
    private SalaryCommand salaryCommand;
    private WithdrawCommand withdrawCommand;
    private LeaveJobCommand leaveJobCommand;

    public JobsCommand() {
        this.helpCommand = new HelpCommand();
        this.chooseCommand = new ChooseCommand();
        this.workCommand = new WorkCommand();
        this.salaryCommand = new SalaryCommand();
        this.withdrawCommand = new WithdrawCommand();
        this.leaveJobCommand = new LeaveJobCommand();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("jobs");
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        int action = PicoJobsAPI.getSettingsManager().getCommandAction();

        if(!sender.isPlayer()) return true;

        if(!sender.hasPermission("picojobs.use.basic")) {
            sender.sendMessage(LanguageManager.getMessage("no-permission"));
            return true;
        }

        if(action == 1) {
            sender.sendMessage(LanguageManager.getMessage("ignore-action", sender.getUUID()));
            return true;
        } else if(action == 2) {
            if(args.length < 1) {
                sender.sendMessage(LanguageManager.getMessage("member-commands", sender.getUUID()));
                return true;
            }

            if(this.helpCommand.getAliases().contains(args[0])) {
                return this.helpCommand.onCommand(cmd, args, sender);
            }

            if(this.chooseCommand.getAliases().contains(args[0])) {
                return this.chooseCommand.onCommand(cmd, args, sender);
            }

            if(this.workCommand.getAliases().contains(args[0])) {
                return this.workCommand.onCommand(cmd, args, sender);
            }

            if(this.salaryCommand.getAliases().contains(args[0])) {
                return this.workCommand.onCommand(cmd, args, sender);
            }

            if(this.withdrawCommand.getAliases().contains(args[0])) {
                return this.withdrawCommand.onCommand(cmd, args, sender);
            }

            if(this.leaveJobCommand.getAliases().contains(args[0])) {
                return this.withdrawCommand.onCommand(cmd, args, sender);
            }
        }

        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        List<String> tabCompletion = new ArrayList<>();
        if(args.length < 1) {
            tabCompletion.add("help");
            tabCompletion.add("choose");
            tabCompletion.add("work");
            tabCompletion.add("salary");
            tabCompletion.add("withdraw");
            tabCompletion.add("leave");
        } else {
            if(this.helpCommand.getAliases().contains(args[0])) {
                return this.helpCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.chooseCommand.getAliases().contains(args[0])) {
                return this.chooseCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.workCommand.getAliases().contains(args[0])) {
                return this.workCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.salaryCommand.getAliases().contains(args[0])) {
                return this.salaryCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.withdrawCommand.getAliases().contains(args[0])) {
                return this.withdrawCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.leaveJobCommand.getAliases().contains(args[0])) {
                return this.leaveJobCommand.getTabCompletions(cmd, args, sender);
            }
        }
        return tabCompletion;
    }
}
