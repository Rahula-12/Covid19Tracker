package com.example.covid19tracker.wholedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.covid19tracker.CovidStatusViewModel
import com.example.covid19tracker.R
import com.example.covid19tracker.databinding.CovidWholeDataFragmentBinding
import android.view.ViewGroup as ViewGroup1
class CovidWholeData : Fragment() {
    lateinit var binding:CovidWholeDataFragmentBinding
    private val viewModel: CovidStatusViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup1?,
        savedInstanceState: Bundle?
    ): View? {
         binding=DataBindingUtil.inflate(inflater, R.layout.covid_whole_data_fragment,container,false)
        binding.lifecycleOwner=this
            binding.countryName.text=viewModel.countryName.capitalize()
        YoYo.with(Techniques.RollIn).duration(700).playOn(binding.activeCases)
        YoYo.with(Techniques.RollIn).duration(800).playOn(binding.totalCases)
        YoYo.with(Techniques.RollIn).duration(900).playOn(binding.totalDeaths)
        YoYo.with(Techniques.RollIn).duration(1000).playOn(binding.totalRecovered)
        binding.activeCases.text="Active Cases:- \n${viewModel.covidData[viewModel.countryName]?.activeCases.toString()}"
        binding.totalCases.text="Total Cases:- \n${viewModel.covidData[viewModel.countryName]?.totalCases.toString()}"
        binding.totalDeaths.text="Total Deaths:- \n${viewModel.covidData[viewModel.countryName]?.totalDeaths.toString()}"
        binding.totalRecovered.text="Total Recovered:- \n${viewModel.covidData[viewModel.countryName]?.totalRecovered.toString()}"
        loadFlag()
        return binding.root
    }
    private fun loadFlag(){
        context?.let { Glide.with(it).load("https://flagcdn.com/h240/${viewModel.countryInfo[viewModel.countryName]?.code?.toLowerCase()}.png").into(binding.flag) }
    }
}