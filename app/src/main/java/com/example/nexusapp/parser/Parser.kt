package com.example.nexusapp.parser;

import android.util.Log
import com.example.nexusapp.fragments.FragmentArticle
import com.example.nexusapp.fragments.FragmentCategories
import com.example.nexusapp.fragments.FragmentCategory
import com.example.nexusapp.models.ArticleModel
import com.example.nexusapp.models.CategoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.util.Collections.sort

class Parser {

    suspend fun parseArticles(url: String, listener: FragmentArticle): ArticleModel {
        var data = ArticleModel("", emptyList(), "")

        val parse: Deferred<ArticleModel> = CoroutineScope(Dispatchers.IO).async {

            try {
                val doc = Jsoup.connect(url).get()
                val article: String =
                    doc.select("#section > div > div.wrap.flex > div:nth-child(2) > div > div.tabcontent.tabcontent-mod-page > div.container.mod_description_container.condensed")
                        .first().toString()
                val titleAbout: String =
                    doc.select("#section > div > div.wrap.flex > div > div > div.tabcontent.tabcontent-mod-page > div.container.tab-description > p")
                        .first().toString()
                val thumbnailImages: Elements =
                    doc.select("#sidebargallery > ul > li")

                val imagesList: ArrayList<String> = ArrayList()
                for (element in thumbnailImages) {
                    imagesList.add(element.select("img").attr("src").toString())
                }

                data = ArticleModel(titleAbout, imagesList, article)
            } catch (e: HttpStatusException) {
            }
            data
        }


        parse.await()
        listener.getResult(data)

        return data
    }

    suspend fun parseCategories(
        url: String,
        selector: String,
        listener: FragmentCategories
    ): List<Pair<String, String>> {
        val data: ArrayList<Pair<String, String>> = ArrayList()

        val parse: Deferred<List<Pair<String, String>>?> = CoroutineScope(Dispatchers.IO).async {

            try {
                val doc = Jsoup.connect(url).get()
                val metaElements: Elements = doc.select(selector)

                for (element in metaElements) {
                    data.add(
                        element.select("a > span.category-name").text() to
                                element.select("a").attr("href").toString()
                    )
                }
            } catch (e: HttpStatusException) {
            }

            data
        }


        parse.await()
        listener.getResult(data)

        return data
    }

    suspend fun parseCategory(
        url: String?,
        listener: FragmentCategory
    ): List<CategoryModel> {
        val data: ArrayList<CategoryModel> = ArrayList()
        val partOfPath = "div.mod-tile-left > div.tile-desc.motm-tile > div.tile-content"

        val parse: Deferred<List<CategoryModel>> = CoroutineScope(Dispatchers.IO).async {
            val doc = Jsoup.connect(url).get()
            val metaElements: Elements = doc.select("#mod-list > ul > li")
            val pages: Elements =
                doc.select("#mod-list > div.pagenav.clearfix.head-nav > div > ul > li")

            val pagesList: ArrayList<String> = ArrayList()

            for (page in pages) {
                pagesList.add(page.text())
            }

            sort(pagesList)

            var i = 0
            for (element in metaElements) {
                data.add(
                    CategoryModel(
                        i++.toLong(),
                        element.select("div.mod-tile-left > a > figure > div > img").attr("src"),
                        element.select("p.tile-name > a").first().text(),
                        element.select("$partOfPath > p.desc").text(),
                        element.select("$partOfPath > p.tile-name > a").attr("href"),
                        pagesList.last().toInt()
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
