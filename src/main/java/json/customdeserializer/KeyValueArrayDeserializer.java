package json.customdeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class KeyValueArrayDeserializer<T> extends StdDeserializer<T> {

    private Map<String, Field> fields;
    private Class<T> clazz;
    private String keyTitle;
    private String valueTitle;

    public KeyValueArrayDeserializer(Class<T> vc, String keyTitle, String valueTitle) {
        super(vc);
        this.fields = Arrays.stream(vc.getDeclaredFields())
                .collect(toMap(x -> x.getName(), x -> x));
        clazz = vc;
        this.keyTitle = keyTitle;
        this.valueTitle = valueTitle;
    }

    @SneakyThrows
    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        JsonNode jsonNode = jsonParser.readValueAsTree();
        Constructor<T> c = clazz.getConstructor();
        T o = c.newInstance();
        if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode node = jsonNode.get(i);
                String fieldName = node.get(keyTitle).asText();
                if (fields.containsKey(fieldName)) {
                    Field f = fields.get(fieldName);
                    Method m = getSetter(f, clazz);
                    String value = node.get(valueTitle).asText();
                    if (value.equals("null")) {
                        continue;
                    }
                    m.invoke(o, value);
                }
            }
        }
        return o;
    }

    private Method getSetter(Field field, Class<T> c) throws NoSuchMethodException {
        StringBuilder sb = new StringBuilder("set");
        String name = field.getName();
        sb.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
        Method m = c.getDeclaredMethod(sb.toString(), field.getType());
        return m;
    }


}
