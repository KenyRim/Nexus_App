package com.example.nexusapp.parser;

import android.util.Log
import com.example.nexusapp.listener.OnResultListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class CategoryParser {

    suspend fun parse(url: String, selector: String, listener: OnResultListener): List<Pair<String,String>>? {
        var data: ArrayList<Pair<String,String>> = ArrayList()

        val parse: Deferred<List<Pair<String,String>>?> = CoroutineScope(Dispatchers.IO).async {
            val doc = Jsoup.connect(url).get()
            val metaElements: Elements = doc.select(selector)

            Log.e("url","$url")
            Log.e("selector","$selector")
            Log.e("metaElements","${metaElements.size}")

            for(element in metaElements){
                data.add(element.select("a > span.category-name").text() to
                    element.select("a").attr("href").toString()
                    )
            }
            data
        }


        parse.await()
        listener.getResult(data)

        return data
    }
}
