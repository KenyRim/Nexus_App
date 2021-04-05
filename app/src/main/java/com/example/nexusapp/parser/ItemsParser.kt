package com.example.nexusapp.parser;

import com.example.nexusapp.listener.OnResultListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.select.Elements

public class ItemsParser {

//    suspend fun parse(url: String, selector: String, listener: OnResultListener): List<String>? {
//        var data: ArrayList<String> = ArrayList()
//
//        val parse: Deferred<List<String>?> = CoroutineScope(Dispatchers.IO).async {
//            val doc = Jsoup.connect(url).get()
//            val metaElements: Elements = doc.select(selector)
//
//            for(element in metaElements){
//                data.add(element.select("div img").attr("data-lazysrc").toString())
//            }
//            data
//        }
//
//
//        parse.await()
//        listener.getResult(data)
//
//        return data
//    }
}


