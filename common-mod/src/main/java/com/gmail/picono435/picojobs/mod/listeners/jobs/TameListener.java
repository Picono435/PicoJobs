package com.gmail.picono435.picojobs.mod.listeners.jobs;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.common.listeners.jobs.WorkListener;
import com.gmail.picono435.picojobs.mod.platform.ModSender;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

public class TameListener implements EntityEvent.AnimalTame {
    @Override
    public EventResult tame(Animal animal, Player player) {
        WorkListener.simulateWorkListener(new ModSender(player), Type.TAME, animal.getType());
        return EventResult.pass();
    }
}
