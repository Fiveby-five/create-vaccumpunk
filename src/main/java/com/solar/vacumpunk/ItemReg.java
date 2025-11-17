package com.solar.vacumpunk;

import com.solar.vacumpunk.content.ConnectorBlock;
import com.solar.vacumpunk.content.SocketBoardBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public class ItemReg {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("createvacumpunk");
    
    public static final DeferredItem<BlockItem> COIL_BLOCK_ITEM = ITEMS.register("coils", () -> new BlockItem(BlockReg.COIL_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> VACUUM_TUBE_BLOCK_ITEM = ITEMS.register("vaccumtube_l", () -> new BlockItem(BlockReg.VACUUM_TUBE_BLOCK.get(), new Item.Properties()));
    
    // 新添加的四个方块对应的物品
    public static final DeferredItem<BlockItem> LARGE_CAPACITOR_BLOCK_ITEM = ITEMS.register("capacitor_l", () -> new BlockItem(BlockReg.LARGE_CAPACITOR_BLOCK.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> LARGE_RESISTOR_BLOCK_ITEM = ITEMS.register("resistor_l", () -> new BlockItem(BlockReg.LARGE_RESISTOR_BLOCK.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> LARGE_INDUCTOR_BLOCK_ITEM = ITEMS.register("inductorl", () -> new BlockItem(BlockReg.LARGE_INDUCTOR_BLOCK.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<BlockItem> LARGE_MAGNET_BLOCK_ITEM = ITEMS.register("magnet_l", () -> new BlockItem(BlockReg.LARGE_MAGNET_BLOCK.get(), new Item.Properties().stacksTo(16)));
    
    // 连接器方块物品
    public static final DeferredItem<BlockItem> CONNECTOR_BLOCK_ITEM = ITEMS.register("connector", () -> new BlockItem(BlockReg.CONNECTOR_BLOCK.get(), new Item.Properties().stacksTo(64)));
    
    // 插板方块物品
    public static final DeferredItem<BlockItem> SOCKET_BOARD_BLOCK_ITEM = ITEMS.register("socketboard", () -> new BlockItem(BlockReg.SOCKET_BOARD_BLOCK.get(), new Item.Properties().stacksTo(64)));
}