package com.gmail.picono435.picojobs.api.field;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A type for a Required Field. This class is currently only used internally for PicoJobs editor but might have other uses,
 * so it is extremely important to follow its usage.
 *
 * @param <P> The type of primitive where the value gets parsed from
 * @param <V> The type of the value
 */
public abstract class RequiredFieldType<P, V> {

    private Class<P> primitiveType;
    private Class<V> valueType;

    public RequiredFieldType(Class<P> primitiveType, Class<V> valueType) {
        this.primitiveType = primitiveType;
        this.valueType = valueType;
    }

    /**
     * Parses the primitive to the value of the required field type
     *
     * @param primitive the primitive to parse from
     * @return the parsed value
     */
    @CheckForNull
    public abstract V toValue(@Nonnull P primitive);

    /**
     * Parses the primitive list to a list of values of the required field type
     *
     * @param primitiveList the list of primitives to parse from
     * @return the parsed value
     */
    @Nonnull
    public List<V> toValueList(List<P> primitiveList) {
        return primitiveList.stream().map(this::toValue).collect(Collectors.toList());
    }

    /**
     * Parses the value to the primitive of the required field type
     *
     * @param value the value to parse from
     * @return the parsed primitive
     */
    @Nonnull
    public abstract P toPrimitive(@CheckForNull V value);

    /**
     * Parses the value list to a list of primitives of the required field type
     *
     * @param valueList the list of values to parse from
     * @return the parsed value
     */
    @Nonnull
    public List<P> toPrimitiveList(List<V> valueList) {
        return valueList.stream().map(this::toPrimitive).collect(Collectors.toList());
    }

    /**
     * Finds suggestions that can be used values for this required field type
     *
     * @return a list with suggestions
     */
    @Nonnull
    public abstract List<V> getSuggestions();

    /**
     * Finds and parses suggestions that can be used values for this required field type as primitives
     *
     * @return a list with primitive suggestions
     */
    @Nonnull
    public List<P> getPrimitiveSuggestions() {
        return getSuggestions().stream().map(this::toPrimitive).collect(Collectors.toList());
    }

    /**
     * Returns the primitive's class type
     *
     * @return the primitive type
     */
    @Nonnull
    public Class<P> getPrimitiveType() {
        return primitiveType;
    }

    /**
     * Returns the value's class type
     *
     * @return the value type
     */
    @Nullable
    public Class<V> getValueType() {
        return valueType;
    }
}
