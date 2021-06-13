package de.lowkeys.customfurnacerecipes.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CustomFurnaceRecipes extends JavaPlugin {

    @Override
    public void onEnable() {
        ItemStack itemStack = new ItemStack(Material.IRON_INGOT);
        itemStack.setAmount(2);

        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(itemStack, Material.IRON_SWORD);

        Bukkit.addRecipe(furnaceRecipe);

        addDefaultConfig();
        generateRecipes();
    }

    private void addDefaultConfig() {
        ArrayList scrapList = new ArrayList();
        scrapList.add("IRON_SWORD");
        scrapList.add("GOLD_SWORD");

        this.getConfig().addDefault("scrapList", scrapList);

        this.getConfig().addDefault("IRON_SWORD.result", "IRON_INGOT");
        this.getConfig().addDefault("IRON_SWORD.amount", 2);

        this.getConfig().addDefault("GOLD_SWORD.result", "GOLD_INGOT");
        this.getConfig().addDefault("GOLD_SWORD.amount", 2);

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    private void generateRecipes() {
        ArrayList<String> scrapList = (ArrayList) this.getConfig().getList("scrapList");

        for(String source : scrapList) {
            ItemStack resultStack = new ItemStack(Material.getMaterial(this.getConfig().getString(source + ".result")));
            resultStack.setAmount(this.getConfig().getInt(source + ".amount"));

            FurnaceRecipe furnaceRecipe = new FurnaceRecipe(resultStack, Material.getMaterial(source));

            Bukkit.addRecipe(furnaceRecipe);
        }
    }
}
