package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.command.admin.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobsAdminCommand implements Command {
    private HelpCommand helpCommand;
    private InfoCommand infoCommand;
    private ReloadCommand reloadCommand;
    private UpdateCommand updateCommand;
    private AboutCommand aboutCommand;
    private SetCommand setCommand;
    private EditorCommand editorCommand;
    private ApplyEditsCommand applyEditsCommand;

    public JobsAdminCommand() {
        this.helpCommand = new HelpCommand();
        this.infoCommand = new InfoCommand();
        this.reloadCommand = new ReloadCommand();
        this.updateCommand = new UpdateCommand();
        this.aboutCommand = new AboutCommand();
        this.setCommand = new SetCommand();
        this.editorCommand = new EditorCommand();
        this.applyEditsCommand = new ApplyEditsCommand();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("jobsadmin");
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        if(!sender.hasPermission("picojobs.admin")) {
            sender.sendMessage(LanguageManager.formatMessage("&7PicoJobs v" + PicoJobsCommon.getVersion() + ". (&8&nhttps://discord.gg/wQj53Hy&r&7)"));
            return true;
        }
        if(args.length < 1) {
            sender.sendMessage(LanguageManager.getFormat("admin-commands", null));
            return true;
        }
        String subcmd = args[0];

        if(this.helpCommand.getAliases().contains(subcmd)) {
            return this.helpCommand.onCommand(cmd, args, sender);
        }

        if(this.infoCommand.getAliases().contains(subcmd)) {
            return this.infoCommand.onCommand(cmd, args, sender);
        }

        if(this.reloadCommand.getAliases().contains(subcmd)) {
            return this.reloadCommand.onCommand(cmd, args, sender);
        }

        if(this.updateCommand.getAliases().contains(subcmd)) {
            return this.updateCommand.onCommand(cmd, args, sender);
        }

        if(this.aboutCommand.getAliases().contains(subcmd)) {
            return this.aboutCommand.onCommand(cmd, args, sender);
        }

        if(this.setCommand.getAliases().contains(subcmd)) {
            return this.setCommand.onCommand(cmd, args, sender);
        }

        if(this.editorCommand.getAliases().contains(subcmd)) {
            return this.editorCommand.onCommand(cmd, args, sender);
        }

        if(this.applyEditsCommand.getAliases().contains(subcmd)) {
            return this.applyEditsCommand.onCommand(cmd, args, sender);
        }

        sender.sendMessage(LanguageManager.getFormat("admin-commands", sender.getUUID()));
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        List<String> tabCompletion = new ArrayList<>();
        if(args.length < 1) {
            tabCompletion.add("help");
            tabCompletion.add("info");
            tabCompletion.add("reload");
            tabCompletion.add("update");
            tabCompletion.add("about");
            tabCompletion.add("set");
            tabCompletion.add("editor");
            tabCompletion.add("applyedits");
        } else {
            if(this.helpCommand.getAliases().contains(args[0])) {
                return this.helpCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.infoCommand.getAliases().contains(args[0])) {
                return this.infoCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.reloadCommand.getAliases().contains(args[0])) {
                return this.reloadCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.updateCommand.getAliases().contains(args[0])) {
                return this.updateCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.aboutCommand.getAliases().contains(args[0])) {
                return this.aboutCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.setCommand.getAliases().contains(args[0])) {
                return this.setCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.editorCommand.getAliases().contains(args[0])) {
                return this.editorCommand.getTabCompletions(cmd, args, sender);
            }

            if(this.applyEditsCommand.getAliases().contains(args[0])) {
                return this.applyEditsCommand.getTabCompletions(cmd, args, sender);
            }
        }
        return tabCompletion;
    }
}
