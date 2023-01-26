package androidkotlin.formation.weather.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidkotlin.formation.weather.R

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, WeatherFragment.newInstance())
            .commit()
    }
}