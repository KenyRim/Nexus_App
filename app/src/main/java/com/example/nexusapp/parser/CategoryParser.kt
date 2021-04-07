package com.example.nexusapp.parser;

import android.util.Log
import com.example.nexusapp.fragments.FragmentCategory
import com.example.nexusapp.models.CategoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class CategoryParser {

    suspend fun parse(
        url: String?,
        listener: FragmentCategory
    ): List<CategoryModel> {
        val data: ArrayList<CategoryModel> = ArrayList()

        val parse: Deferred<List<CategoryModel>> = CoroutineScope(Dispatchers.IO).async {
            val doc = Jsoup.connect(url).get()
            val metaElements: Elements = doc.select("#mod-list > ul > li")
            val pages: Elements =
                doc.select("#mod-list > div.pagenav.clearfix.head-nav > div > ul > li")

            val partOfPath = "div.mod-tile-left > div.tile-desc.motm-tile > div.tile-content"
            for (element in metaElements) {
                data.add(
                    CategoryModel(
                        element.select("div.mod-tile-left > a > figure > div > img").attr("src"),
                        element.select("p.tile-name > a").text(),
                        element.select("$partOfPath > p.desc").text(),
                        element.select("$partOfPath > p.tile-name > a").attr("href"),
                        if (pages.size>1) pages[pages.size-2].text().toInt() else 1
                    )
                )
            }
            data
        }


        parse.await()
        listener.getResult(data)

        return data
    }
}


