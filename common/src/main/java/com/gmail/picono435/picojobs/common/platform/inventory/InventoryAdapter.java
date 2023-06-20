package com.gmail.picono435.picojobs.common.platform.inventory;

import com.gmail.picono435.picojobs.common.inventory.ClickAction;

public interface InventoryAdapter {

    void init(String title, int size);

    void setItem(int slot, ItemAdapter item);

    void setItem(int slot, ItemAdapter item, ClickAction clickAction);

    int getSize();

    boolean isEmpty(int slot);

}
