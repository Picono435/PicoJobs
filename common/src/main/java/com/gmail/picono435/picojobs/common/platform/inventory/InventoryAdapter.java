package com.gmail.picono435.picojobs.common.platform.inventory;

import com.gmail.picono435.picojobs.common.inventory.ClickAction;

public interface InventoryAdapter {

    void create(String title, int size);

    void setItem(int slot, ItemAdapter item);

    void setItem(int slot, ItemAdapter item, ClickAction clickAction);

    ItemAdapter getItem(int slot);

    boolean isEmpty(int slot);

    int getSize();

    String getTitle();
    
    ItemAdapter toItemAdapter(Object object);
}
