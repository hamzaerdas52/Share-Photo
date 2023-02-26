package com.hamzaerdas.sharephoto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.sharephoto.databinding.PostRecyclerRowBinding
import com.hamzaerdas.sharephoto.model.Post
import com.squareup.picasso.Picasso


class PostAdapter(val postList: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.PostHolder>() {

    class PostHolder(val binding: PostRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = PostRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.comment.text = postList[position].comment
        holder.binding.userEmail.text = postList[position].email
        Picasso.get().load(postList[position].url).into(holder.binding.imageView);
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}