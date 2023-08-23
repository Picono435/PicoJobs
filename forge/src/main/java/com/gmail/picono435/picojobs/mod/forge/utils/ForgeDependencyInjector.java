package com.gmail.picono435.picojobs.mod.forge.utils;

import io.github.slimjar.injector.DependencyInjector;
import io.github.slimjar.injector.InjectionFailedException;
import io.github.slimjar.injector.helper.InjectionHelper;
import io.github.slimjar.injector.helper.InjectionHelperFactory;
import io.github.slimjar.injector.loader.Injectable;
import io.github.slimjar.injector.loader.InstrumentationInjectable;
import io.github.slimjar.resolver.ResolutionResult;
import io.github.slimjar.resolver.data.Dependency;
import io.github.slimjar.resolver.data.DependencyData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URLClassLoader;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ForgeDependencyInjector implements DependencyInjector {
    private final InjectionHelperFactory injectionHelperFactory;
    private final List<Dependency> processingDependencies = Collections.synchronizedList(new ArrayList());

    public ForgeDependencyInjector(InjectionHelperFactory injectionHelperFactory) {
        this.injectionHelperFactory = injectionHelperFactory;
    }

    public void inject(Injectable injectable, DependencyData data, Map<String, ResolutionResult> preResolvedResults) throws ReflectiveOperationException, NoSuchAlgorithmException, IOException, URISyntaxException {
        InjectionHelper helper = this.injectionHelperFactory.create(data, preResolvedResults);
        this.injectDependencies(injectable, helper, data.dependencies());
    }

    private void injectDependencies(Injectable injectable, InjectionHelper injectionHelper, Collection<Dependency> dependencies) throws RuntimeException {
        System.out.println("1");
        dependencies.stream().filter((dependency) -> {
            System.out.println("2 " + dependency.artifactId());
            return !injectionHelper.isInjected(dependency);
        }).forEach((dependency) -> {
            if (!this.processingDependencies.contains(dependency)) {
                System.out.println("3 " + dependency.artifactId());
                this.processingDependencies.add(dependency);
                System.out.println("4 " + dependency.artifactId());

                try {
                    File depJar = injectionHelper.fetch(dependency);
                    System.out.println("5 " + dependency.artifactId());
                    if (depJar == null) {
                        System.out.println("6 " + dependency.artifactId());
                        return;
                    }

                    System.out.println("7.5 " + depJar.toURI().toURL());
                    injectable.inject(depJar.toURI().toURL());
                    System.out.println("7 " + dependency.artifactId() + " " + (injectable instanceof URLClassLoader));
                    this.injectDependencies(injectable, injectionHelper, dependency.transitive());
                    System.out.println("8 " + dependency.artifactId());
                } catch (IOException var5) {
                    throw new InjectionFailedException(dependency, var5);
                } catch (InvocationTargetException | URISyntaxException | IllegalAccessException var6) {
                    var6.printStackTrace();
                } catch (InterruptedException | ReflectiveOperationException var7) {
                    throw new RuntimeException(var7);
                }

                this.processingDependencies.remove(dependency);
            }
        });
    }
}
