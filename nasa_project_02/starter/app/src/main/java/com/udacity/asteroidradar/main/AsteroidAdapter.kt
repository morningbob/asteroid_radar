package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding


class AsteroidAdapter(val asteroidList: List<Asteroid>) : RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>(){

    //val data = listOf<Asteroid>()

    override fun getItemCount(): Int {
        return asteroidList.size
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroidList[position]
        holder.binding.codeName.text = asteroid.codename
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(AsteroidListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class AsteroidViewHolder(val binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}