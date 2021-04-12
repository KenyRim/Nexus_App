package com.example.nexusapp.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.transition.Fade
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.bumptech.glide.request.transition.Transition
import com.example.nexusapp.App
import com.example.nexusapp.R
import com.example.nexusapp.adapters.GalleryAdapter
import com.example.nexusapp.constants.EMPTY_STRING
import com.example.nexusapp.constants.TITLE
import com.example.nexusapp.constants.URL
import com.example.nexusapp.listener.OnResultListeners
import com.example.nexusapp.models.ArticleModel
import com.example.nexusapp.parser.Parser
import com.example.nexusapp.utils.SliderTransformer
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.titlebar.*
import kotlinx.android.synthetic.main.titlebar.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList


class FragmentArticle : Fragment(), OnResultListeners.FullArticle {

    private val imagesList:ArrayList<String> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }


    fun newInstance(categoryUrl: String, viewTransitionName: String, title: String): FragmentArticle {
        val args = Bundle()
        args.putString(URL, categoryUrl)
        args.putString(TITLE, title)
    //    args.putString(VIEW_TRANSITION_NAME, viewTransitionName)
        val fragment = FragmentArticle()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val fade = Fade()
            fade.excludeTarget(android.R.id.statusBarBackground, true)
            fade.excludeTarget(android.R.id.navigationBarBackground, true)
            sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.simple_fragment_transition)

            sharedElementReturnTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.simple_fragment_transition)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_article, container, false)

        val name = arguments?.getString(TITLE)
        val titleBar = rootView.top_bar
        titleBar.title_tv.text = name?.capitalize(Locale.getDefault())
        titleBar.top_bar.transitionName = name
        titleBar.back_btn_iv.setOnClickListener {
            activity?.onBackPressed()
        }

       loadContent()
        return rootView
    }

    private fun iniPager(images: List<String>){
        val pager: ViewPager2 = vp_article_images
        val adapter = GalleryAdapter(images)
        pager.offscreenPageLimit= 2
        pager.setPageTransformer(SliderTransformer(2))
        pager.adapter = adapter

        TabLayoutMediator(page_indicator, pager)
        { it, position ->
            it.customView = createTabItemView(images[position],position)

        }.attach()
    }

    private fun createTabItemView(imgUri: String,position:Int): View {
        val textView = TextView(App.applicationContext())
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = params
        textView.text = position.toString()
//        Glide.with(App.applicationContext()).load(imgUri).into(imageView)

//
//
//        Glide.with(App.applicationContext())
//            .asBitmap()
//            .load(
//                if (imgUri
//                        .contains("svg") || imgUri == EMPTY_STRING
//                ) "https://www.nexusmods.com/assets/images/default/tile_empty.png"  else imgUri
//            )
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .into(object : CustomTarget<Bitmap?>() {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap?>?
//                ) {
//
//                    imageView.setImageBitmap(resource)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {}
//            })

        return textView
    }


    private fun loadContent() {
        view?.progressbar?.visibility = View.VISIBLE
        GlobalScope.launch(IO) {
            Parser().parseArticles(
                arguments?.getString(URL).toString(),
                this@FragmentArticle
            )
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun getResult(article: ArticleModel) {
        imagesList.clear()
        imagesList.addAll(article.images)
        GlobalScope.launch(Dispatchers.Main) {
            if (progressbar != null){
                progressbar.visibility =  View.GONE
            }
            iniPager(imagesList)




            val webView: WebView = web_view
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = SSLTolerentWebViewClient()
            webView.settings.domStorageEnabled = true
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

            webView.settings.loadWithOverviewMode = true
            webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webView.settings.useWideViewPort = true
            webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

            webView.loadData(htmlOptimize(article.article), "text/html; charset=utf-8", "UTF-8")

            article_title.text = arguments?.getString(TITLE)
            article_about.text = Html.fromHtml(article.textAbout)

        }

        for(image in article.images) {
            Log.e("result", image)
        }
    }

    private fun htmlOptimize(htmlString: String):String{
        val doc = Jsoup.parse(htmlString)
        doc.select("img").attr("width", "100%") // find all images and set with to 100%
        doc.select("figure").attr("style", "width: 80%") // find all figures and set with to 80%
        doc.select("iframe").attr("style", "width: 100%") // find all iframes and set with to 100%
        return doc.html()
    }

    private class SSLTolerentWebViewClient : WebViewClient() {
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()
        }
    }


}



