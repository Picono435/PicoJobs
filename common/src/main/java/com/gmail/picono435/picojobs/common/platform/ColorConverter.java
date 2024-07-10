package com.gmail.picono435.picojobs.common.platform;

import java.util.ArrayList;
import java.util.List;

public interface ColorConverter {

    String translateAlternateColorCodes(String string);

    default List<String> translateAlternateColorCodes(List<String> stringList) {
        List<String> translatedList = new ArrayList<>();
        for(String s : stringList) {
            translatedList.add(translateAlternateColorCodes(s));
        }
        return translatedList;
    }

    String stripColor(String string);
}
