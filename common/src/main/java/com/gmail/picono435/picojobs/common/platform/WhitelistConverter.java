package com.gmail.picono435.picojobs.common.platform;

import com.gmail.picono435.picojobs.api.Type;

import java.util.List;

//TODO: Add a separation between block whitelist types and item whitelist types
public interface WhitelistConverter {

    boolean inStringList(Object object, Type type, List<String> whitelist);
}
