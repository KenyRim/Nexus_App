package com.example.nexusapp.listener

import com.example.nexusapp.models.CategoryModel

interface OnResultListener {
    fun getResult(data: List<CategoryModel>)

}