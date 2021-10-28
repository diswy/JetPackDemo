package com.xiaofu.jetpackdemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiaofu.jetpackdemo.databinding.ItemDemoArticleBinding
import com.xiaofu.jetpackdemo.vo.WXArticle

class MyAdapter(private val context: Context, mData: ArrayList<WXArticle>? = null) :
    RecyclerView.Adapter<MyAdapter.ArticleHolder>() {
    val data = ArrayList<WXArticle>()
    private val inflater: LayoutInflater

    init {
        mData?.let { data.addAll(mData) }
        inflater = LayoutInflater.from(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: ArrayList<WXArticle>?) {
        if (dataList == null) {
            data.clear()
            notifyDataSetChanged()
        } else {
            data.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(ItemDemoArticleBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val item = data[position]
        holder.bind.tvAuthor.text = item.name
    }

    override fun getItemCount(): Int = data.size

    class ArticleHolder(var bind: ItemDemoArticleBinding) : RecyclerView.ViewHolder(bind.root)

}