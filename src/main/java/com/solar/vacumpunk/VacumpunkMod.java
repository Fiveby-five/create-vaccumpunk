package com.solar.vacumpunk;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.slf4j.Logger;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;

@Mod(VacumpunkMod.MODID)
public class VacumpunkMod {
    public static final String MODID = "createvacumpunk";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = BlockReg.BLOCKS;
    public static final DeferredRegister.Items ITEMS = ItemReg.ITEMS;
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = BlockEntityReg.BLOCK_ENTITIES;
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    
    // Creates a creative tab with the id "createvacumpunk:createvacumpunk_tab" for the coil block
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATEVACUMPUNK_TAB = CREATIVE_MODE_TABS.register("createvacumpunk_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.createvacumpunk")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemReg.COIL_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemReg.COIL_BLOCK_ITEM.get()); // Add the coil block item to the tab
                output.accept(ItemReg.VACUUM_TUBE_BLOCK_ITEM.get()); // Add the vacuum tube block item to the tab
                
                // 添加新方块到创造模式物品栏
                output.accept(ItemReg.LARGE_CAPACITOR_BLOCK_ITEM.get());
                output.accept(ItemReg.LARGE_RESISTOR_BLOCK_ITEM.get());
                output.accept(ItemReg.LARGE_INDUCTOR_BLOCK_ITEM.get());
                output.accept(ItemReg.LARGE_MAGNET_BLOCK_ITEM.get());
                
                // 添加连接器方块到创造模式物品栏
                output.accept(ItemReg.CONNECTOR_BLOCK_ITEM.get());
                
                // 添加插板方块到创造模式物品栏
                output.accept(ItemReg.SOCKET_BOARD_BLOCK_ITEM.get());
            }).build());

    public VacumpunkMod(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register the common setup method for modloading
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getName());
    }
}