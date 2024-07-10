package com.gmail.picono435.picojobs.common.utils;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubAPI {

    public static boolean isTagLatest(String tagName) throws Exception {
        URL url = new URL("https://api.github.com/repos/Picono435/PicoJobs/compare/" + tagName + "..." + PicoJobsCommon.getVersion().replace("-DEV", ""));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setInstanceFollowRedirects(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        String status = jsonObject.get("status").getAsString();
        switch(status) {
            case("diverged"):
                return jsonObject.get("ahead_by").getAsInt() >= jsonObject.get("behind_by").getAsInt();
            case("identical"):
            case("ahead"):
            default:
                return true;
            case("behind"):
                return false;
        }
    }
}
