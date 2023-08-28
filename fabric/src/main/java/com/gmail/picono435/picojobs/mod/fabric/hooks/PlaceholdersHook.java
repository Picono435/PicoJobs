package com.gmail.picono435.picojobs.mod.fabric.hooks;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.placeholders.PlaceholderExtension;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.resources.ResourceLocation;

public class PlaceholdersHook {

    public static void registerPlaceholders() {
        for(PlaceholderExtension extension : PicoJobsAPI.getPlaceholderManager().getExtensions()) {
            for (String placeholder : extension.getPlaceholders()) {
                Placeholders.register(new ResourceLocation(extension.getPrefix(), placeholder), (ctx, arg) -> {
                    return PlaceholderResult.value(extension.translatePlaceholders(ctx.player().getUUID(), placeholder));
                });
            }
        }
    }
}
