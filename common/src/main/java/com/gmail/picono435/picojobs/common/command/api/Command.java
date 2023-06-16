package com.gmail.picono435.picojobs.common.command.api;

import java.util.List;

public interface Command {

    boolean onCommand(String cmd, String[] args, Sender sender);

    List<String> getTabCompletions(String cmd, String[] args, Sender sender);
}
