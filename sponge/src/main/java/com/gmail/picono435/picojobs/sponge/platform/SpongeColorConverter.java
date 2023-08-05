package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.platform.ColorConverter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class SpongeColorConverter implements ColorConverter {
    @Override
    public String translateAlternateColorCodes(String string) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(Component.text(string));
    }

    @Override
    public String stripColor(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string).content();
    }
}
