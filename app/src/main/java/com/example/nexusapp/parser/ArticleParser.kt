package com.example.nexusapp.parser;

import android.util.Log
import com.example.nexusapp.fragments.FragmentArticle
import com.example.nexusapp.fragments.FragmentCategories
import com.example.nexusapp.models.ArticleModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ArticleParser {

    suspend fun parse(url: String,listener: FragmentArticle): ArticleModel {
        var data = ArticleModel("", emptyList(),"")

        val parse: Deferred<ArticleModel> = CoroutineScope(Dispatchers.IO).async {

            try {
                val doc = Jsoup.connect(url).get()
                val title: Elements = doc.select("#pagetitle > h1")
                val images: Elements = doc.select("#sidebargallery > ul > li")//   > figure > a > img
                //



                for (element in images){
                    Log.e("assdfsdfsdf", element.attr("data-src").toString())
                }

                data = ArticleModel(title.text(), emptyList(),"")
            }catch (e: HttpStatusException){
            }

            data
        }


        parse.await()
        listener.getResult(data)

        return data
    }
}
