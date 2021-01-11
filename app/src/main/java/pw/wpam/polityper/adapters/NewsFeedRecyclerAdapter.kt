package pw.wpam.polityper.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recycler_view_news.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Article
import kotlin.collections.ArrayList

class NewsFeedRecyclerAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsFeedViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is NewsFeedViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(articleList: List<Article>){
        val data = ArrayList<Article>()
        for (article in articleList) {
            data.add(article)
            Log.d("INFO", article.title)
        }
        items = data
        this.notifyDataSetChanged()
    }

    fun submitList(articleList: List<Article>){
        items = articleList
    }

    inner class NewsFeedViewHolder
    constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val newsIcon = itemView.news_icon
        val newsTitle = itemView.news_title
        val newsDescription = itemView.news_description

        init {
            itemView.setOnClickListener {view ->
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(items[position].url)
                mContext.startActivity(openURL)
            }
        }

        fun bind(article: Article){
            val requestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(article.urlToImage)
                    .into(newsIcon)

            newsTitle.text = article.title.take(50)
            newsDescription.text = article.description.take(200)
        }
    }
}
