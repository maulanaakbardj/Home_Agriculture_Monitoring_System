package com.cap0488.homeagriculturemonitoringsystem.ui.page1.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.cap0488.homeagriculturemonitoringsystem.R
import com.cap0488.homeagriculturemonitoringsystem.databinding.ActivityArticleBinding
import com.cap0488.homeagriculturemonitoringsystem.model.ArticleModel

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding : ActivityArticleBinding
    private lateinit var articleExtra:ArticleModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.article)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        articleExtra =intent.getParcelableExtra<ArticleModel>(EXTRA) as ArticleModel
        setArticle(articleExtra)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setArticle(article : ArticleModel){

        binding.apply {
            titleArticle.text = article.title
            contentArticle.text = article.content
        }

        Glide.with(this)
            .load(article.img)
            .into(binding.imgArticle)
    }

    companion object{
        const val EXTRA = "extra"
    }
}