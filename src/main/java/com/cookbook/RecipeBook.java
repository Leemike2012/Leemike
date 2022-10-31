package com.cookbook;

import java.util.Comparator;
import java.util.List;

public class RecipeBook {
    /** 食谱集合 */
    private List<Recipe> recipeList;
    /** 食谱集合数量 */
    private Integer numberOfRecipes;

    /** 食谱集合 */
    public List<Recipe> getRecipeList(){
        return this.recipeList;
    }
    /** 食谱集合 */
    public void setRecipeList(List<Recipe> recipeList){
        this.recipeList = recipeList;
    }
    /** 食谱集合数量 */
    public Integer getNumberOfRecipes(){
        return this.numberOfRecipes;
    }
    /** 食谱集合数量 */
    public void setNumberOfRecipes(Integer numberOfRecipes){
        this.numberOfRecipes = numberOfRecipes;
    }

    //无参数
    public RecipeBook(){}

    //正常构造
    public RecipeBook(List<Recipe> recipeList){
        this.recipeList =recipeList;
        this.numberOfRecipes = recipeList.size();
    }

    //添加单个食谱
    public void addRecipe(Recipe recipe){
        this.recipeList.add(recipe);
        //根据食谱名排序
        this.recipeList.sort(Comparator.comparing(Recipe::getName));
    }

    //根据食谱名获取食谱
    public Recipe findRecipe(String name){
        for (int i = 0; i < this.recipeList.size(); i++) {
            if (name.equals(this.recipeList.get(i).getName())){
                return recipeList.get(i);
            }
        }
        return null;
    }
    //删除食谱
    public void removeRecipe(String name){
        for (int i = 0; i < this.recipeList.size(); i++) {
            if (name.equals(this.recipeList.get(i).getName())){
                this.recipeList.remove(i);
                i--;
            }
        }
    }

    @Override
    public String toString(){
        String rs = "食谱列表：";
        for (int i = 0; i < this.recipeList.size(); i++) {
            rs += recipeList.get(i).getName()+"-";
        }
        return rs;
    }
}
