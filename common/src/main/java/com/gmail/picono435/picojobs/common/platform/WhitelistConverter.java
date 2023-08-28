package com.gmail.picono435.picojobs.common.platform;

import com.gmail.picono435.picojobs.api.Type;

import java.util.List;

public interface WhitelistConverter {

    boolean inStringList(Object object, Type type, List<String> whitelist);
}
