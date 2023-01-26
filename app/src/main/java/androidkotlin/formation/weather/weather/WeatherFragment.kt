package androidkotlin.formation.weather.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidkotlin.formation.weather.App
import androidkotlin.formation.weather.R
import androidkotlin.formation.weather.openweathermap.WeatherWrapper
import androidkotlin.formation.weather.openweathermap.mapOpenWeatherDataToWeather
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment: Fragment() {

    private lateinit var cityName: String
    private val TAG = WeatherFragment::class.java.simpleName

    private lateinit var city: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherDescription: TextView
    private lateinit var temperature: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView
    private lateinit var refreshLayout: SwipeRefreshLayout

    companion object {
        val EXTRA_CITY_NAME = "androidkotlin.formation.weather.extras.EXTRA_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)
        refreshLayout = view.findViewById(R.id.swipe_refresh)

        refreshLayout.setOnRefreshListener { refreshWeather() }

        return view
    }

    private fun refreshWeather() {
        updateWeatherForCity(cityName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity?.intent!!.hasExtra(EXTRA_CITY_NAME)) {
            updateWeatherForCity(requireActivity().intent.getStringExtra(EXTRA_CITY_NAME)!!)
        }
    }

    fun updateWeatherForCity(cityName: String) {
        this.cityName = cityName

        if(!refreshLayout.isRefreshing) {
            refreshLayout.isRefreshing = true
        }

        val call = App.weatherService.getWeather("${cityName}, fr")
        call.enqueue(object : Callback<WeatherWrapper> {
            override fun onResponse(call: Call<WeatherWrapper>, response: Response<WeatherWrapper>) {

                response.body()?.let {
                    val weather = mapOpenWeatherDataToWeather(it)
                    updateUi(weather)
                    Log.i(TAG, "OpenWeatherMap response: $weather")
                    refreshLayout.isRefreshing = false
                }

            }

            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e(TAG, "Could not load city weather", t)
                Toast.makeText(activity,
                    getString(R.string.weather_message_error_could_not_load_weather),
                    Toast.LENGTH_SHORT).show()
                refreshLayout.isRefreshing = false
            }

        })
    }

    private fun updateUi(weather: Weather) {

        Picasso.get()
            .load(weather.iconUrl)
            .placeholder(R.drawable.ic_baseline_cloud_off_24)
            .into(weatherIcon)

        city.text = cityName.uppercase()
        weatherDescription.text = weather.description
        temperature.text = getString(R.string.weather_temperature_value, weather.temperature.toInt())
        humidity.text = getString(R.string.weather_humidity_value, weather.humidity)
        pressure.text = getString(R.string.weather_pressure_value, weather.pressure)


    }

    fun clearUi() {
        weatherIcon.setImageResource(R.drawable.ic_baseline_cloud_off_24)
        cityName = ""
        city.text = ""
        weatherDescription.text = ""
        humidity.text = ""
        pressure.text = ""
    }
}