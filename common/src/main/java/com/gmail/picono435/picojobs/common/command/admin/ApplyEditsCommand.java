package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.PicoJobsMain;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ApplyEditsCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("applyedits", "ae");
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        if(args.length < 2) {
            sender.sendMessage(LanguageManager.getMessage("no-args", sender.getUUID()));
            return true;
        }
        if(applyEditsFromEditor(sender, args[1])) {
            sender.sendMessage(LanguageManager.formatMessage("&aWeb editor data was applied to the jobs configuration successfully."));
        } else {
            sender.sendMessage(LanguageManager.formatMessage("&cWeb editor data was not applied to the jobs configuration because of a unexpected error."));
        }
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }

    private boolean applyEditsFromEditor(Sender sender, String editor) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonEditor = new JsonObject();
            jsonEditor.addProperty("plugin", "PicoJobs");
            jsonEditor.addProperty("server", InetAddress.getLocalHost() + ":" + PicoJobsCommon.getPlatformAdapter().getPort());
            jsonEditor.addProperty("author", String.valueOf(sender.getUUID()));
            jsonEditor.addProperty("editor", editor);

            String charset = "UTF-8";

            URL url = new URL(PicoJobsMain.EDITOR_STRING + "/picojobs/get");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Charset", charset);
            con.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String json = jsonEditor.toString();
            try (OutputStream output = con.getOutputStream()) {
                output.write(json.getBytes(charset));
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder responseString = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    responseString.append(responseLine.trim());
                }
                JsonObject response = (JsonObject) parser.parse(responseString.toString());
                ConfigurationNode jobConfiguration = YamlConfigurationLoader.builder().build().load();
                if(response.get("status").getAsInt() == 3000) {
                    PicoJobsCommon.getMainInstance().jobs.clear();
                    System.out.println(response);
                    JsonObject jobsObject = (JsonObject) response.get("data");
                    for(String jobID : jobsObject.keySet()) {
                        Job job = new Job(jobsObject.get(jobID).getAsJsonObject());
                        jobConfiguration.node("jobs", jobID).set(job.toYamlConfiguration());
                        PicoJobsCommon.getMainInstance().jobs.put(jobID, job);
                    }
                    PicoJobsCommon.getFileManager().saveJobsFile(jobConfiguration);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
