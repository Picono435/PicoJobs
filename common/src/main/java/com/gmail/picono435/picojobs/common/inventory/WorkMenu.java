package com.gmail.picono435.picojobs.common.inventory;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.gmail.picono435.picojobs.common.platform.inventory.InventoryAdapter;
import com.gmail.picono435.picojobs.common.platform.inventory.ItemAdapter;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class WorkMenu {

    public static InventoryAdapter createMenu(InventoryAdapter inventoryAdapter, UUID player, String menu) {
        CommentedConfigurationNode node = FileManager.getGuiNode().node("gui-settings", menu);
        inventoryAdapter.create(node.node("title").getString(), node.node("size").getInt());

        for(Object itemNameObject : node.node("items").childrenMap().keySet()) {
            String itemName = (String) itemNameObject;
            CommentedConfigurationNode itemNode = node.node("items", itemName);
            ItemAdapter itemAdapter;
            if(PicoJobsCommon.isLessThan("1.13.2")) {
                int itemData = itemNode.node("item-data").getInt();
                if(itemData == -1) {
                    itemAdapter = new ItemAdapter(itemNode.node("material").getString());
                } else {
                    itemAdapter = new ItemAdapter(itemNode.node("material").getString(), 1, (byte)itemData);
                }
            } else {
                itemAdapter = new ItemAdapter(itemNode.node("material").getString());
            }
            itemAdapter.setName(PicoJobsCommon.getPlaceholderTranslator().setPlaceholders(player, PicoJobsCommon.getColorConverter().translateAlternateColorCodes(itemNode.node("name").getString())));
            itemAdapter.setEnchanted(itemNode.node("enchanted").getBoolean());

            if(PicoJobsCommon.isMoreThan("1.14")) {
                itemAdapter.setData(itemNode.node("custom-model-data").getInt());
            }

            List<String> lore;
            try {
                lore = itemNode.node("lore").getList(String.class);
            } catch (SerializationException e) {
                e.printStackTrace();
                lore = new ArrayList<>();
            }
            lore = lore.stream().map(s -> PicoJobsCommon.getColorConverter().translateAlternateColorCodes(s)).collect(Collectors.toList());
            lore = PicoJobsCommon.getPlaceholderTranslator().setPlaceholders(player, lore);
            itemAdapter.setLore(lore);

            inventoryAdapter.setItem(itemNode.node("slot").getInt() - 1, itemAdapter, ClickAction.valueOf(itemNode.node("action").getString().toUpperCase(Locale.ROOT)));
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
