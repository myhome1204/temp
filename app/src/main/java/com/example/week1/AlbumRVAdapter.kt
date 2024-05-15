
package com.example.week1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.databinding.ItemAlbumBinding


class AlbumRVAdapter(private val albumArrayList : ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>(){
    interface MyItemClickListener{
        fun onItemClick(album : Album)
        fun onRemoveAlbum(position: Int)
    }
    private lateinit var mItemClickListener :MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
    fun addItem(album:Album){
        albumArrayList.add(album)
        notifyDataSetChanged()
    }
    fun removeItem(position: Int){
        albumArrayList.removeAt(position)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding : ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumArrayList[position])
        //클릭이벤트처리는 포지션을가지고있는 이곳에서함  하지만 이작동은 리사이클리 뷰 내에서만 가능한것 .
        //다른 어뎁터의 외부에서 처리하고싶으면 추가로 클릭리스너를 처리해주는 인터페이스를 만들어야함
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumArrayList[position]) }
    }

    override fun getItemCount(): Int {
        return albumArrayList.size
    }

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(album : Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}