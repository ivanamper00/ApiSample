package com.ivandeveloper.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModel {

    @SerializedName("categories")
    private List<Category> categories = null;

    public static class Category
    {
        @SerializedName("idCategory")
        private String idCategory;
        @SerializedName("strCategory")
        private String strCategory;
        @SerializedName("strCategoryThumb")
        private String strCategoryThumb;
        @SerializedName("strCategoryDescription")
        private String strCategoryDescription;

         public Category(String idCategory, String strCategory, String strCategoryThumb, String strCategoryDescription)
        {
            super();
            this.idCategory = idCategory;
            this.strCategory = strCategory;
            this.strCategoryThumb = strCategoryThumb;
            this.strCategoryDescription = strCategoryDescription;
        }


        public String getIdCategory () {
            return idCategory;
        }

        public void setIdCategory (String idCategory){
            this.idCategory = idCategory;
        }

        public String getStrCategory () {
            return strCategory;
        }

        public void setStrCategory (String strCategory){
            this.strCategory = strCategory;
        }

        public String getStrCategoryThumb () {
            return strCategoryThumb;
        }

        public void setStrCategoryThumb (String strCategoryThumb){
            this.strCategoryThumb = strCategoryThumb;
        }

        public String getStrCategoryDescription () {
            return strCategoryDescription;
        }

        public void setStrCategoryDescription (String strCategoryDescription){
            this.strCategoryDescription = strCategoryDescription;
        }
    }

    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> data) {
        this.categories = categories;
    }
}

