package com.gmail.picono435.picojobs;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import org.bukkit.Material;
import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutionException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PicoJobsPluginTests {
	
    private PicoJobsPlugin plugin;
    private ServerMock server;
	private PlayerMock player;
    private JobPlayer jobPlayer;

    @BeforeAll
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(PicoJobsPlugin.class);
        player = server.addPlayer();
        player.addAttachment(plugin, "picojobs.use.basic", true);
    }

    @AfterAll
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @Order(1)
    public void createPlayer() throws ExecutionException, InterruptedException {
        this.jobPlayer = PicoJobsAPI.getPlayersManager().getJobPlayer(player.getUniqueId(), true);
        Assertions.assertSame(jobPlayer.getUUID(), player.getUniqueId());
    }

    @Test
    @Order(2)
    public void chooseJob() {
    	Assertions.assertTrue(player.performCommand("jobs choose builder"));
        Assertions.assertEquals("builder", jobPlayer.getJob().getID());
    }

    @Test()
    @Order(3)
    public void acceptWork() {
        Assertions.assertTrue(player.performCommand("jobs work"));
        Assertions.assertTrue(jobPlayer.isWorking());
    }

    @Test
    @Order(4)
    public void testJob() {
        double method1 = jobPlayer.getMethod();
        player.simulateBlockPlace(Material.BRICKS, player.getLocation());
        Assertions.assertEquals(jobPlayer.getMethod(), method1 + 1);
    }
    
}