package com.example.ageinminutes2

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.util.*
import java.text.*
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        /**
         * Here we have taken an instance of Date Formatter as it will format our
         * selected date in the format which we pass it as an parameter and Locale.
         * Here I have passed the format as dd/MM/yyyy.
         */





        btnDatePicker.setOnClickListener{
            //call clickDatePicker when this button is clicked
            clickDatePicker()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(this,{ _, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "Years was $selectedYear, month was ${selectedMonth + 1}, day of month was $selectedDayOfMonth", Toast.LENGTH_LONG).show()
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
// Selected date it set to the TextView to make it visible to user.
            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
// The formatter will parse the selected date in to Date object
// so we can simply get date in to milliseconds.

            val theDate = sdf.parse(selectedDate)

            tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
            theDate?.let {
                /** Here we have get the time in milliSeconds from Date object
                 * And as we know the formula as milliseconds can be converted to second by dividing it by 1000.
                 * And the seconds can be converted to minutes by dividing it by 60.
                 * So now in the selected date into minutes.
                 */
                val selectedDateInMinutes = theDate.time/60000
                // Here we have parsed the current date with the Date Formatter which is used above.
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                //use the safe call operator with .let to avoid app crashing it currentDate is null
                currentDate?.let {
                    // Current date in to minutes.
                    val currentDateToMinutes = currentDate.time / 60000

                    /**
                     * Now to get the difference into minutes.
                     * We will subtract the selectedDateToMinutes from currentDateToMinutes.
                     * Which will provide the difference in minutes.
                     */
                    val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes
                    tvAgeInMinutes?.text =
                        differenceInMinutes.toString() //differenceInMinutes is in long so convert it to string
                }}}, year, month, day)

        /**
         * Sets the maximal date supported by this in
         * milliseconds since January 1, 1970 00:00:00 in time zone.
         *
         * @param maxDate The maximal supported date.
         */
        // 86400000 is milliseconds of 24 Hours. Which is used to restrict the user from selecting today and future day.
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

            }}







