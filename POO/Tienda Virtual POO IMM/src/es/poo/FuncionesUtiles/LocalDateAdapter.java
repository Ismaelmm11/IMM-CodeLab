package es.poo.FuncionesUtiles;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase adaptadora para la serialización y deserialización de objetos LocalDate en formato JSON.
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
	
	// Formato de fecha utilizado para la serialización y deserialización.
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(formatter.format(date));
    }
    @Override
    /**
     * Deserializa un elemento JSON a un objeto LocalDate.
     * @param jsonElement Elemento JSON que representa un objeto LocalDate.
     * @param type Tipo de objeto.
     * @param jsonDeserializationContext Contexto de deserialización JSON.
     * @return Objeto LocalDate deserializado.
     * @throws JsonParseException Si hay un error durante la deserialización.
     */
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return LocalDate.parse(jsonElement.getAsString(), formatter);
	}
}

