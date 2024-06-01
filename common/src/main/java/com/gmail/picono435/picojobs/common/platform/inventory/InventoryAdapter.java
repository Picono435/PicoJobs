package com.gmail.picono435.picojobs.common.platform.inventory;

public interface InventoryAdapter {

    void create(String title, int size);

    void setItem(int slot, ItemAdapter item);

    ItemAdapter getItem(int slot);

    boolean isEmpty(int slot);

    int getSize();

    String getTitle();
    
    ItemAdapter toItemAdapter(Object object);
}
