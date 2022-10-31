package com.cookbook;

import java.util.Arrays;
import java.util.Objects;

public class Recipe {
    /** 食谱名称 */
    private String name ;
    /** 烹饪教程步骤 */
    private String[] instructions ;
    /** 步骤数量 */
    private Integer numberOfInstructions ;
    /** 材料 */
    private String[] ingredients ;
    /** 材料数量 */
    private Integer numberOfIngredients ;

    /** 食谱名称 */
    public String getName(){
        return this.name;
    }
    /** 食谱名称 */
    public void setName(String name){
        this.name=name;
    }
    /** 烹饪教程步骤 */
    public String[] getInstructions(){
        return this.instructions;
    }
    /** 烹饪教程步骤 */
    public void setInstructions(String[] instructions){
        this.instructions=instructions;
    }
    /** 步骤数量 */
    public Integer getNumberOfInstructions(){
        return this.numberOfInstructions;
    }
    /** 步骤数量 */
    public void setNumberOfInstructions(Integer numberOfInstructions){
        this.numberOfInstructions=numberOfInstructions;
    }
    /** 材料 */
    public String[] getIngredients(){
        return this.ingredients;
    }
    /** 材料 */
    public void setIngredients(String[] ingredients){
        this.ingredients=ingredients;
    }
    /** get材料数量 */
    public Integer getNumberOfIngredients(){
        return this.numberOfIngredients;
    }
    /** set材料数量 */
    public void setNumberOfIngredients(Integer numberOfIngredients){
        this.numberOfIngredients=numberOfIngredients;
    }
    //无参数
    public Recipe(){
    }
    //全参数
    public Recipe(String name,String[] instructions,String[] ingredients){
        this.name = name;
        this.instructions = instructions;
        this.numberOfInstructions = instructions.length;
        this.ingredients = ingredients;
        this.numberOfIngredients = ingredients.length;
    }
    //菜谱名构造
    public Recipe(String name){
        this.name = name;
    }

    //tostring方法
    @Override
    public String toString(){
        return  "食谱-"+name+"-共需要"+numberOfInstructions+"步-共需"+numberOfIngredients+"种材料";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //判断是否是同一个类型
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        //判断材料是否有相同,
        for (int i = 0; i < recipe.numberOfIngredients; i++) {
            for (int j = 0; j < numberOfIngredients; j++) {
                if (recipe.ingredients[i].equals(ingredients[j])){
                    return true;
                }
            }
        }
        return false;
    }
}
