package com.solar.vacumpunk;

import com.solar.vacumpunk.content.VacuumTubeBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityReg {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "createvacumpunk");
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VacuumTubeBlockEntity>> VACUUM_TUBE_BLOCK_ENTITY = 
        BLOCK_ENTITIES.register("vaccumtube_l", () -> BlockEntityType.Builder.of(VacuumTubeBlockEntity::new, BlockReg.VACUUM_TUBE_BLOCK.get()).build(null));
}