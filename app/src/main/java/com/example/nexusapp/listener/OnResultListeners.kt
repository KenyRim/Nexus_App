package com.example.nexusapp.listener

import com.example.nexusapp.models.CategoryModel

class OnResultListeners {

    interface Categories{
        fun getResult(data: List<Pair<String,String>>)
    }

    interface Category{
        fun getResult(data: List<CategoryModel>)
    }


}