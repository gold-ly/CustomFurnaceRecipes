package de.lowkeys.customfurnacerecipes.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomFurnaceRecipes extends JavaPlugin {
    File errorFile;
    FileConfiguration errorConfiguration;

    @Override
    public void onEnable() {
        ItemStack itemStack = new ItemStack(Material.IRON_INGOT);
        itemStack.setAmount(2);

        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(itemStack, Material.IRON_SWORD);

        Bukkit.addRecipe(furnaceRecipe);

        generateErrorFile();
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

        for (String source : scrapList) {
            try {
                ItemStack resultStack = new ItemStack(Material.getMaterial(this.getConfig().getString(source + ".result")));

                resultStack.setAmount(this.getConfig().getInt(source + ".amount"));

                FurnaceRecipe furnaceRecipe = new FurnaceRecipe(resultStack, Material.getMaterial(source));
                furnaceRecipe.setCookingTime(this.getConfig().getInt(source + ".time"));
                furnaceRecipe.setExperience(this.getConfig().getInt(source + ".exp"));

                Bukkit.addRecipe(furnaceRecipe);
            } catch (Exception exception) {
                System.out.println("Error at entry: " + source);
                errorConfiguration.set(source, source);
                try {
                    errorConfiguration.save(errorFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void generateErrorFile() {
        errorFile = new File("plugins/CustomFurnaceRecipes", "errors.yml");
        if(!errorFile.exists()) {
            try {
                errorFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        errorConfiguration = YamlConfiguration.loadConfiguration(errorFile);
    }
}
