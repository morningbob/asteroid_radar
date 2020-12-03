package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        val database = activity?.let { AsteroidDatabase.getInstance(it) }
        val mainViewModelFactory = database?.let { MainViewModelFactory(it) }
        ViewModelProvider(this, mainViewModelFactory)
          .get(MainViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val database = AsteroidDatabase.getInstance(requireContext())
        val mainViewModelFactory = MainViewModelFactory(database)
        val viewModel = ViewModelProvider(this, mainViewModelFactory)
            .get(MainViewModel::class.java)

        binding.viewModel = viewModel

        // observe the asteroid list

        var adapter: AsteroidAdapter? = AsteroidAdapter(emptyList(), AsteroidAdapter.AsteroidListener{
            viewModel.displayAsteroidDetails(it)
        })

        binding.asteroidRecycler.adapter = adapter

        val asteroidListObserver = Observer<List<Asteroid?>?> { newList ->
            if (newList != null) {
                //Log.i("Observer ", "ran")
                binding.asteroidRecycler.adapter = AsteroidAdapter(
                  newList as List<Asteroid>, AsteroidAdapter.AsteroidListener{
                    viewModel.displayAsteroidDetails(it)
                })

            }
        }

        val pictureObserver = Observer<PictureOfDay?> { newPicture ->
            if (newPicture != null) {
                Picasso.with(context).load(newPicture.url).into(binding.activityMainImageOfTheDay);
            }
        }

        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if(it != null){
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidComplete()
            }
        })

        viewModel.asteroidList.observe(viewLifecycleOwner, asteroidListObserver)
        viewModel.picture.observe(viewLifecycleOwner, pictureObserver)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week -> viewModel.onWeekClicked()
            R.id.show_today -> viewModel.onTodayClicked()
            R.id.show_saved -> viewModel.onSavedClicked()
        }
        return true
    }

}
