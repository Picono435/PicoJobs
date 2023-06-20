package com.gmail.picono435.picojobs.common.platform;

import java.util.List;
import java.util.UUID;

public interface PlaceholderTranslator {

    String setPlaceholders(UUID player, String string);

    List<String> setPlaceholders(UUID player, List<String> stringList);
}
