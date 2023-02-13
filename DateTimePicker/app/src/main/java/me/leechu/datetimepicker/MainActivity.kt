package me.leechu.datetimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import me.leechu.datetimepicker.databinding.ActivityMainBinding
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val currentDate = LocalDate.now()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnDatePicker.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    binding.textDate.text = "$year-$month-$dayOfMonth"
                },
                currentDate.year,
                currentDate.monthValue,
                currentDate.dayOfMonth
            ).show()
        }
        
        binding.btnTimePicker.setOnClickListener {
            TimePickerDialog(
                this@MainActivity,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    binding.textTime.text = "$hourOfDay:$minute"
                },
                0,
                0,
                true
            ).show()
        }
    }
}