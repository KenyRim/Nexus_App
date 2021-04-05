package com.example.nexusapp.parser;

import android.util.Log
import com.example.nexusapp.listener.OnResultListener
import com.example.nexusapp.models.CategoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.select.Elements

public class CategoryParser {


    //#mainContent > section > div > div.col-1-1.grey > div > ul > li > ul > li:nth-child(1) > div > a > span.category-name

    suspend fun parse(url: String, selector: String, listener: OnResultListener): List<CategoryModel>? {
        var data: ArrayList<CategoryModel> = ArrayList()

        val parse: Deferred<List<CategoryModel>?> = CoroutineScope(Dispatchers.IO).async {
            val doc = Jsoup.connect(url).get()
            val metaElements: Elements = doc.select(selector)

            Log.e("url","$url")
            Log.e("selector","$selector")
            Log.e("metaElements","${metaElements.size}")

            for(element in metaElements){
                data.add(
                    CategoryModel(element.select("a > span.category-name").text(),
                    element.select("a").attr("href").toString())
                    )
            }
            data
        }


        parse.await()
        listener.getResult(data)

        return data
    }
}
