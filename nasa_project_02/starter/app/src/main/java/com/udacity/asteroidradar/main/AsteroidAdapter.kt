package com.udacity.asteroidradar.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding


class AsteroidAdapter(val asteroidList: List<Asteroid>, val clickListener: AsteroidListener) :
  RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>(){

    override fun getItemCount(): Int {
        return asteroidList.size
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroidList[position]
        holder.binding.codeName.text = asteroid.codename
        holder.bind(asteroid, clickListener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(AsteroidListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
        //Log.i("Adapter ", "I ran")
    }

    class AsteroidViewHolder(val binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid, clickListener: AsteroidListener) {
            binding.asteroid = item
            binding.clicklistener = clickListener
            binding.executePendingBindings()
        }
    }

    class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}