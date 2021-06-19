//
	// MIT License
//
// Copyright (c) 2021 Vaishnav Anil
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//

package com.gmail.picono435.picojobs.utils;

import io.github.slimjar.relocation.RelocationRule;
import io.github.slimjar.relocation.Relocator;
import io.github.slimjar.relocation.facade.JarRelocatorFacade;
import io.github.slimjar.relocation.facade.JarRelocatorFacadeFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.codehaus.plexus.util.FileUtils;

public final class JarRelocator implements Relocator {
    private final Collection<RelocationRule> relocations;
    private final JarRelocatorFacadeFactory relocatorFacadeFactory;

    public JarRelocator(final Collection<RelocationRule> relocations, final JarRelocatorFacadeFactory relocatorFacadeFactory) {
        this.relocations = relocations;
        this.relocatorFacadeFactory = relocatorFacadeFactory;
    }

    @Override
    public void relocate(File input, File output) throws IOException, ReflectiveOperationException {
        if(input.getName().equals("configurate-core-4.1.1.jar") || input.getName().equals("configurate-gson-4.1.1.jar") 
        		|| input.getName().equals("configurate-yaml-4.1.1.jar") || input.getName().equals("configurate-hocon-4.1.1.jar")) {
        	System.out.println("Using exception for " + input.getName());
        	if(output.exists()) {
        		System.out.println("File was already relocated.");
        		return;
        	}
        	FileUtils.copyFile(input, output);
        	return;
        }
        output.getParentFile().mkdirs();
        output.createNewFile();
        final JarRelocatorFacade jarRelocator = relocatorFacadeFactory.createFacade(input,output, relocations);
        jarRelocator.run();
    }
}
