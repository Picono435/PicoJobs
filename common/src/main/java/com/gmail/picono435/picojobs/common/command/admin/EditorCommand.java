package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.PicoJobsMain;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class EditorCommand implements Command {

    @Override
    public List<String> getAliases() {
        return Arrays.asList("editor", LanguageManager.getSubCommandAlias("editor"));
    }

    @Override
    public boolean onCommand(String cmd, String[] args, Sender sender) {
        sender.sendMessage(LanguageManager.formatMessage("&7Preparing a new editor session. Please wait..."));
        String editor = createEditor(sender);
        if(editor != null) {
            sender.sendMessage(LanguageManager.formatMessage("&aClick the link below to open the editor:\n&b&e" + PicoJobsMain.EDITOR_STRING + "/picojobs/" + editor));
        } else {
            sender.sendMessage(LanguageManager.formatMessage("&cThis feature is not yet avaiable for public. For more information check our discord or/and ou wiki."));
        }
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }

    private String createEditor(Sender sender) {
        try {
            String serverVersionString = PicoJobsCommon.getPlatformAdapter().getMinecraftVersion();

            JsonParser parser = new JsonParser();
            JsonObject jsonEditor = new JsonObject();
            jsonEditor.addProperty("plugin", "PicoJobs");
            jsonEditor.addProperty("server", InetAddress.getLocalHost() + ":" + PicoJobsCommon.getPlatformAdapter().getPort());
            jsonEditor.addProperty("author", String.valueOf(sender.getUUID()));
            jsonEditor.addProperty("minecraftVersion", serverVersionString);

            JsonArray jsonEconomies = new JsonArray();
            for(String economy : PicoJobsCommon.getMainInstance().economies.keySet()) {
                jsonEconomies.add(economy);
            }
            jsonEditor.add("economies", jsonEconomies);

            JsonArray jsonWorkzones = new JsonArray();
            for(String workzone : PicoJobsCommon.getMainInstance().workZones.keySet()) {
                jsonEconomies.add(workzone);
            }
            jsonEditor.add("workzones", jsonWorkzones);

            JsonObject jsonTypes = new JsonObject();
            for(Type type : Type.values()) {
                jsonTypes.addProperty(type.name(), type.getWhitelistType().name());
            }
            jsonEditor.add("types", jsonTypes);

            JsonObject jsonJobs = new JsonObject();
            for(Job job : PicoJobsAPI.getJobsManager().getJobs()) {
                jsonJobs.add(job.getID(), job.toJsonObject());
            }
            jsonEditor.add("jobs", jsonJobs);

            String charset = "UTF-8";

            URL url = new URL(PicoJobsMain.EDITOR_STRING + "/picojobs/create");
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
                return response.get("editor").getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
