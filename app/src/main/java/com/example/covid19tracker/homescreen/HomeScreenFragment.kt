package com.example.covid19tracker.homescreen

import android.app.AlertDialog
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.covid19tracker.CovidStatusViewModel
import com.example.covid19tracker.R
import com.example.covid19tracker.databinding.FragmentHomeScreenBinding
import kotlin.system.exitProcess


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreen : Fragment() {

    private lateinit var button: Button
    private val viewModel: CovidStatusViewModel by activityViewModels()
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val connectivityManager=
            context?.let { ContextCompat.getSystemService(it, ConnectivityManager::class.java) }
        val info= connectivityManager?.activeNetworkInfo
        if (info != null) {
            if(!info.isConnected) {
                val builder = AlertDialog.Builder(context)
                //set title for alert dialog
                builder.setTitle("Internet Problem")
                //set message for alert dialog
                builder.setMessage("Please check your internet connection")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Ok"){dialogInterface, which ->
                    exitProcess(0)
                }
                //performing cancel action
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
        else
        {
            val builder = AlertDialog.Builder(context)
            //set title for alert dialog
            builder.setTitle("Internet Problem")
            //set message for alert dialog
            builder.setMessage("Please check your internet connection")
            builder.setIcon(R.drawable.ic_noun_no_internet_183410)

            //performing positive action
            builder.setPositiveButton("Ok"){dialogInterface, which ->
                exitProcess(0)
            }
            //performing cancel action
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        val binding: FragmentHomeScreenBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
        binding.worldStats.setOnClickListener{
            findNavController().navigate(R.id.action_homeScreen_to_covidStatus)
        }
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}