package com.solar.vacumpunk;

import com.solar.vacumpunk.content.CoilBlock;
import com.solar.vacumpunk.content.VacuumTubeBlock;
import com.solar.vacumpunk.content.LargeCapacitorBlock;
import com.solar.vacumpunk.content.LargeResistorBlock;
import com.solar.vacumpunk.content.LargeInductorBlock;
import com.solar.vacumpunk.content.LargeMagnetBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public class BlockReg {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("createvacumpunk");
    
    public static final DeferredBlock<CoilBlock> COIL_BLOCK = BLOCKS.register("coils", CoilBlock::new);
    public static final DeferredBlock<VacuumTubeBlock> VACUUM_TUBE_BLOCK = BLOCKS.register("vaccumtube_l", VacuumTubeBlock::new);
    
    // 新添加的四个方块
    public static final DeferredBlock<LargeCapacitorBlock> LARGE_CAPACITOR_BLOCK = BLOCKS.register("capacitor_l", LargeCapacitorBlock::new);
    public static final DeferredBlock<LargeResistorBlock> LARGE_RESISTOR_BLOCK = BLOCKS.register("resistor_l", LargeResistorBlock::new);
    public static final DeferredBlock<LargeInductorBlock> LARGE_INDUCTOR_BLOCK = BLOCKS.register("inductorl", LargeInductorBlock::new);
    public static final DeferredBlock<LargeMagnetBlock> LARGE_MAGNET_BLOCK = BLOCKS.register("magnet_l", LargeMagnetBlock::new);
}