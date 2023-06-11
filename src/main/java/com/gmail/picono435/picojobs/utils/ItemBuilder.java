package com.gmail.picono435.picojobs.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.gmail.picono435.picojobs.hooks.PlaceholderAPIHook;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {
   private ItemStack is;

   public ItemBuilder(Material m){
     this(m, 1);
   }

   public ItemBuilder(ItemStack is){
     this.is=is;
   }

   public ItemBuilder(Material m, int amount){
	   if(m == null) {
		   m = Material.STONE;
	   }
     is= new ItemStack(m, amount);
   }

   @Deprecated
   public ItemBuilder(Material m, int amount, byte durability){
	   if(m == null) {
		   m = Material.STONE;
	   }
     is = new ItemStack(m, amount, durability);
   }

   public ItemBuilder clone(){
     return new ItemBuilder(is);
   }
   
   @SuppressWarnings("deprecation")
public ItemBuilder setDurability(short dur){
     is.setDurability(dur);
     return this;
   }

   public ItemBuilder setName(String name){
     ItemMeta im = is.getItemMeta();
     im.setDisplayName(name);
     is.setItemMeta(im);
     return this;
   }

    public ItemBuilder setCustomModelData(int data){
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(data);
        is.setItemMeta(im);
        return this;
    }

   public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level){
     is.addUnsafeEnchantment(ench, level);
     return this;
   }

   public ItemBuilder removeEnchantment(Enchantment ench){
     is.removeEnchantment(ench);
     return this;
   }

   @SuppressWarnings("deprecation")
public ItemBuilder setSkullOwner(String owner){
     try{
       SkullMeta im = (SkullMeta)is.getItemMeta();
       im.setOwner(owner);
       is.setItemMeta(im);
     }catch(ClassCastException expected){}
     return this;
   }

   public ItemBuilder addEnchant(Enchantment ench, int level){
     ItemMeta im = is.getItemMeta();
     im.addEnchant(ench, level, true);
     is.setItemMeta(im);
     return this;
   }

   public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments){
     is.addEnchantments(enchantments);
     return this;
   }

   @SuppressWarnings("deprecation")
public ItemBuilder setInfinityDurability(){
     is.setDurability(Short.MAX_VALUE);
     return this;
   }

   public ItemBuilder setLore(String... lore){
     ItemMeta im = is.getItemMeta();
     im.setLore(Arrays.asList(lore));
     is.setItemMeta(im);
     return this;
   }

   public ItemBuilder setLore(List<String> lore) {
     ItemMeta im = is.getItemMeta();
     List<String> newLore = new ArrayList<>();
     for(String l : lore) {
         newLore.add(PlaceholderAPIHook.setPlaceholders(null, ColorConverter.translateAlternateColorCodes(l)));
     }
     im.setLore(newLore);
     is.setItemMeta(im);
     return this;
   }

   public ItemBuilder removeLoreLine(String line){
     ItemMeta im = is.getItemMeta();
     List<String> lore = new ArrayList<>(im.getLore());
     if(!lore.contains(line))return this;
     lore.remove(line);
     im.setLore(lore);
     is.setItemMeta(im);
     return this;
   }

   public ItemBuilder removeLoreLine(int index){
     ItemMeta im = is.getItemMeta();
     List<String> lore = new ArrayList<>(im.getLore());
     if(index<0||index>lore.size())return this;
     lore.remove(index);
     im.setLore(lore);
     is.setItemMeta(im);
     return this;
   }

   public ItemBuilder addLoreLine(String line){
     ItemMeta im = is.getItemMeta();
     List<String> lore = new ArrayList<>();
     if(im.hasLore())lore = new ArrayList<>(im.getLore());
     lore.add(line);
     im.setLore(lore);
     is.setItemMeta(im);
     return this;
   }

   public ItemBuilder addLoreLine(String line, int pos){
     ItemMeta im = is.getItemMeta();
     List<String> lore = new ArrayList<>(im.getLore());
     lore.set(pos, line);
     im.setLore(lore);
     is.setItemMeta(im);
     return this;
   }

   public ItemBuilder setLeatherArmorColor(Color color){
     try{
       LeatherArmorMeta im = (LeatherArmorMeta)is.getItemMeta();
       im.setColor(color);
       is.setItemMeta(im);
     }catch(ClassCastException expected){}
     return this;
   }

   public ItemBuilder removeAttributes() {
       final ItemMeta meta = this.is.getItemMeta();
       meta.addItemFlags(ItemFlag.values());
       this.is.setItemMeta(meta);
       return this;
   }

   public ItemStack toItemStack(){
     return is;
   }
}