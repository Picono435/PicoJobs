package com.gmail.picono435.picojobs.api.field.type;

import com.gmail.picono435.picojobs.api.field.RequiredFieldType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class StringRequiredFieldType extends RequiredFieldType<String, String> {

    public StringRequiredFieldType() {
        super(String.class, String.class);
    }

    @Override
    public String toValue(@Nonnull String primitive) {
        return primitive;
    }

    @Nonnull
    @Override
    public String toPrimitive(String value) {
        return value;
    }

    @Nonnull
    @Override
    public List<String> getSuggestions() {
        return new ArrayList<>();
    }
}
