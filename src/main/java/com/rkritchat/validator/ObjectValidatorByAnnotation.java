package com.rkritchat.validator;

import com.rkritchat.anotation.Required;
import com.rkritchat.model.AddressModel;
import com.rkritchat.model.NoteBookModel;
import com.rkritchat.model.ProductModel;
import com.rkritchat.model.UserModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ObjectValidatorByAnnotation {

    public static void main(String... args) {
        String[] people = {"1"};
        NoteBookModel noteBookModel = new NoteBookModel();
        noteBookModel.setName("test");
        noteBookModel.setPrice(12);
        noteBookModel.setPeople(people);

        ProductModel productModel = new ProductModel();
        productModel.setNoteBookModel(noteBookModel);

        AddressModel addressModel = new AddressModel();
        addressModel.setTel("1234");
        addressModel.setState("test");
        addressModel.setRoad("ro");

        UserModel userModel = new UserModel();
        userModel.setProductModel(productModel);
        userModel.setAddress(addressModel);

        String result = validateObject(userModel, UserModel.class);
        System.out.println(result == null? null : result + " is required");
    }

    private static String validateObject(Object mainObject, Class<?> userModelClass) {
        try {
            if(mainObject == null){
                return userModelClass.getName().replace("com.rkritchat.model.","");
            }
            Field[] declaredFields = mainObject.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if(field.getAnnotation(Required.class)!=null){
                    field.setAccessible(true);
                    Object result = field.get(mainObject);
                    if (test(result)) {
                        return field.getName().replace("get", "").replace("Model", "");
                    } else {
                        String rp = validateObject(result, result.getClass());
                        if(rp != null) return rp;
                    }
                }
            }
        } catch (Exception err) {
            System.out.println(err);
        }


        // this is example for getting value from getter method instead of field
//        try {
//            for (Method method : mainObject.getClass().getDeclaredMethods()) {
//                if (isContainAnnotation(method)) {
//                    System.out.println("validate " + method.getName());
//                    Object result = method.invoke(mainObject);
//                    if (result == null || (result instanceof String && ((String) result).trim().equals("")) || (result instanceof Number && ((Number) result).longValue() == 0)) {
//                        return method.getName().replace("get", "").replace("Model", "");
//                    } else {
//                        String rp = validateObject(result, result.getClass());
//                        if(rp != null) return rp;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        return null;
    }

    private static boolean isContainAnnotation(Method method){
        return method.getAnnotation(Required.class) != null && method.getName().startsWith("get");
    }

    private static boolean test(Object obj){
        return obj == null ||
                (obj instanceof String && ((String) obj).trim().equals("")) ||
                (obj instanceof Number && ((Number) obj).longValue() == 0)  ||
                (obj instanceof List && ((List) obj).size() == 0)           ||
                (obj.getClass().isArray() && testArray(obj));
    }

    private static boolean testArray(Object obj){
        return (obj instanceof Byte[] && ((Byte[]) obj).length == 0) ||
                (obj instanceof Short[] && ((Short[]) obj).length == 0) ||
                (obj instanceof Integer[] && ((Integer[]) obj).length == 0) ||
                (obj instanceof Long[] && ((Long[]) obj).length == 0) ||
                (obj instanceof Float[] && ((Float[]) obj).length == 0) ||
                (obj instanceof Double[] && ((Double[]) obj).length == 0) ||
                (obj instanceof Character[] && ((Character[]) obj).length == 0) ||
                (obj instanceof Boolean[] && ((Boolean[]) obj).length == 0) ||
                (obj instanceof Object[] && ((Object[]) obj).length == 0);
    }
}
