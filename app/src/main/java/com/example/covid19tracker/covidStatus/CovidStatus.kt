package com.example.covid19tracker.covidStatus

import android.app.AlertDialog
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.covid19tracker.CovidStatusViewModel
import com.example.covid19tracker.R
import com.example.covid19tracker.databinding.CovidStatusFragmentBinding
import kotlin.system.exitProcess

class CovidStatus : Fragment() {
    private val viewModel: CovidStatusViewModel by activityViewModels()
    lateinit var binding:CovidStatusFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding=
            DataBindingUtil.inflate(inflater, R.layout.covid_status_fragment,container,false)
        binding.viewModel= viewModel
        binding.lifecycleOwner=this
        binding.floatingActionButton.setOnClickListener{
            deleteCountry()
        }
        binding.goButton.setOnClickListener {
            val connectivityManager=
                context?.let { ContextCompat.getSystemService(it, ConnectivityManager::class.java) }
            val info= connectivityManager?.activeNetworkInfo
            if (info != null) {
                if (!info.isConnected) {
                    val builder = AlertDialog.Builder(context)
                    //set title for alert dialog
                    builder.setTitle("Internet Problem")
                    //set message for alert dialog
                    builder.setMessage("Please check your internet connection")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)

                    //performing positive action
                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                        exitProcess(0)
                    }
                    //performing cancel action
                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
                else {
                    if(binding.name.text.toString() == "")
                    {
                        val builder = AlertDialog.Builder(context)
                        //set title for alert dialog
                        builder.setTitle("No Country Entered")
                        //set message for alert dialog
                        builder.setMessage("Please enter country name to proceed")
                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                        }
                        //performing cancel action
                        // Create the AlertDialog
                        val alertDialog: AlertDialog = builder.create()
                        // Set other dialog properties
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
                    else {
                        viewModel.countryName = binding.name.text.toString().trim().lowercase()
                        val country = viewModel.countryName
                        if (viewModel.covidData.isEmpty()) {
                            val builder = AlertDialog.Builder(context)
                            //set title for alert dialog
                            builder.setTitle("Data Not available")
                            //set message for alert dialog
                            builder.setMessage("Please wait while data is collected.")
                            builder.setIcon(R.drawable.ic_spinner_1s_200px)

                            //performing positive action
                            builder.setPositiveButton("Ok") { dialogInterface, which ->

                            }
                            //performing cancel action
                            // Create the AlertDialog
                            val alertDialog: AlertDialog = builder.create()
                            // Set other dialog properties
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        } else {
                            if (viewModel.covidData.containsKey(country)) {
                                findNavController().navigate(R.id.action_covidStatus_to_covidWholeData)
                            } else {
                                val builder = AlertDialog.Builder(context)
                                //set title for alert dialog
                                builder.setTitle("Country not found")
                                //set message for alert dialog
                                builder.setMessage("Please entry correct country")
                                builder.setIcon(android.R.drawable.ic_dialog_alert)

                                //performing positive action
                                builder.setPositiveButton("Ok") { dialogInterface, which ->

                                }
                                //performing cancel action
                                // Create the AlertDialog
                                val alertDialog: AlertDialog = builder.create()
                                // Set other dialog properties
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        }
                    }
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
        }
        return binding.root
    }

    private fun deleteCountry() {
        binding.name.setText("")
    }
}