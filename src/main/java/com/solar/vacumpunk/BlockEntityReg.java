package com.solar.vacumpunk;

import com.solar.vacumpunk.content.VacuumTubeBlockEntity;
import com.solar.vacumpunk.content.ElectricalBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityReg {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "createvacumpunk");
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VacuumTubeBlockEntity>> VACUUM_TUBE_BLOCK_ENTITY = 
        BLOCK_ENTITIES.register("vaccumtube_l", () -> BlockEntityType.Builder.of(VacuumTubeBlockEntity::new, BlockReg.VACUUM_TUBE_BLOCK.get()).build(null));
        
    // 注册电气方块实体
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricalBlockEntity>> ELECTRICAL_BLOCK_ENTITY = 
        BLOCK_ENTITIES.register("electrical_block", () -> BlockEntityType.Builder.of(ElectricalBlockEntity::new, 
            BlockReg.LARGE_RESISTOR_BLOCK.get(),
            BlockReg.LARGE_CAPACITOR_BLOCK.get(),
            BlockReg.LARGE_INDUCTOR_BLOCK.get(),
            BlockReg.LARGE_MAGNET_BLOCK.get(),
            BlockReg.CONNECTOR_BLOCK.get(),
            BlockReg.SOCKET_BOARD_BLOCK.get()).build(null));
            

}