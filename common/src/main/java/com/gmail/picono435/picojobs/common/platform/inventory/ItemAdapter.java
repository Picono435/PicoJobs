package com.gmail.picono435.picojobs.common.platform.inventory;

import java.util.List;

public class ItemAdapter {

    private String material;
    private Integer amount;
    private Byte durability;
    private String name;
    private Integer data;
    private boolean enchanted;
    private List<String> lore;

    public ItemAdapter(String material) {
        this.material = material;
    }

    public ItemAdapter(String material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    @Deprecated
    public ItemAdapter(String material, int amount, byte durability) {
        this.material = material;
        this.amount = amount;
        this.durability = durability;
    }

    public String getMaterial() {
        return material;
    }

    public Integer getAmount() {
        return amount;
    }

    public Byte getDurability() {
        return durability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }

    public boolean isEnchanted() {
        return enchanted;
    }

    public void setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}
