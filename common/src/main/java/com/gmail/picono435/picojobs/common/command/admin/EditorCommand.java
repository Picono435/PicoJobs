package com.gmail.picono435.picojobs.common.command.admin;

import com.gmail.picono435.picojobs.api.*;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.api.field.RequiredField;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.PicoJobsMain;
import com.gmail.picono435.picojobs.common.command.api.Command;
import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        Object editor = createEditor(sender);
        if(editor instanceof String) {
            sender.sendMessage(LanguageManager.formatMessage("&aClick the link below to open the editor:\n&b&e" + PicoJobsMain.EDITOR_STRING + "/picojobs/" + editor));
        } else if(editor instanceof Integer) {
            int errorCode = (int) editor;
            if(errorCode == 501) {
                sender.sendMessage(LanguageManager.formatMessage("&cThis feature is not yet avaiable for public. For more information check our discord and wiki."));
            } else {
                sender.sendMessage(LanguageManager.formatMessage("&cAn unexpected error occured while connecting with the PicoJobs editor. For more information check server logs."));
            }
        } else {
            sender.sendMessage(LanguageManager.formatMessage("&cAn unexpected error occured while connecting with the PicoJobs editor. For more information check server logs."));
        }
        return true;
    }

    @Override
    public List<String> getTabCompletions(String cmd, String[] args, Sender sender) {
        return null;
    }

    private Object createEditor(Sender sender) {
        try {
            Gson gson = new Gson();

            String serverVersionString = PicoJobsCommon.getPlatformAdapter().getMinecraftVersion();

            JsonObject jsonEditor = new JsonObject();
            jsonEditor.addProperty("plugin", "PicoJobs");
            jsonEditor.addProperty("server", InetAddress.getLocalHost() + ":" + PicoJobsCommon.getPlatformAdapter().getPort());
            jsonEditor.addProperty("platform", PicoJobsCommon.getPlatform().name());
            jsonEditor.addProperty("author", String.valueOf(sender.getUUID()));
            jsonEditor.addProperty("minecraftVersion", serverVersionString);

            JsonArray jsonItems = gson.toJsonTree(PicoJobsCommon.getRegistryCollector().getItemList()).getAsJsonArray();
            jsonEditor.add("items", jsonItems);

            JsonArray jsonEntities = gson.toJsonTree(PicoJobsCommon.getRegistryCollector().getEntityList()).getAsJsonArray();
            jsonEditor.add("entities", jsonEntities);

            JsonObject jsonEconomies = new JsonObject();
            jsonEconomies.add("DEFAULT", new JsonObject());
            for(String economy : PicoJobsCommon.getMainInstance().economies.keySet()) {
                RequiredField<?, ?> requiredField = PicoJobsAPI.getEconomy(economy).getRequiredField();
                jsonEconomies.add(economy, requiredField != null ? requiredField.toJsonObject() : new JsonObject());
            }
            jsonEditor.add("economies", jsonEconomies);

            JsonObject jsonWorkZones = new JsonObject();
            jsonWorkZones.add("DEFAULT", new JsonObject());
            for(String workzone : PicoJobsCommon.getMainInstance().workzones.keySet()) {
                RequiredField<?, ?> requiredField = PicoJobsAPI.getWorkZone(workzone).getRequiredField();
                jsonWorkZones.add(workzone, requiredField != null ? requiredField.toJsonObject() : new JsonObject());
            }
            jsonEditor.add("workzones", jsonWorkZones);

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

            Charset charset = StandardCharsets.UTF_8;

            URL url = new URL(PicoJobsMain.EDITOR_STRING + "/picojobs/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", charset.name());
            connection.setRequestProperty("Content-Type", "application/json;charset=" + charset.name());
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String json = jsonEditor.toString();
            try (OutputStream output = connection.getOutputStream()) {
                output.write(json.getBytes(charset));
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), charset))) {
                StringBuilder responseString = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    responseString.append(responseLine.trim());
                }
                JsonObject response = (JsonObject) JsonParser.parseString(responseString.toString());
                return response.get("editor").getAsString();
            } catch (IOException exception) {
                return connection.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
