package com.gmail.picono435.picojobs.common.platform.whitelist;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.Type;

import java.util.List;
import java.util.Map;

public interface WhitelistConverter {

    WhitelistInformation convertWhitelist(Map<Type, List<String>> whitelist, Job job);
}
