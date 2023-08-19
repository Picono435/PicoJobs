package com.gmail.picono435.picojobs.mod.forge.mixin;

import com.gmail.picono435.picojobs.mod.PicoJobsMod;
import io.github.slimjar.app.module.ModuleNotFoundException;
import io.github.slimjar.app.module.TemporaryModuleExtractor;
import io.github.slimjar.injector.agent.ByteBuddyInstrumentationFactory;
import io.github.slimjar.injector.loader.InstrumentationInjectable;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

@Mixin(value = TemporaryModuleExtractor.class, remap = false)
public class ByteBuddyInstrumentationFactoryMixin {

        /**
         * @author
         * @reason
         */
        @Overwrite()
        public URL extractModule(URL url, String name) throws IOException {
                url = new URL("jar:" + ModList.get().getModFileById(PicoJobsMod.MOD_ID).getFile().getFilePath().toFile().toURI().toURL() + "!/" + name + ".isolated-jar");
                System.err.println(url + " ADOIFHJGDASPGAP " + name);
                File tempFile = File.createTempFile(name, ".jar");
                tempFile.deleteOnExit();
                URLConnection connection = url.openConnection();
                if (!(connection instanceof JarURLConnection jarURLConnection)) {
                        System.err.println("RIP 1");
                        throw new AssertionError("Invalid Module URL provided(Non-Jar File)");
                } else {
                        JarFile jarFile = jarURLConnection.getJarFile();
                        ZipEntry module = jarFile.getJarEntry(name + ".isolated-jar");
                        if (module == null) {
                                System.err.println("RIP 2");
                                throw new ModuleNotFoundException(name);
                        } else {
                                InputStream inputStream = jarFile.getInputStream(module);

                                try {
                                        Files.copy(inputStream, tempFile.toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                                } catch (Throwable var12) {
                                        if (inputStream != null) {
                                                try {
                                                        inputStream.close();
                                                } catch (Throwable var11) {
                                                        System.err.println("RIP 3");
                                                        var12.addSuppressed(var11);
                                                }
                                        }

                                        throw var12;
                                }

                                if (inputStream != null) {
                                        inputStream.close();
                                }

                                return tempFile.toURI().toURL();
                        }
                }
        }

}
