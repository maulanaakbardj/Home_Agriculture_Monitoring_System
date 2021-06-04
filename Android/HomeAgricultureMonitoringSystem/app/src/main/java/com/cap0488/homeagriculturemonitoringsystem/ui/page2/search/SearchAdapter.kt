package com.cap0488.homeagriculturemonitoringsystem.ui.page2.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cap0488.homeagriculturemonitoringsystem.databinding.RecycleviewBigBinding
import com.cap0488.homeagriculturemonitoringsystem.model.PlantTypeModel
import com.cap0488.homeagriculturemonitoringsystem.ui.page2.image.ImageActivity


class SearchAdapter(): RecyclerView.Adapter<SearchAdapter.SearchiewHolder>() {

    private var mlist = ArrayList<PlantTypeModel>()
    fun setSearchPlant(list: List<PlantTypeModel>?) {
        if (list == null) return
        this.mlist.clear()
        this.mlist.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchiewHolder {
        val binding = RecycleviewBigBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchiewHolder(binding)        }

    override fun onBindViewHolder(holder: SearchiewHolder, position: Int) {
        val searchPlant = mlist[position]
        holder.bind(searchPlant)    }

    override fun getItemCount(): Int = mlist.size

    inner class SearchiewHolder(private val binding: RecycleviewBigBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(plantType: PlantTypeModel){
            binding.apply {
                tvArticle.text = plantType.name

                Glide.with(itemView.context)
                        .load(plantType.img)
                        .into(binding.ivArticle)

                itemView.setOnClickListener {
                    val i = Intent(itemView.context, ImageActivity::class.java)
                    i.putExtra(ImageActivity.EXTRAJENIS,plantType.name)
                    itemView.context.startActivity(i)

                }

            }


        }
    }
}