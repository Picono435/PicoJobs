package com.gmail.picono435.testing;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class TestPlugin {
	
    PicoJobsPlugin plugin;
    private ServerMock server;
    private PlayerMock player;
    
    @BeforeAll
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(PicoJobsPlugin.class);
        player = server.addPlayer();
    }
    
    @AfterAll
    public void tearDown() {
        MockBukkit.unmock();
    }
    
}