package com.rkritchat.validator;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;

public class validator {
    protected static boolean test(Object obj){
        return obj == null ||
                (obj instanceof String && ((String) obj).trim().equals("")) ||
                (obj instanceof Number && ((Number) obj).longValue() == 0)  ||
                (obj instanceof List && ((List) obj).size() == 0)           ||
                (obj.getClass().isArray() && testArray(obj));
    }

    protected static boolean testArray(Object obj){
        return (obj instanceof Byte[] && ((Byte[]) obj).length == 0)            ||
                (obj instanceof Short[] && ((Short[]) obj).length == 0)         ||
                (obj instanceof Integer[] && ((Integer[]) obj).length == 0)     ||
                (obj instanceof Long[] && ((Long[]) obj).length == 0)           ||
                (obj instanceof Float[] && ((Float[]) obj).length == 0)         ||
                (obj instanceof Double[] && ((Double[]) obj).length == 0)       ||
                (obj instanceof Character[] && ((Character[]) obj).length == 0) ||
                (obj instanceof Boolean[] && ((Boolean[]) obj).length == 0)     ||
                (obj instanceof Blob[] && ((Blob[]) obj).length == 0)           ||
                (obj instanceof Object[] && ((Object[]) obj).length == 0);
    }

    private boolean validateString(String text){
        return Optional.ofNullable(text).map(String::trim).filter(e -> e.length() > 0).isPresent();
    }

    private boolean validateList(List<?> test) {
        return Optional.ofNullable(test).map(List::size).orElse(0) > 0;
    }

    private boolean isContainString(String text) {
        return Optional.ofNullable(text).filter(e -> e.contains(" ")).isPresent();
    }

    private void isNumeric(String text, String message){
        // Optional.ofNullable(text).filter(StringUtils::isNumeric).orElseThrow(() -> new RuntimeException(message));
    }
}
