package com.solar.vacumpunk.content;

import com.solar.vacumpunk.BlockEntityReg;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

public class VacuumTubeBlockEntity extends BlockEntity {
    public VacuumTubeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityReg.VACUUM_TUBE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }
}