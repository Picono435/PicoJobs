package com.gmail.picono435.picojobs.mod.fabric.hooks;

import com.gmail.picono435.picojobs.api.JobPlaceholders;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.resources.ResourceLocation;

public class PlaceholdersHook {

    public static void registerPlaceholders() {
        Placeholders.register(new ResourceLocation("jobplayer", "job"), (ctx, arg) -> {
            if (!ctx.hasPlayer())
                return PlaceholderResult.invalid("No player!");

            return PlaceholderResult.value(JobPlaceholders.translatePlaceholders(ctx.player().getUUID(), "job"));
        });

        Placeholders.register(new ResourceLocation("jobplayer", "tag"), (ctx, arg) -> {
            if (!ctx.hasPlayer())
                return PlaceholderResult.invalid("No player!");

            return PlaceholderResult.value(JobPlaceholders.translatePlaceholders(ctx.player().getUUID(), "tag"));
        });

        Placeholders.register(new ResourceLocation("jobplayer", "work"), (ctx, arg) -> {
            if (!ctx.hasPlayer())
                return PlaceholderResult.invalid("No player!");

            return PlaceholderResult.value(JobPlaceholders.translatePlaceholders(ctx.player().getUUID(), "work"));
        });

        Placeholders.register(new ResourceLocation("jobplayer", "reqmethod"), (ctx, arg) -> {
            if (!ctx.hasPlayer())
                return PlaceholderResult.invalid("No player!");

            return PlaceholderResult.value(JobPlaceholders.translatePlaceholders(ctx.player().getUUID(), "reqmethod"));
        });

        Placeholders.register(new ResourceLocation("jobplayer", "salary"), (ctx, arg) -> {
            if (!ctx.hasPlayer())
                return PlaceholderResult.invalid("No player!");

            return PlaceholderResult.value(JobPlaceholders.translatePlaceholders(ctx.player().getUUID(), "salary"));
        });

        Placeholders.register(new ResourceLocation("jobplayer", "level"), (ctx, arg) -> {
            if (!ctx.hasPlayer())
                return PlaceholderResult.invalid("No player!");

            return PlaceholderResult.value(JobPlaceholders.translatePlaceholders(ctx.player().getUUID(), "level"));
        });

        Placeholders.register(new ResourceLocation("jobplayer", "working"), (ctx, arg) -> {
            if (!ctx.hasPlayer())
                return PlaceholderResult.invalid("No player!");

            return PlaceholderResult.value(JobPlaceholders.translatePlaceholders(ctx.player().getUUID(), "working"));
        });
    }
}
