package com.gmail.picono435.picojobs;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
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
        System.out.println("3 H");
    	Assertions.assertTrue(player.performCommand("jobs"));
        System.out.println(player.getOpenInventory().getItem(28).getItemMeta().getDisplayName());
        System.out.println(player.getOpenInventory().getTitle());
        InventoryClickEvent event = player.simulateInventoryClick(player.getOpenInventory(), 28);
        System.out.println(event.getClickedInventory().getSize());
        Assertions.assertEquals("builder", jobPlayer.getJob().getID());
        System.out.println("4 H");
    }

    @Test()
    @Order(3)
    public void acceptWork() {
        System.out.println("5 H");
        Assertions.assertTrue(player.performCommand("jobs"));
        System.out.println(player.getOpenInventory().getTitle() + " heyo");
        player.simulateInventoryClick(16);
        Assertions.assertTrue(jobPlayer.isWorking());
        System.out.println("6 H");
    }

    @Test
    @Order(4)
    public void testJob() {
        System.out.println("7 H");
        double method1 = jobPlayer.getMethod();
        player.simulateBlockPlace(Material.BRICKS, player.getLocation());
        Assertions.assertEquals(jobPlayer.getMethod(), method1 - 1);
        System.out.println("8 H");
    }
    
}