package com.example.nexusapp.parser;

import android.util.Log
import com.example.nexusapp.fragments.FragmentCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class CategoriesParser {

    suspend fun parse(url: String, selector: String, listener: FragmentCategories): List<Pair<String,String>> {
        val data: ArrayList<Pair<String,String>> = ArrayList()

        val parse: Deferred<List<Pair<String,String>>?> = CoroutineScope(Dispatchers.IO).async {
            val doc = Jsoup.connect(url).get()

            val metaElements: Elements = doc.select(selector)

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
