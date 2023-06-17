package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("set", LanguageManager.getSubCommandAlias("set"));
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        if(args.length < 2) {
            sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
            return true;
        }

        String salaryString = LanguageManager.getSubCommandAlias("salary");
        String methodString = LanguageManager.getSubCommandAlias("method");
        String jobString = LanguageManager.getSubCommandAlias("job");

        if(args[1].equalsIgnoreCase("salary") || args[1].equalsIgnoreCase(salaryString)) {
            if(args.length < 4) {
                sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
                return true;
            }
            JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(PicoJobsCommon.getPlatformAdapter().getPlayerUUID(args[2]));
            if(jp == null)  {
                sender.sendMessage(LanguageManager.getMessage("player-not-found", sender.getUUID()));
                return true;
            }
            int newSalary = 0;
            try {
                newSalary = Integer.parseInt(args[3]);
            } catch(Exception ex) {
                sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
                return true;
            }
            jp.setSalary(newSalary);
            sender.sendMessage(LanguageManager.getMessage("sucefully", sender.getUUID()));
            return true;
        }

        if(args[1].equalsIgnoreCase("method") || args[1].equalsIgnoreCase(methodString)) {
            if(args.length < 4) {
                sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
                return true;
            }
            JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(PicoJobsCommon.getPlatformAdapter().getPlayerUUID(args[2]));
            if(jp == null)  {
                sender.sendMessage(LanguageManager.getMessage("player-not-found", sender.getUUID()));
                return true;
            }
            int newMethod = 0;
            try {
                newMethod = Integer.parseInt(args[3]);
            } catch(Exception ex) {
                sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
                return true;
            }
            jp.setMethod(newMethod);
            sender.sendMessage(LanguageManager.getMessage("sucefully", sender.getUUID()));
            return true;
        }

        if(args[1].equalsIgnoreCase("job") || args[1].equalsIgnoreCase(jobString)) {
            if(args.length < 4) {
                sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
                return true;
            }
            JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(PicoJobsCommon.getPlatformAdapter().getPlayerUUID(args[2]));
            if(jp == null)  {
                sender.sendMessage(LanguageManager.getMessage("player-not-found", sender.getUUID()));
                return true;
            }
            Job job = PicoJobsAPI.getJobsManager().getJob(args[3]);
            if(job == null) {
                job = PicoJobsAPI.getJobsManager().getJobByStrippedColorDisplayname(args[3]);
                if(job == null) {
                    sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
                    return true;
                }
            }
            jp.setJob(job);
            sender.sendMessage(LanguageManager.getMessage("sucefully", sender.getUUID()));
            return true;
        }

        sender.sendMessage(LanguageManager.getFormat("admin-commands", sender.getUUID()));
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        List<String> tabCompletion = new ArrayList<>();
        if(args.length < 3) {
            tabCompletion.add("salary");
            tabCompletion.add("method");
            tabCompletion.add("job");
        }

        return tabCompletion;
    }
}
