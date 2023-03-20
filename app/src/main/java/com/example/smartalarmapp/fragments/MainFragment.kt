package com.example.smartalarmapp.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.smartalarmapp.MainViewModel
import com.example.smartalarmapp.R
import com.example.smartalarmapp.databinding.FragmentMainBinding
import org.shredzone.commons.suncalc.SunTimes
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*
import java.util.stream.Collectors


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var ids: MutableList<String?>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTimePickers()
//        getSun()
        setTextAlarm()
        initSpinner()
        binding.numPickMin.setOnValueChangedListener { _, _, _ ->
            setTextAlarm()
        }
        binding.numPickHour.setOnValueChangedListener { _, _, _ ->
            setTextAlarm()
        }
        binding.tvGeo.setOnClickListener { setOnClick() }



//        binding.btGeo.setOnClickListener {
//            val tz = TimeZone.getDefault()
//            binding.tvGeo.setText(
//                "Time zone ${
//                    tz.getDisplayName(
//                        false, TimeZone.SHORT
//                    )
//                }\n Time zone id ${tz.id}"
//            )
//
//
//        }
    }

    private fun setTextAlarm() = with(binding) {
        tvAlarm.text = getString(
            com.example.smartalarmapp.R.string.set_alarm_text,
            binding.numPickHour.value.toString(),
            binding.numPickMin.value.toString()
        )


    }


    private fun getSun() {

        val dateTime: ZonedDateTime = ZonedDateTime.now()
        val lat = 55.7522
        val lng = 37.6156
        val times: SunTimes = SunTimes.compute().on(dateTime)   // set a date
            .at(lat, lng)   // set a location
            .execute();     // get the results
        binding.textView3.text = "Sunrise: " + times.getRise()
        binding.textView4.text = "Sunset: " + times.getSet()

    }

    private fun initTimePickers() = with(binding) {
        numPickHour.maxValue = 23
        numPickHour.minValue = 0
        numPickMin.maxValue = 59
        numPickMin.minValue = 0
    }

    private fun setOnClick() {

        val h = binding.numPickHour.value
        val m = binding.numPickMin.value

        val tz = binding.spinner.selectedItem.toString()
        viewModel.setAlarm(tz, h, m, requireContext())
    }


    private fun initSpinner() {
        val spinner: Spinner = binding.spinner
        ids = viewModel.getTimezonesList()

        ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ids
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}