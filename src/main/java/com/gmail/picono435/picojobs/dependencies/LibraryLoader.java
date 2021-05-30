/*
 * This file is part of helper, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.gmail.picono435.picojobs.dependencies;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

/**
 * Resolves {@link MavenLibrary} annotations for a class, and loads the dependency
 * into the classloader.
 */
public final class LibraryLoader {

    private static final Supplier<Method> ADD_URL_METHOD = Suppliers.memoize(() -> {
        try {
            Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addUrlMethod.setAccessible(true);
            return addUrlMethod;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    });

    /**
     * Resolves all required dependencies and loasd them.
     *
     */
    public static void loadAllRequired() {
        for (Dependency lib : Dependency.getRequiredDependencies()) {
        	for(Dependency dep : lib.getDependencies()) {
        		load(dep);
        	}
            load(lib);
        }
    }

    public static void load(Dependency d) {
    	for(Dependency dep : d.getDependencies()) {
    		load(dep);
    	}
        PicoJobsPlugin.getInstance().getLogger().info(String.format("Loading dependency %s:%s:%s from %s", d.getGroupId(), d.getArtifactId(), d.getVersion(), d.getRepoUrl()));
        String name = d.getArtifactId() + "-" + d.getVersion();

        File saveLocation = new File(getLibFolder(), name + ".jar");
        if (!saveLocation.exists()) {

            try {
                PicoJobsPlugin.getInstance().getLogger().info("Dependency '" + name + "' is not already in the libraries folder. Attempting to download...");
                URL url = d.getUrl();
                
                try (InputStream is = url.openStream()) {
                    Files.copy(is, saveLocation.toPath());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            PicoJobsPlugin.getInstance().getLogger().info("Dependency '" + name + "' successfully downloaded.");
        }

        if (!saveLocation.exists()) {
            throw new RuntimeException("Unable to download dependency: " + d.toString());
        }

        URLClassLoader classLoader = (URLClassLoader) PicoJobsPlugin.getInstance().getClass().getClassLoader();
        try {
            ADD_URL_METHOD.get().invoke(classLoader, saveLocation.toURI().toURL());
        } catch (Exception e) {
            throw new RuntimeException("Unable to load dependency: " + saveLocation.toString(), e);
        }
        
        PicoJobsPlugin.getInstance().getLogger().info("Loaded dependency '" + name + "' successfully.");
    }

    private static File getLibFolder() {
        File pluginDir = PicoJobsPlugin.getInstance().getDataFolder();

        File libs = new File(pluginDir, "libraries");
        libs.mkdirs();
        return libs;
    }


}
