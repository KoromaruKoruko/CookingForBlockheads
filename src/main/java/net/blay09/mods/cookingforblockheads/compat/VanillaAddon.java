package net.blay09.mods.cookingforblockheads.compat;

import net.blay09.mods.cookingforblockheads.api.CookingForBlockheadsAPI;
import net.blay09.mods.cookingforblockheads.api.FoodStatsProvider;
import net.blay09.mods.cookingforblockheads.api.SinkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class VanillaAddon implements FoodStatsProvider {

    public VanillaAddon() {
        SinkHandler simpleHandler = itemStack -> {
			ItemStack result = itemStack.copy();
			result.setCount(1);
			result.setItemDamage(0);
			return result;
		};
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE), simpleHandler);
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE), simpleHandler);
        SinkHandler armorHandler = itemStack -> {
			if(itemStack.getItem() instanceof ItemArmor) {
				((ItemArmor) itemStack.getItem()).removeColor(itemStack);
			}
			return itemStack;
		};
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.LEATHER_BOOTS, 1, OreDictionary.WILDCARD_VALUE), armorHandler);
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.LEATHER_CHESTPLATE, 1, OreDictionary.WILDCARD_VALUE), armorHandler);
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.LEATHER_HELMET, 1, OreDictionary.WILDCARD_VALUE), armorHandler);
        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.LEATHER_LEGGINGS, 1, OreDictionary.WILDCARD_VALUE), armorHandler);

        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.MILK_BUCKET), itemStack -> new ItemStack(Items.BUCKET, 1));

        CookingForBlockheadsAPI.addSinkHandler(new ItemStack(Items.POTIONITEM, 1, OreDictionary.WILDCARD_VALUE), itemStack -> new ItemStack(Items.GLASS_BOTTLE, 1));

        CookingForBlockheadsAPI.setFoodStatsProvider(this);
    }

    @Override
    public float getSaturation(ItemStack itemStack, EntityPlayer entityPlayer) {
        ItemFood item = (ItemFood) itemStack.getItem();
        return item.getSaturationModifier(itemStack);
    }

    @Override
    public int getFoodLevel(ItemStack itemStack, EntityPlayer entityPlayer) {
        ItemFood item = (ItemFood) itemStack.getItem();
        return item.getHealAmount(itemStack);
    }
}
