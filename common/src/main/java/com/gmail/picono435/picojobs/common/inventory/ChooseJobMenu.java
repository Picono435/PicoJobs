package com.gmail.picono435.picojobs.common.inventory;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.inventory.InventoryAdapter;
import com.gmail.picono435.picojobs.common.platform.inventory.ItemAdapter;
import org.spongepowered.configurate.CommentedConfigurationNode;

import java.util.UUID;

public class ChooseJobMenu {

    public static InventoryAdapter createMenu(InventoryAdapter inventoryAdapter, UUID player) {
        CommentedConfigurationNode node = FileManager.getGuiNode().node("gui-settings", "choose-job");
        inventoryAdapter.create(node.node("title").getString(), node.node("size").getInt());

        for(Job job : PicoJobsAPI.getJobsManager().getJobs()) {
            inventoryAdapter.setItem(job.getSlot(), job.getFormattedItem());
        }

        if(node.node("put-background-item").getBoolean()) {
            ItemAdapter itemAdapter;
            if(PicoJobsCommon.isLessThan("1.13.2")) {
                int itemData = node.node("item-data").getInt();
                if(itemData == -1) {
                    itemAdapter = new ItemAdapter(node.node("item").getString());
                } else {
                    itemAdapter = new ItemAdapter(node.node("item").getString(), 1, (byte)itemData);
                }
            } else {
                itemAdapter = new ItemAdapter(node.node("item").getString());
            }
            itemAdapter.setName(PicoJobsCommon.getPlaceholderTranslator().setPlaceholders(player, PicoJobsCommon.getColorConverter().translateAlternateColorCodes(node.node("item-name").getString())));
            itemAdapter.setEnchanted(node.node("enchanted").getBoolean());
            for(int i = 0; i < inventoryAdapter.getSize(); i++) {
                if(inventoryAdapter.isEmpty(i)) {
                    inventoryAdapter.setItem(i, itemAdapter);
                }
            }
        }

        return inventoryAdapter;
    }

}
