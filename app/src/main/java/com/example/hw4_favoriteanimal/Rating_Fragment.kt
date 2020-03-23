package com.example.hw4_favoriteanimal

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentanimallist.MainViewModel
import kotlinx.android.synthetic.main.fragment_rating_.*
import kotlinx.android.synthetic.main.fragment_rating_.view.*

/**
 * A simple [Fragment] subclass.
 */
class Rating_Fragment : Fragment() {

    private val animalList = listOf("Dog", "Cat", "Bear", "Rabbit")
    private val TAG = "RatingFragment"
    lateinit var viewModel: MainViewModel
    var rateText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_rating_, container, false)

        //ViewModelProvider returns an existing ViewModel if one exists,
        // or it creates a new one if it does not already exist.
        // Get an instance of our ViewModel by passing context and MainViewModel class,
        // then put the value (position) in liveData
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        var position = 0 // default value to select the first index if no position

        // this observer will be updated when the value of position changes
        viewModel.position.observe(requireActivity(), Observer {
            Log.d(TAG, "position: $it")
            position = it
        })

        // Set the text to textviews after getting the selected position
        view.animalLabel.text = animalList[position]
        // Based on the index of position selected, set the corresponding image
        val imageId = when(position){
            0 -> R.drawable.dog
            1 -> R.drawable.cat
            2 -> R.drawable.bear
            else -> R.drawable.rabbit
        }
        view.animal.setImageResource(imageId)

        // Create a SharedPreferences instance for edit
        val sharedPreferences = activity!!.getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        view.Save.setOnClickListener{
            viewModel.rate.value = ratingBar.rating.toString()
            rateText = ratingBar.rating.toString()

            // save and apply changes
            editor.putString(animalList[position], rateText)
            editor.apply()
        }

        view.SaveAndBack.setOnClickListener{
            viewModel.rate.value = ratingBar.rating.toString()
            rateText = ratingBar.rating.toString()

            // save and apply changes
            editor.putString(animalList[position], rateText)
            editor.apply()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, Main_Fragment())
                .addToBackStack(null)
                .commit()
        }
        return view
    }

}
