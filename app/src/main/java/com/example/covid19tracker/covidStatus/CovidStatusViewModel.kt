package com.example.covid19tracker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

interface MyCallback{
    fun assignData(map:MutableMap<String,AllCovidDataItem>)
}
interface GetCountryInfo{
    fun assignCountryInfo(map: MutableMap<String, CountryInfoItem>)
}

class CovidStatusViewModel: ViewModel() {
    private val _covidData = mutableMapOf<String, AllCovidDataItem>()
    val covidData: MutableMap<String, AllCovidDataItem>
        get() = _covidData

    private val _countryInfo = mutableMapOf<String, CountryInfoItem>()
    val countryInfo: MutableMap<String, CountryInfoItem>
        get() = _countryInfo

    private val _removeProgressBar=MutableLiveData<Boolean>()
    val removeProgressBar:LiveData<Boolean>
    get() = _removeProgressBar

    var countryName: String = String()

    init {
        _removeProgressBar.value=false
        funGetData(object : MyCallback {
            override fun assignData(map: MutableMap<String, AllCovidDataItem>) {
                _covidData.putAll(map)
            }
        })
        getInfo(object : GetCountryInfo {
            override fun assignCountryInfo(map: MutableMap<String, CountryInfoItem>) {
                _countryInfo.putAll(map)
            }
        })
        if(_covidData.isNotEmpty() && _countryInfo.isNotEmpty())
            _removeProgressBar.value=true
    }

    private fun funGetData(myCallback: MyCallback) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://covid19-status-api.herokuapp.com/countries")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val gson = GsonBuilder().create()
                val body = response.body()?.string()
                var data: AllCovidData = gson.fromJson(body, AllCovidData::class.java)
                val map = mutableMapOf<String, AllCovidDataItem>()
                for (i in 0 until data.size) {
                    val country: String = data[i].country.toLowerCase()
                    map[country] = data[i]
                }
                myCallback.assignData(map)
            }
        })

    }

    private fun getInfo(getCountryInfo: GetCountryInfo) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://gist.githubusercontent.com/Goles/3196253/raw/9ca4e7e62ea5ad935bb3580dc0a07d9df033b451/CountryCodes.json")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val gson = GsonBuilder().create()
                val body = response.body()?.string()
                var data: CountryInfo = gson.fromJson(body, CountryInfo::class.java)
                val map = mutableMapOf<String, CountryInfoItem>()
                for (i in 0 until data.size) {
                    val country: String = data[i].name.toLowerCase()
                    map[country] = data[i]
                }
                getCountryInfo.assignCountryInfo(map)
            }
        })
    }

}
