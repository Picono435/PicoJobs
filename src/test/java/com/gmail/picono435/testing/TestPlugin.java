package com.gmail.picono435.testing;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.mongodb.assertions.Assertions;
import org.bukkit.Material;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import org.junit.Test;

public class TestPlugin {
	
    static PicoJobsPlugin plugin;
    private static ServerMock server;
	private static PlayerMock player;

    @Before
    public static void setUp() {
        server = MockBukkit.mock();
        PicoJobsPlugin.isTestEnvironment = true;
        plugin = MockBukkit.load(PicoJobsPlugin.class);
        player = server.addPlayer();
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
    
    @Test
    public void chooseJob() {
    	Assert.assertTrue(player.performCommand("jobs"));
        player.simulateInventoryClick(28).getSlot();
        Assert.assertTrue(PicoJobsAPI.getPlayersManager().getJobPlayer(player).getJob().getID().equals("builder"));
    }

    @Test
    public void acceptWork() {
        Assert.assertTrue(player.performCommand("jobs"));
        player.simulateInventoryClick(16);
        JobPlayer jobPlayer = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        Assert.assertTrue(jobPlayer.isWorking());
    }

    @Test
    public void testJob() {
        JobPlayer jobPlayer = PicoJobsAPI.getPlayersManager().getJobPlayer(player);
        double method1 = jobPlayer.getMethod();
        player.simulateBlockPlace(Material.BRICKS, player.getLocation());
        Assert.assertTrue(PicoJobsAPI.getPlayersManager().getJobPlayer(player).getMethod() == method1 - 1);
    }
    
}