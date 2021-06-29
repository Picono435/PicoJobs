package com.gmail.picono435.testing;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class TestPlugin {
	
    static PicoJobsPlugin plugin;
    private static ServerMock server;
	private static PlayerMock player;
    
    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(PicoJobsPlugin.class);
        player = server.addPlayer();
    }
    
    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }
    
    @Test
    void chooseJob() {
    	Assertions.assertEquals(true, player.performCommand("jobs"));
    }
    
}