package com.gmail.picono435.picojobs.mod.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;

public class PlacedBlocksData extends SavedData {
    private List<BlockPos> placedBlocks = new ArrayList<>();

    public void addPlacedBlock(BlockPos blockPos) {
        placedBlocks.add(blockPos);
        setDirty(true);
    }

    public void removePlacedBlock(BlockPos blockPos) {
        placedBlocks.add(blockPos);
        setDirty(true);
    }

    public boolean isPlacedBlock(BlockPos blockPos) {
        return placedBlocks.contains(blockPos);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag listTag = new ListTag();
        for(BlockPos blockPos : placedBlocks) {
            CompoundTag blockTag = new CompoundTag();
            blockTag.putInt("X", blockPos.getX());
            blockTag.putInt("Y", blockPos.getY());
            blockTag.putInt("Z", blockPos.getZ());
            listTag.add(blockTag);
        }
        compoundTag.put("blocks", listTag);
        return compoundTag;
    }

    public static PlacedBlocksData create() {
        return new PlacedBlocksData();
    }

    public static PlacedBlocksData load(CompoundTag compoundTag) {
        PlacedBlocksData data = create();
        // Load saved data
        ListTag listTag = (ListTag) compoundTag.get("blocks");
        for(Tag tag : listTag) {
            CompoundTag blockTag = (CompoundTag) tag;
            data.placedBlocks.add(new BlockPos(blockTag.getInt("X"), blockTag.getInt("Y"), blockTag.getInt("Z")));
        }
        return data;
    }
}
