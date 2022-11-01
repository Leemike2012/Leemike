package com.cookbook;

import java.util.ArrayList;
import java.util.List;

public class TestRecipe {
    public static void main(String[] args){
        //红烧肉，无参构造初始化
        Recipe braisedPork = new Recipe();
        braisedPork.setName("BraisedPork");
        String[] bpInstructions = {"Step1","Step2","Step3"};
        String[] bpIngredients ={"Pork","Water"};
        braisedPork.setInstructions(bpInstructions);
        braisedPork.setNumberOfInstructions(bpInstructions.length);
        braisedPork.setIngredients(bpIngredients);
        braisedPork.setNumberOfIngredients(bpIngredients.length);
        System.out.println(braisedPork.toString());

        //土豆牛肉，有参构造
        String[] pbInstructions = {"Step1","Step2","Step3","Step4"};
        String[] pbIngredients = {"Potato","Beef","Water"};
        Recipe potatoBeef = new Recipe("PotatoBeef",pbInstructions,pbIngredients);
        System.out.println(potatoBeef.toString());

        //RecipeEquals方法
        System.out.println(braisedPork.equals(potatoBeef));

        //食谱书 有参构造
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(braisedPork);
        recipeList.add(potatoBeef);
        RecipeBook recipeBook = new RecipeBook(recipeList);
        System.out.println(recipeBook.toString());
        //添加食谱并排序
        recipeBook.addRecipe(braisedPork);
        System.out.println(recipeBook.toString());
        //查找食谱
        Recipe r = recipeBook.findRecipe("PotatoBeef");
        System.out.println(r.toString());
        //删除食谱
        recipeBook.removeRecipe("BraisedPork");
        System.out.println(recipeBook);
    }
}
