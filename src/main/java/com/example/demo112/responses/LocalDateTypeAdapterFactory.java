package com.example.demo112.responses;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        if (!LocalDate.class.isAssignableFrom(typeToken.getRawType())) {
            return null;
        }

        TypeAdapter<T> defaultAdapter = (TypeAdapter<T>) gson.getDelegateAdapter(this, typeToken);
        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    LocalDate localDate = (LocalDate) value;
                    String formattedDate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
                    out.value(formattedDate);
                }
            }

            @Override
            public T read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                } else {
                    String dateString = in.nextString();
                    LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
                    return (T) localDate;
                }
            }
        }.nullSafe();
    }
}