package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.platform.ColorConverter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class SpongeColorConverter implements ColorConverter {
    @Override
    public String translateAlternateColorCodes(String string) {
        // We cant do anything in this side unfortunately. Color translating is under SpongeSender#sendMessage
        return string;
    }

    @Override
    public String stripColor(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string).content();
    }
}
