package fr.anthonydu77.modmoderation.managers;

import fr.anthonydu77.modmoderation.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by Anthonydu77 13/12/2020 inside the package - fr.anthonydu77.modmoderation.managers
 */

public enum ModFile {
    MESSAGES("messages.yml"),
    CONFIG("config.yml"),
    LANG("lang.yml");

    private final String fileName;
    private final File dataFolder;

    ModFile(String fileName) {
        this.fileName = fileName;
        this.dataFolder = Main.getInstance().getDataFolder();
    }

    public void create(Logger logger){
        if (fileName == null || fileName.isEmpty()){
            throw new IllegalArgumentException("RessourcePath cannot be null or empty");
        }
        InputStream in = Main.getInstance().getResource(fileName);
        if (in == null){
            throw new IllegalArgumentException("The ressource" + fileName + " cannot be found in the plugin jar");
        }

        if (!dataFolder.exists() && !dataFolder.mkdir()){
            logger.severe("Failed to make directory");
        }

        File outFile = getFile();
        try {
            if (!outFile.exists()) {
                logger.severe("Files not found, creation in progress ... ");
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int n;

                while ((n = in.read(buf)) >= 0){
                    out.write(buf, 0, n);
                }

                out.close();
                in.close();

                if (!outFile.exists()){
                    logger.severe("Unable to copy files");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile(){
        return new File(dataFolder, fileName);
    }

    public FileConfiguration getConfig(){
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration config){
        try {
            config.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }
}
