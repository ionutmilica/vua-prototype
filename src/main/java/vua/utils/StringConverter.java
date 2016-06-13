package vua.utils;

public class StringConverter {

    public static Object convert(String input, Class<?> type) throws Exception {
        if (input == null) {
            return null;
        }
        switch (type.getName()) {
            case "short":
                return Short.parseShort(input);
            case "int":
                return Integer.parseInt(input);
            case "long":
                return Long.parseLong(input);
            case "float":
                return Float.parseFloat(input);
            case "double":
                return Double.parseDouble(input);
            case "byte":
                return Byte.parseByte(input);
            case "boolean":
                return Boolean.parseBoolean(input);
            case "java.lang.String":
            case "java.lang.Short":
            case "java.lang.Integer":
            case "java.lang.Double":
            case "java.lang.Float":
            case "java.lang.byte":
            case "java.lang.Boolean":
                return type.getConstructor(String.class).newInstance(input);
        }
        return input;
    }
}
