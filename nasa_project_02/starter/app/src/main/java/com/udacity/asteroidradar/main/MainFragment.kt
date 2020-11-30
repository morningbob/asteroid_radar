package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Network
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class MainFragment : Fragment() {

    //private val viewModel: MainViewModel by lazy {
    //    ViewModelProvider(this).get(MainViewModel::class.java)
    //}



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        var asteroidList: List<Asteroid>? = null

        val mainViewModelFactory = MainViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, mainViewModelFactory)
            .get(MainViewModel::class.java)

        binding.viewModel = viewModel

        // observe the asteroid list
        val asteroidListObserver = Observer<List<Asteroid>> { newList ->
            binding.asteroidRecycler.adapter = AsteroidAdapter(newList)
            (binding.asteroidRecycler.adapter as AsteroidAdapter).notifyDataSetChanged()
            //binding.asteroidRecycler.invalidate()
        }

        viewModel.asteroidList.observe(viewLifecycleOwner, asteroidListObserver)



        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }




}
