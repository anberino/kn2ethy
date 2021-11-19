package com.example.kneethy2.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kneethy2.R
import com.example.kneethy2.TEMPERATURE
import com.example.kneethy2.WeatherResponse
import com.example.kneethy2.databinding.FragmentGalleryBinding
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query








interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("q") city: String?,
        @Query("units") units: String?,
        @Query("appid") app_id: String?
    ): retrofit2.Call<WeatherResponse?>
}

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var temperature = "NaN"
        val service = retrofit.create(WeatherService::class.java)
        val call: retrofit2.Call<WeatherResponse?> = service.getCurrentWeatherData("São Paulo", "metric", "a2636b80c7c4083cd00d031520a39963")
        call.enqueue(object : Callback<WeatherResponse?> {
            override fun onResponse(
                call: retrofit2.Call<WeatherResponse?>,
                response: retrofit2.Response<WeatherResponse?>
            ) {
                if (response.code() === 200) {
                    val weatherResponse: WeatherResponse = response.body()!!
                    temperature = weatherResponse.main.temp.toString()

                    root.findViewById<TextView>(R.id.temperature_display)?.apply {
                        text = temperature + "°C"
                        TEMPERATURE = temperature + "°C"
                    }
                }
            }
            override fun onFailure(call: retrofit2.Call<WeatherResponse?>, t: Throwable) {
                println("DEU RUIM AAAA")
            }
        })

        root.findViewById<TextView>(R.id.text_gallery)?.apply {
            text = "🎵 Your weather will remain 🎵"
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}