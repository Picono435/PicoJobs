package com.gmail.picono435.picojobs.common.command.main;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

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
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        int action = PicoJobsAPI.getSettingsManager().getCommandAction();

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
            String helpString = LanguageManager.getSubCommandAlias("help");
            String chooseString = LanguageManager.getSubCommandAlias("choose");
            String workString = LanguageManager.getSubCommandAlias("work");
            String salaryString = LanguageManager.getSubCommandAlias("salary");
            String withdrawString = LanguageManager.getSubCommandAlias("withdraw");
            String leaveString = LanguageManager.getSubCommandAlias("leave");

            if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase(helpString)) {
                return this.helpCommand.onCommand(cmd, args, sender);
            }

            if(args[0].equalsIgnoreCase("choose") || args[0].equalsIgnoreCase(chooseString)) {
                return this.chooseCommand.onCommand(cmd, args, sender);
            }

            if(args[0].equalsIgnoreCase("work") || args[0].equalsIgnoreCase(workString)) {
                return this.workCommand.onCommand(cmd, args, sender);
            }

            if(args[0].equalsIgnoreCase("salary") || args[0].equalsIgnoreCase(salaryString)) {
                return this.workCommand.onCommand(cmd, args, sender);
            }

            if(args[0].equalsIgnoreCase("withdraw") || args[0].equalsIgnoreCase(withdrawString)) {
                return this.withdrawCommand.onCommand(cmd, args, sender);
            }

            if(args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase(leaveString)) {
                return this.withdrawCommand.onCommand(cmd, args, sender);
            }
        }

        return false;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }
}
