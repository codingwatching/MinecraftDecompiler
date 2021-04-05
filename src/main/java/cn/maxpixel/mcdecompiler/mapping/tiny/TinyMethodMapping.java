/*
 * MinecraftDecompiler. A tool/library to deobfuscate and decompile Minecraft.
 * Copyright (C) 2019-2021  MaxPixelStudios
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.maxpixel.mcdecompiler.mapping.tiny;

import cn.maxpixel.mcdecompiler.asm.MappingRemapper;
import cn.maxpixel.mcdecompiler.mapping.ClassMapping;
import cn.maxpixel.mcdecompiler.mapping.TinyClassMapping;
import cn.maxpixel.mcdecompiler.mapping.base.DescriptoredBaseMethodMapping;
import cn.maxpixel.mcdecompiler.mapping.components.Documented;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.nio.file.Path;

public class TinyMethodMapping extends DescriptoredBaseMethodMapping implements cn.maxpixel.mcdecompiler.mapping.components.Namespaced, Documented {
    private final Int2ObjectOpenHashMap<String> lvt = new Int2ObjectOpenHashMap<>();
    private final Int2ObjectOpenHashMap<String> lvtDoc = new Int2ObjectOpenHashMap<>();
    private final Object2ObjectOpenHashMap<String, String> names = new Object2ObjectOpenHashMap<>();
    private String document;

    public TinyMethodMapping(String unmappedDescriptor, Namespaced... names) {
        for(Namespaced namespaced : names) this.names.put(namespaced.getNamespace(), namespaced.getName());
        setUnmappedDescriptor(unmappedDescriptor);
    }

    @Override
    public String getName(String namespace) {
        return names.get(namespace);
    }

    @Override
    public void setName(String namespace, String name) {
        names.put(namespace, name);
    }

    @Override
    public TinyClassMapping getOwner() {
        return (TinyClassMapping) super.getOwner();
    }

    @Override
    public TinyMethodMapping setOwner(ClassMapping owner) {
        if(!(owner instanceof TinyClassMapping)) throw new IllegalArgumentException("TinyMethodMapping's owner must be TinyClassMapping");
        return this.setOwner((TinyClassMapping) owner);
    }

    public TinyMethodMapping setOwner(TinyClassMapping owner) {
        super.setOwner(owner);
        return this;
    }

    /** Recommend to use {@link #getName(String)} */
    @Override
    public String getUnmappedName() {
        String s = getName(Namespaced.OFFICIAL);
        return s == null ? getName(Namespaced.INTERMEDIARY) : s;
    }

    /** Recommend to use {@link #getName(String)} */
    @Override
    public String getMappedName() {
        String s = getName(Namespaced.YARN);
        return s == null ? getName(Namespaced.INTERMEDIARY) : s;
    }

    /**
     * @deprecated Use {@link #setName(String, String)} instead.
     * @throws UnsupportedOperationException When calling this method
     */
    @Override
    @Deprecated
    public void setUnmappedName(String unmappedName) {
        throw new UnsupportedOperationException();
    }

    /**
     * @deprecated Use {@link #setName(String, String)} instead.
     * @throws UnsupportedOperationException When calling this method
     */
    @Override
    @Deprecated
    public void setMappedName(String mappedName) {
        throw new UnsupportedOperationException();
    }

    public void setLocalVariableName(int index, String name) {
        lvt.put(index, name);
    }

    public void setLocalVariableDocument(int index, String document) {
        lvtDoc.put(index, document);
    }

    public String getLocalVariableDocument(int index) {
        return lvtDoc.get(index);
    }

    public String getLocalVariableName(int index) {
        return lvt.get(index);
    }

    /**
     * @deprecated Use {@link cn.maxpixel.mcdecompiler.deobfuscator.TinyDeobfuscator#deobfuscate(Path, Path, boolean, String, String)}
     * @throws UnsupportedOperationException When calling this method
     */
    @Override
    @Deprecated
    public void reverse(MappingRemapper remapper) {
        throw new UnsupportedOperationException();
    }

    /** @deprecated See {@link #reverse()} for more info */
    @Override
    @Deprecated
    public void reverse0(MappingRemapper remapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String getDocument() {
        return document;
    }
}