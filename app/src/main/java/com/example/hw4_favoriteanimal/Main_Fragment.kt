package com.example.hw4_favoriteanimal

import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentanimallist.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_.view.*

/**
 * A simple [Fragment] subclass.
 */
class Main_Fragment : Fragment() {

    private val TAG = "MainFragment"
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_, container, false)

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        var rate = "" // default value to select the first index if no position

        // this observer will be updated when the value of position changes
        viewModel.rate.observe(requireActivity(), Observer {
            Log.d(TAG, "rate: $it")
            rate = it
        })

        val sharedPreferences = activity!!.getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val dog = sharedPreferences.getString("Dog", rate)
        view.RateDog.text = "Your rating: $dog"

        val cat = sharedPreferences.getString("Cat", rate)
        view.RateCat.text = "Your rating: $cat"

        val bear = sharedPreferences.getString("Bear", rate)
        view.RateBear.text = "Your rating: $bear"

        val rabbit = sharedPreferences.getString("Rabbit", rate)
        view.RateRabbit.text = "Your rating: $rabbit"

        view.dogImage.setOnClickListener {
            viewModel.position.value = 0
            changeFragments()
        }
        view.catImage.setOnClickListener {
            viewModel.position.value = 1
            changeFragments()
        }
        view.bearImage.setOnClickListener {
            viewModel.position.value = 2
            changeFragments()
        }
        view.rabbitImage.setOnClickListener {
            viewModel.position.value = 3
            changeFragments()
        }
        view.clear.setOnClickListener{
            view.RateDog.text = "Your Rating: "
            view.RateCat.text = "Your Rating: "
            view.RateBear.text = "Your Rating: "
            view.RateRabbit.text = "Your Rating: "
            editor.clear()
            editor.apply()
        }
        return view
    }

    private fun changeFragments() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // We are in portrait orientation
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, Rating_Fragment())
                .addToBackStack(null)
                .commit()
        } else {
            // We are in landscape orientation
            // Load Detail fragment, i.e., replace the current detail fragment
            // with detail fragment containing the selected item's details
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.detail_container, Rating_Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

}
