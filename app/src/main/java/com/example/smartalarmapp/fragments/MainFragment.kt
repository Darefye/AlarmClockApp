package com.example.smartalarmapp.fragments


import android.R
import android.location.Location
import android.location.LocationManager
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.smartalarmapp.MainViewModel
import com.example.smartalarmapp.TimeZonesList
import com.example.smartalarmapp.databinding.FragmentMainBinding
import org.shredzone.commons.suncalc.SunTimes
import java.time.ZonedDateTime
import java.util.*


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var ids: MutableList<String?>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.bsetAlarm.setOnClickListener { setOnClick() }
        initTimePickers()
        getSun()
        setTextAlarm()
        initSpinner()
        binding.numPickMin.setOnValueChangedListener { _, _, _ ->
            setTextAlarm()
        }
        binding.numPickHour.setOnValueChangedListener { _, _, _ ->
            setTextAlarm()
        }


        binding.btGeo.setOnClickListener {

            val tz = TimeZone.getDefault()
            binding.tvGeo.setText(
                "Time zone ${
                    tz.getDisplayName(
                        false, TimeZone.SHORT
                    )
                }\n Time zone id ${tz.id}"
            )


        }
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

//        val tz = binding.spinner.selectedItem.toString()
//        viewModel.setAlarm(tz,h,m)
    }

    private fun initSpinner() {
        val spinner: Spinner = binding.spinner
        val idArray = TimeZone.getAvailableIDs()
        val idAdapter = ArrayAdapter(
            requireContext(), R.layout.simple_spinner_dropdown_item, idArray
        )
        idAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = idAdapter

        for (i in 0 until idAdapter.count) {
            if (idAdapter.getItem(i).equals(TimeZone.getDefault().id)) {
                spinner.setSelection(i)
            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}