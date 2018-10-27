package com.svennieke.AgeingMobs.block;

import java.util.Random;

import com.svennieke.AgeingMobs.Reference;
import com.svennieke.AgeingMobs.endermite.TileEndermite;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockEndermite extends Block implements ITileEntityProvider
{
	private Block originalBlock;
	
    public BlockEndermite(String registry, Block original)
    {
        super(Material.CLAY);
        this.setHardness(0.75F);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.originalBlock = original;
		this.setUnlocalizedName(Reference.MOD_PREFIX + registry.replaceAll("_", ""));
		this.setRegistryName(registry);
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    public static boolean canContainEndermite(IBlockState blockState)
    {
        Block block = blockState.getBlock();
        return block == Blocks.PURPUR_BLOCK || block == Blocks.PURPUR_PILLAR || block == Blocks.PURPUR_DOUBLE_SLAB;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
    	return new ItemStack(originalBlock);
    }

    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops"))
        {
            EntityEndermite entityendermite = new EntityEndermite(worldIn);
            entityendermite.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
            worldIn.spawnEntity(entityendermite);
            entityendermite.spawnExplosionParticle();
        }
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
    		EntityPlayer player) {
        return new ItemStack(this, 1, state.getBlock().getMetaFromState(state));
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEndermite();
	}
}
