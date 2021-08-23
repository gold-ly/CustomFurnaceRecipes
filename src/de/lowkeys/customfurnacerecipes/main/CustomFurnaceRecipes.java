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

        this.getConfig().addDefault("scrapList", scrapList);

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    private void generateRecipes() {
        ArrayList<String> scrapList;

        try {
            scrapList = (ArrayList) this.getConfig().getList("scrapList");
        } catch (Exception exception) {
            System.out.println("Couldn't find config entry 'scrapList'");
            return;
        }

        try {
            for(String source : scrapList) {
                ItemStack resultStack = new ItemStack(Material.getMaterial(this.getConfig().getString(source + ".result")));
                resultStack.setAmount(this.getConfig().getInt(source + ".amount"));

                FurnaceRecipe furnaceRecipe = new FurnaceRecipe(resultStack, Material.getMaterial(source));

                Bukkit.addRecipe(furnaceRecipe);
            }
        } catch (Exception exception) {
            System.out.println("There was an error loading the config file, check the entrys for mistakes.");
        }


    }
}
