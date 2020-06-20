package net.snakefangox.mechanized;

import com.google.common.base.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.snakefangox.mechanized.blocks.*;
import net.snakefangox.mechanized.blocks.entity.*;
import net.snakefangox.mechanized.effects.ExoEffect;
import net.snakefangox.mechanized.entities.FlyingBlockEntity;
import net.snakefangox.mechanized.gui.*;
import net.snakefangox.mechanized.items.PressureGauge;
import net.snakefangox.mechanized.items.SteamCanister;
import net.snakefangox.mechanized.items.SteamDrill;
import net.snakefangox.mechanized.items.SteamExoSuit;
import net.snakefangox.mechanized.recipes.AlloyRecipe;

import java.util.function.ToIntFunction;

public class MRegister {

	// Blocks
	//TODO Set mining level back up when fapi fixed
	public static final Block COPPER_ORE = new Block(FabricBlockSettings.of(Material.STONE).hardness(5).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES, 0));
	public static final Block ZINC_ORE = new Block(FabricBlockSettings.of(Material.STONE).hardness(5).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES, 0));
	public static final Block ALLOY_FURNACE = new AlloyFurnace(FabricBlockSettings.of(Material.STONE).hardness(4)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES).lightLevel(createLightLevelFromBlockState(10)).nonOpaque());
	public static final Block BASIC_BOILER = new SteamBoiler(FabricBlockSettings.of(Material.METAL).hardness(4)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES).lightLevel(createLightLevelFromBlockState(10)), BasicBoilerEntity::new);
	public static final Block BLAST_BOILER = new SteamBoiler(FabricBlockSettings.of(Material.METAL).hardness(4)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES).lightLevel(createLightLevelFromBlockState(10)), BlastBoilerEntity::new);
	public static final Block STEAM_PIPE = new SteamPipe(FabricBlockSettings.of(Material.METAL).hardness(3)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES));
	public static final Block STEAM_TANK = new SteamTank(FabricBlockSettings.of(Material.METAL).hardness(4)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES));
	public static final Block PUMP = new Pump(FabricBlockSettings.of(Material.METAL).hardness(4).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES));
	public static final Block BREAKER = new Breaker(FabricBlockSettings.of(Material.METAL).hardness(4).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES));
	public static final Block PLACER = new Placer(FabricBlockSettings.of(Material.METAL).hardness(4).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES));
	public static final Block STEAM_PISTON = new SteamPiston(FabricBlockSettings.of(Material.METAL).hardness(4)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES).nonOpaque());
	public static final Block FAN = new Fan(FabricBlockSettings.of(Material.METAL).hardness(4).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES));
	public static final Block PRESSURE_VALVE = new PressureValve(FabricBlockSettings.of(Material.METAL).hardness(4)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES));
	public static final Block STEAM_CHARGER = new SteamCharger(FabricBlockSettings.of(Material.METAL).hardness(4)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES));
	public static final Block UPGRADE_TABLE = new UpgradeTable(FabricBlockSettings.of(Material.METAL).hardness(4)
			.resistance(3).breakByTool(FabricToolTags.PICKAXES));
	public static final Block STEAM_FRACTIONATING_TOWER = new SteamFractionatingTower(FabricBlockSettings
			.of(Material.METAL).hardness(4).resistance(3).breakByTool(FabricToolTags.PICKAXES).nonOpaque());
	public static final Block BRASS_FRAME = new Block(FabricBlockSettings.of(Material.METAL).hardness(4).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES).nonOpaque());
	public static final Block STEAM_CONDENSER = new SteamCondenser(FabricBlockSettings.of(Material.METAL).hardness(4).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES).nonOpaque());
	public static final Block STEAM_SOURCE = new SteamSource(FabricBlockSettings.of(Material.METAL).hardness(4).resistance(3)
			.breakByTool(FabricToolTags.PICKAXES));

	// BlockEntities
	public static BlockEntityType<AlloyFurnaceEntity> ALLOY_FURNACE_ENTITY;
	public static BlockEntityType<BasicBoilerEntity> BASIC_BOILER_ENTITY;
	public static BlockEntityType<BlastBoilerEntity> BLAST_BOILER_ENTITY;
	public static BlockEntityType<SteamPipeEntity> STEAM_PIPE_ENTITY;
	public static BlockEntityType<SteamTankEntity> STEAM_TANK_ENTITY;
	public static BlockEntityType<PumpEntity> PUMP_ENTITY;
	public static BlockEntityType<BreakerEntity> BREAKER_ENTITY;
	public static BlockEntityType<PlacerEntity> PLACER_ENTITY;
	public static BlockEntityType<SteamPistonEntity> STEAM_PISTON_ENTITY;
	public static BlockEntityType<FanEntity> FAN_ENTITY;
	public static BlockEntityType<PressureValveEntity> PRESSURE_VALVE_ENTITY;
	public static BlockEntityType<SteamChargerEntity> STEAM_CHARGER_ENTITY;
	public static BlockEntityType<SteamFractionatingTowerEntity> STEAM_FRACTIONATING_TOWER_ENTITY;
	public static BlockEntityType<SteamSourceEntity> STEAM_SOURCE_ENTITY;
	public static BlockEntityType<SteamCondenserEntity> STEAM_CONDENSER_ENTITY;

	// Containers
	public static final Identifier ALLOY_FURNACE_CONTAINER = new Identifier(Mechanized.MODID, "alloy_furnace");
	public static final Identifier STEAM_BOILER_CONTAINER = new Identifier(Mechanized.MODID, "steam_boiler");
	public static final Identifier PRESSURE_VALVE_CONTAINER = new Identifier(Mechanized.MODID, "pressure_valve");
	public static final Identifier UPGRADE_TABLE_CONTAINER = new Identifier(Mechanized.MODID, "upgrade_table");
	public static final Identifier PLACER_CONTAINER = new Identifier(Mechanized.MODID, "placer");
	public static final Identifier STEAM_GAUGE_CONTAINER = new Identifier(Mechanized.MODID, "steam_gauge");

	public static final ScreenHandlerType<AlloyFurnaceContainer> ALLOY_FURNACE_SCREEN_HANDLER =  ScreenHandlerRegistry.registerExtended(ALLOY_FURNACE_CONTAINER,
			(syncId,  playerInv, buf) -> new AlloyFurnaceContainer(syncId, playerInv,
					ScreenHandlerContext.create(playerInv.player.world, buf.readBlockPos())));
	public static final ScreenHandlerType<SteamBoilerContainer> STEAM_BOILER_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(STEAM_BOILER_CONTAINER,
			(syncId,  playerInv, buf) -> new SteamBoilerContainer(syncId, playerInv,
		  	ScreenHandlerContext.create(playerInv.player.world, buf.readBlockPos())));
	public static final ScreenHandlerType<PressureValveContainer> PRESSURE_VALVE_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(PRESSURE_VALVE_CONTAINER,
			(syncId,  playerInv, buf) -> new PressureValveContainer(syncId, playerInv,
			ScreenHandlerContext.create(playerInv.player.world, buf.readBlockPos())));
	public static final ScreenHandlerType<UpgradeTableContainer> UPGRADE_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(UPGRADE_TABLE_CONTAINER,
			UpgradeTableContainer::new);
	public static final ScreenHandlerType<PlacerContainer> PLACER_CONTAINER_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(PLACER_CONTAINER,
			(syncId,  playerInv, buf) -> new PlacerContainer(syncId, playerInv,
			 ScreenHandlerContext.create(playerInv.player.world, buf.readBlockPos())));
	public static final ScreenHandlerType<SteamGaugeContainer> STEAM_GAUGE_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(STEAM_GAUGE_CONTAINER,
			(syncId,  playerInv, buf) -> new SteamGaugeContainer(syncId, playerInv,
					ScreenHandlerContext.create(playerInv.player.world, buf.readBlockPos())));


	// Items
	public static final Item COPPER_INGOT = new Item(new Item.Settings().group(Mechanized.ITEM_GROUP));
	public static final Item ZINC_INGOT = new Item(new Item.Settings().group(Mechanized.ITEM_GROUP));
	public static final Item BRASS_INGOT = new Item(new Item.Settings().group(Mechanized.ITEM_GROUP));
	public static final Item FAN_BLADE = new Item(new Item.Settings().group(Mechanized.ITEM_GROUP));
	public static final Item PRESSURE_GAUGE = new PressureGauge(
			new Item.Settings().group(Mechanized.ITEM_GROUP).maxCount(1));
	public static final Item STEAM_CANISTER = new SteamCanister(
			new Item.Settings().group(Mechanized.ITEM_GROUP).maxCount(1).maxDamage(SteamCanister.STEAM_CAPACITY));
	public static final Item STEAM_DRILL = new SteamDrill(
			new Item.Settings().group(Mechanized.ITEM_GROUP).maxCount(1).maxDamage(SteamDrill.STEAM_CAPACITY));
	public static final Item STEAM_EXOSUIT_HELMET = new SteamExoSuit(EquipmentSlot.HEAD,
			new Item.Settings().group(Mechanized.ITEM_GROUP).maxCount(1).maxDamage(SteamExoSuit.STEAM_CAPACITY));
	public static final Item STEAM_EXOSUIT_CHEST = new SteamExoSuit(EquipmentSlot.CHEST,
			new Item.Settings().group(Mechanized.ITEM_GROUP).maxCount(1).maxDamage(SteamExoSuit.STEAM_CAPACITY));
	public static final Item STEAM_EXOSUIT_LEGS = new SteamExoSuit(EquipmentSlot.LEGS,
			new Item.Settings().group(Mechanized.ITEM_GROUP).maxCount(1).maxDamage(SteamExoSuit.STEAM_CAPACITY));
	public static final Item STEAM_EXOSUIT_BOOTS = new SteamExoSuit(EquipmentSlot.FEET,
			new Item.Settings().group(Mechanized.ITEM_GROUP).maxCount(1).maxDamage(SteamExoSuit.STEAM_CAPACITY));

	// Status Effects
	public static StatusEffect EXOSUIT_STRENGTH;
	public static StatusEffect EXOSUIT_SPEED;
	public static StatusEffect EXOSUIT_PROTECC;

	// Sound Events
	public static SoundEvent STEAM_INJECT;
	public static SoundEvent STEAM_ESCAPES;
	public static SoundEvent STEAM_HIT;

	// Entities
	public static final EntityType<FlyingBlockEntity> FLYING_BLOCK = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(Mechanized.MODID, "flying_block"),
			FabricEntityTypeBuilder
					.create(SpawnGroup.MISC, (EntityType.EntityFactory<FlyingBlockEntity>) FlyingBlockEntity::new)
					.dimensions(EntityDimensions.fixed(1, 1)).build());

	public static void registerEverything() {
		registerBlock(COPPER_ORE, new Identifier(Mechanized.MODID, "copper_ore"), RenderLayerEnum.CUTOUT);
		registerBlock(ZINC_ORE, new Identifier(Mechanized.MODID, "zinc_ore"), RenderLayerEnum.CUTOUT);
		ALLOY_FURNACE_ENTITY = registerBlock(ALLOY_FURNACE, new Identifier(Mechanized.MODID, "alloy_furnace"),
				AlloyFurnaceEntity::new);
		BASIC_BOILER_ENTITY = registerBlock(BASIC_BOILER, new Identifier(Mechanized.MODID, "steam_boiler"),
				BasicBoilerEntity::new);
		BLAST_BOILER_ENTITY = registerBlock(BLAST_BOILER, new Identifier(Mechanized.MODID, "blast_boiler"),
				BlastBoilerEntity::new);
		STEAM_PIPE_ENTITY = registerBlock(STEAM_PIPE, new Identifier(Mechanized.MODID, "steam_pipe"),
				SteamPipeEntity::new);
		STEAM_TANK_ENTITY = registerBlock(STEAM_TANK, new Identifier(Mechanized.MODID, "steam_tank"),
				SteamTankEntity::new);
		PUMP_ENTITY = registerBlock(PUMP, new Identifier(Mechanized.MODID, "pump"), PumpEntity::new,
				RenderLayerEnum.CUTOUT);
		BREAKER_ENTITY = registerBlock(BREAKER, new Identifier(Mechanized.MODID, "breaker"), BreakerEntity::new);
		PLACER_ENTITY = registerBlock(PLACER, new Identifier(Mechanized.MODID, "placer"), PlacerEntity::new);
		STEAM_PISTON_ENTITY = registerBlock(STEAM_PISTON, new Identifier(Mechanized.MODID, "steam_piston"),
				SteamPistonEntity::new);
		FAN_ENTITY = registerBlock(FAN, new Identifier(Mechanized.MODID, "fan"), FanEntity::new);
		PRESSURE_VALVE_ENTITY = registerBlock(PRESSURE_VALVE, new Identifier(Mechanized.MODID, "pressure_valve"),
				PressureValveEntity::new);
		STEAM_CHARGER_ENTITY = registerBlock(STEAM_CHARGER, new Identifier(Mechanized.MODID, "steam_charger"),
				SteamChargerEntity::new);
		registerBlock(UPGRADE_TABLE, new Identifier(Mechanized.MODID, "upgrade_table"));
		STEAM_FRACTIONATING_TOWER_ENTITY = registerBlock(STEAM_FRACTIONATING_TOWER,
				new Identifier(Mechanized.MODID, "steam_fractionating_tower"), SteamFractionatingTowerEntity::new);
		registerBlock(BRASS_FRAME, new Identifier(Mechanized.MODID, "brass_frame"));
		STEAM_SOURCE_ENTITY = registerBlock(STEAM_SOURCE, new Identifier(Mechanized.MODID, "steam_source"),
				SteamSourceEntity::new);
		STEAM_CONDENSER_ENTITY = registerBlock(STEAM_CONDENSER, new Identifier(Mechanized.MODID, "steam_condenser"),
				SteamCondenserEntity::new);


		registerItem(COPPER_INGOT, new Identifier(Mechanized.MODID, "copper_ingot"));
		registerItem(ZINC_INGOT, new Identifier(Mechanized.MODID, "zinc_ingot"));
		registerItem(BRASS_INGOT, new Identifier(Mechanized.MODID, "brass_ingot"));
		registerItem(FAN_BLADE, new Identifier(Mechanized.MODID, "fan_blade"));
		registerItem(PRESSURE_GAUGE, new Identifier(Mechanized.MODID, "pressure_gauge"));
		registerItem(STEAM_CANISTER, new Identifier(Mechanized.MODID, "steam_canister"));
		registerItem(STEAM_DRILL, new Identifier(Mechanized.MODID, "steam_drill"));
		registerItem(STEAM_EXOSUIT_HELMET, new Identifier(Mechanized.MODID, "steam_exosuit_helm"));
		registerItem(STEAM_EXOSUIT_CHEST, new Identifier(Mechanized.MODID, "steam_exosuit_chest"));
		registerItem(STEAM_EXOSUIT_LEGS, new Identifier(Mechanized.MODID, "steam_exosuit_legs"));
		registerItem(STEAM_EXOSUIT_BOOTS, new Identifier(Mechanized.MODID, "steam_exosuit_boots"));

		EXOSUIT_STRENGTH = Registry.register(Registry.STATUS_EFFECT,
				new Identifier(Mechanized.MODID, "exosuit_strength"),
				new ExoEffect().addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,
						"6551312b-037b-4152-8c44-e81e9065bae6", 2, EntityAttributeModifier.Operation.ADDITION));
		EXOSUIT_SPEED = Registry.register(Registry.STATUS_EFFECT, new Identifier(Mechanized.MODID, "exosuit_speed"),
				new ExoEffect().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
						"d859def7-792c-40c2-881b-7d2703fc5760", 0.15D,
						EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
		EXOSUIT_PROTECC = Registry.register(Registry.STATUS_EFFECT, new Identifier(Mechanized.MODID, "exosuit_protecc"),
				new ExoEffect().addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "1b7e6f75-78d3-4a4d-85af-330e09d2470e", 2,
						EntityAttributeModifier.Operation.ADDITION));

		STEAM_INJECT = registerSoundEvent(new Identifier(Mechanized.MODID, "steam_inject"));
		STEAM_HIT = registerSoundEvent(new Identifier(Mechanized.MODID, "steam_hit"));
		STEAM_ESCAPES = registerSoundEvent(new Identifier(Mechanized.MODID, "steam_escapes"));

		Registry.register(Registry.RECIPE_SERIALIZER, AlloyRecipe.ID, AlloyRecipe.AlloyRecipeSerializer.INSTANCE);
		Registry.register(Registry.RECIPE_TYPE, AlloyRecipe.ID, AlloyRecipe.AlloyRecipeType.INSTANCE);

	}

	private static void registerBlock(Block block, Identifier id, RenderLayerEnum layer) {
		registerBlock(block, id);
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			setRenderLayer(block, layer);
	}

	private static void registerBlock(Block block, Identifier id) {
		Registry.register(Registry.BLOCK, id, block);
		Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(Mechanized.ITEM_GROUP)));

	}

	private static SoundEvent registerSoundEvent(Identifier id) {
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

	@SuppressWarnings("unchecked")
	private static <T extends BlockEntity> BlockEntityType<T> registerBlock(Block block, Identifier id,
			Supplier<BlockEntity> be) {
		registerBlock(block, id);
		return (BlockEntityType<T>) Registry.register(Registry.BLOCK_ENTITY_TYPE, id,
				BlockEntityType.Builder.create(be, block).build(null));
	}

	@SuppressWarnings("unchecked")
	private static <T extends BlockEntity> BlockEntityType<T> registerBlock(Block block, Identifier id,
			Supplier<BlockEntity> be, RenderLayerEnum layer) {
		registerBlock(block, id, layer);
		return (BlockEntityType<T>) Registry.register(Registry.BLOCK_ENTITY_TYPE, id,
				BlockEntityType.Builder.create(be, block).build(null));
	}
	private static void setRenderLayer(Block block, RenderLayerEnum layer) {
		switch (layer) {
		case CUTOUT:
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
			break;
		default:
			break;
		}
	}
	
	private static ToIntFunction<BlockState> createLightLevelFromBlockState(int litLevel) {
		return (blockState) -> {
			return (Boolean)blockState.get(Properties.LIT) ? litLevel : 0;
		};
	}
	
	private static void registerItem(Item item, Identifier id) {
		Registry.register(Registry.ITEM, id, item);
	}

	public static enum RenderLayerEnum {
		CUTOUT;
	}
}
