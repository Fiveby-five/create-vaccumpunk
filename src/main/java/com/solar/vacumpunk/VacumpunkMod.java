package com.solar.vacumpunk;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(VacumpunkMod.MODID)
public class VacumpunkMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "createvacumpunk";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "createvacumpunk" namespace
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
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public VacumpunkMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the Deferred Register to the mod event bus so blocks get registered
        BlockReg.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ItemReg.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so block entities get registered
        BlockEntityReg.BLOCK_ENTITIES.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    // Add the coil block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ItemReg.COIL_BLOCK_ITEM);
            event.accept(ItemReg.VACUUM_TUBE_BLOCK_ITEM);
            
            // 添加新方块到建筑方块物品栏
            event.accept(ItemReg.LARGE_CAPACITOR_BLOCK_ITEM);
            event.accept(ItemReg.LARGE_RESISTOR_BLOCK_ITEM);
            event.accept(ItemReg.LARGE_INDUCTOR_BLOCK_ITEM);
            event.accept(ItemReg.LARGE_MAGNET_BLOCK_ITEM);
        }
    }
}