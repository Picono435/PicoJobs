package com.gmail.picono435.picojobs.nukkit.platform;

import cn.nukkit.utils.TextFormat;
import com.gmail.picono435.picojobs.common.platform.ColorConverter;

import java.util.regex.Pattern;

public class NukkitColorConverter implements ColorConverter {

    public static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    @Override
    public String translateAlternateColorCodes(String string) {
        return TextFormat.colorize('&', string);
    }

    @Override
    public String stripColor(String string) {
        return TextFormat.clean(string);
    }
}