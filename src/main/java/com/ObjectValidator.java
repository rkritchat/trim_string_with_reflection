package com;

import model.NoteBookModel;
import model.ProductModel;
import model.UserModel;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectValidator {

    public static void main(String... args) {

//		System.out.println(validateString(" a"));
//		System.out.println(validateList(Arrays.asList("test",null)));
//		System.out.println(isContainString(null));
//		isNumeric("1234", "Invalid data");

        NoteBookModel noteBookModel = new NoteBookModel();
        noteBookModel.setName("test");
        noteBookModel.setPrice(10);

        ProductModel productModel = new ProductModel();
        productModel.setNoteBookModel(noteBookModel);

        UserModel userModel = new UserModel();
        userModel.setProductModel(productModel);
        String text = "userModel.getProductModel().getNoteBookModel()->.getName().getPrice().getTest()";
        //String text = "userModel.getProductModel().getNoteBookModel().getName()";
        System.out.println(validateObject(userModel, text));  //userModel.getProductModel().getNoteBookModel().getName()
    }

    /**
     *
     * This method use for checking text style
     * The validator allow two method style
     * I. checking object inside object [normal style]
     *    - Ex
     *          INPUT : userModel.getProductModel().getNoteBookModel().getName()
     *          PROCESS :
     *                   1. check getProductModel object if null return Product
     *                   2. check getNoteBookModel that belong to ProductModel object if null return NoteBook
     *                   3. check getName that belong to NoteBookModel object if null return Name
     *                   ** if all method not null then return null
     *
     * II. checking object inside object and all method of last object [arrow style]
     *    - Ex
     *          INPUT : userModel.getProductModel().getNoteBookModel()->.getName().getPrice().getTest()
     *          PROCESS :
     *                   1. split text from allow format to normal format result will be list of String
     *                      - userModel.getProductModel().getNoteBookModel().getName()
     *                      - userModel.getProductModel().getNoteBookModel().getPrice()
     *                      - userModel.getProductModel().getNoteBookModel().getTest()
     *                   2. process on normal style
     *                  ** PS if using this style system will store catch for avoid to getting getter method
     */
    private static String validateObject(Object object, String text) {
        List<String> method = initMethod(text);
        Map<String, Object> storage = new HashMap<>();
        for (String target : method) {
            System.out.println("validate " + target);
            List<String> methods = removeBrackets(target);
            String result = execute(object, methods, 0, storage);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    /**
     *   Checking style
     *   this method allow two style
     *
     *   I.  Normal style
     *      - EX userModel.getProductModel().getNoteBookModel().getName()
     *
     *   II. Arrow style
     *      - EX userModel.getProductModel().getNoteBookModel()->.getName().getPrice().getTest()
     *
     */
    private static List<String> initMethod(String text) {
        if (text.contains("->")) {
            String[] str = text.split("->");
            String[] split = str[1].split("\\.");
            return Stream.of(split).filter(e -> !e.equals("")).map(e -> str[0] + "." + e).collect(Collectors.toList());
        } else {
            return Collections.singletonList(text);
        }
    }

    /**
     *
     *  This is recursive method that use for calling searchGetterMethod
     *  This method support catch and will exist when all method is validated.
     */
    private static String execute(Object object, List<String> methods, int counter, Map<String, Object> storage) {
        try {
            Object cache = storage.get(methods.get(counter));
            Object rp = cache !=null ? cache : searchGetterMethod(object, methods.get(counter));
            if (rp == null || (rp instanceof String && ((String) rp).trim().equals("")) || (rp instanceof Number && ((Number) rp).longValue() == 0)) {
                return methods.get(counter).replace("get", "").replace("Model", "");
            } else {
                if (cache == null) storage.put(methods.get(counter), rp);
                return methods.size() == counter + 1 ? null : execute(rp, methods, ++counter, storage);
            }
        } catch (Exception err) {
            System.out.println(err);
        }
        return null;
    }

    /**
     *  This method for get object by using reflection, the method will searching method name by target
     *  if found then return result of invoking <getter> method
     *
     */
    private static Object searchGetterMethod(Object object, String target) {
        try {
            if(target == null || !target.contains("get")) return null;
            Method[] declaredMethods = object.getClass().getDeclaredMethods();
            for (Method method : declaredMethods) {
                if(target.equals(method.getName())){
                    return method.invoke(object);
                }
            }
        } catch (Exception err) {
            System.out.println("Exception occurred" + err);
        }
        return null;
    }

    /**
     *
     *  This method use for remove Brackets and split the getter method
     */
    private static List<String> removeBrackets(String text) {
        String[] split = text.split("\\.");
        return Stream.of(split).filter(e -> e.contains("()")).map(e -> e.replace("()", "")).collect(Collectors.toList());
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