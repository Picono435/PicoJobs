package com.gmail.picono435.picojobs.common.platform.whitelist;

import com.gmail.picono435.picojobs.api.Type;

import java.util.List;
import java.util.Map;

public class WhitelistInformation {

    private Map<Type, List<Object>> objectWhitelist;
    private Map<Type, List<String>> stringWhitelist;

    public WhitelistInformation(Map<Type, List<Object>> objectWhitelist, Map<Type, List<String>> stringWhitelist) {
        this.objectWhitelist = objectWhitelist;
        this.stringWhitelist = stringWhitelist;
    }

    public Map<Type, List<Object>> getObjectWhitelist() {
        return objectWhitelist;
    }

    public Map<Type, List<String>> getStringWhitelist() {
        return stringWhitelist;
    }
}
