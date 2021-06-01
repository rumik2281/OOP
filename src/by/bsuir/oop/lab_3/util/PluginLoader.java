package by.bsuir.oop.lab_3.util;

import plugins.EncryptorPlugin;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {
    private static final String PATH_TO_PLUGINS = "C:\\Users\\Kiril\\IdeaProjects\\lab_3\\plugins";

    public Class<? extends EncryptorPlugin> receivePlugin(String findClassName) {
        Class<? extends EncryptorPlugin> pluginClass = null;
        File pluginDir = new File(PATH_TO_PLUGINS);
        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        for (File fileJar : jars) {
            try (JarFile jarFile = new JarFile(fileJar)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                URL path = fileJar.toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{path});
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.isDirectory()) {
                        continue;
                    }
                    String name = entry.getName();
                    String ext = receiveFileExtension(name).toLowerCase();

                    if (ext.equals("class")) {
                        String className = name.substring(0, name.length() - 6).replace('/', '.');
                        if (className.equals("plugins." + findClassName)) {
                            Class<?> thisClass = classLoader.loadClass(className);
                            Class<?>[] interfaces = thisClass.getInterfaces();
                            for (Class<?> c : interfaces) {
                                if (c.equals(EncryptorPlugin.class)) {
                                    pluginClass = (Class<? extends EncryptorPlugin>) thisClass;
                                    return pluginClass;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pluginClass;
    }

    private String receiveFileExtension(String name) {
        int index = name.lastIndexOf('.') + 1;
        if (index > 0) {
            return name.substring(index);
        }
        return "";
    }
}
