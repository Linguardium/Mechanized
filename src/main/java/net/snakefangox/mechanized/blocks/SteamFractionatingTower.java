package net.snakefangox.mechanized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.snakefangox.mechanized.MRegister;

public class SteamFractionatingTower extends Block implements BlockEntityProvider {

	public static final IntProperty LEVEL = IntProperty.of("level", 0, 2);

	public SteamFractionatingTower(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(LEVEL, 0));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.isAir(pos.offset(Direction.UP)) && world.isAir(pos.offset(Direction.UP, 2));
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		world.setBlockState(pos.offset(Direction.UP), getDefaultState().with(LEVEL, 1));
		world.setBlockState(pos.offset(Direction.UP, 2), getDefaultState().with(LEVEL, 2));
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		super.onStateReplaced(state, world, pos, newState, moved);

		int level = state.get(LEVEL);
		world.setBlockEntity(pos, null);
		if (level != 0) {
			world.setBlockState(pos.offset(Direction.DOWN), Blocks.AIR.getDefaultState());
		}
		if (level > 1) {
			world.setBlockState(pos.offset(Direction.DOWN, 2), Blocks.AIR.getDefaultState());
		}
		if (level < 2) {
			world.setBlockState(pos.offset(Direction.UP), Blocks.AIR.getDefaultState());
		}
		if (level < 1) {
			world.setBlockState(pos.offset(Direction.UP, 2), Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockView view) {
		return MRegister.STEAM_FRACTIONATING_TOWER_ENTITY.instantiate();
	}
}
