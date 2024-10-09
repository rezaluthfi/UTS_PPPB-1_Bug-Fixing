package com.example.pertemuan6

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker.OnDateChangedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pertemuan6.databinding.ActivityMainBinding
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
//            Get Array
            val monthList = resources.getStringArray(R.array.month)

//            Initiate
            var selectedTime ="${timePicker.hour}:${timePicker.minute}"
            val _tempCalendar : Calendar = Calendar.getInstance()
            _tempCalendar.timeInMillis = System.currentTimeMillis()
            var selectedDate = "${_tempCalendar.get(Calendar.DAY_OF_MONTH)} ${monthList[_tempCalendar.get(Calendar.MONTH)]} ${_tempCalendar.get(Calendar.YEAR)}"


//            Date Picker
            datepicker.init(
                _tempCalendar.get(Calendar.YEAR),
                _tempCalendar.get(Calendar.MONTH),
                _tempCalendar.get(Calendar.DAY_OF_MONTH),
                OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
                    selectedDate = "$dayOfMonth ${monthList[monthOfYear]} $year"
                }
            )

//            Time Picker
            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                selectedTime = "$hourOfDay:$minute"
            }


//            Kehadiran Dropdown=======================================
            val kehadiranList = listOf("Hadir tepat waktu", "Terlambat", "Izin")
            val adapterKehadiran = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_spinner_dropdown_item,
                kehadiranList
            )
            kehadiranSpinner.adapter = adapterKehadiran

//            Jika User memilih terlambat atau izin, maka akan muncul edit text, jika tidak, maka tidak muncul
            kehadiranSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position == 0) {
                            binding.keteranganEdittext.visibility = View.GONE
                        } else {
                            binding.keteranganEdittext.visibility = View.VISIBLE
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            //submit button
            submitButton.setOnClickListener {
                Toast.makeText(this@MainActivity,
                    "Presensi berhasil $selectedDate jam $selectedTime",
                    Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }
}