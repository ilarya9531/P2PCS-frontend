package com.example.p2pcs_app.EditCarCalendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.p2pcs_app.R
import kotlinx.android.synthetic.main.activity_add_avaiability.*
import java.util.*

class ActivityAddAvaiability: AppCompatActivity() {
    var minstart=""
    var minend=""
    var dataarr=""
    var targapassed=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_avaiability)

        targapassed=intent.getStringExtra("TARGA")

        //seleziona data
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        textView.setOnClickListener {
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                    textView.setText("" + mDay + "-" + (mMonth+1) + "-" + mYear)
                    //salvo data
                    dataarr="" + mYear + "-" + "07" + "-" + mDay
                }, year, month, day )

            dpd.show()
        }


        //seleziona ora partenza
        textView3.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay:Int, minute:Int ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                textView3.setText(""+hourOfDay+":"+minute)
                //salvo minuti
                minstart=""+(hourOfDay*60+minute)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }



        //seleziona ora arrivo
        textView4.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay:Int, minute:Int ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                textView4.setText(""+hourOfDay+":"+minute)
                //salvo ora
                minend=""+(hourOfDay*60+minute)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        //listener bottone
        button3.setOnClickListener {
            save_avaiability()
        }
    }

    fun save_avaiability(){
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://ec2-18-206-124-50.compute-1.amazonaws.com/Api/car/avaiability/update.php?TARGA="+targapassed+"&GIORNO=2019-07-16"+"&START="+minstart+"&END="+minend
        val stringReq = StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "Aggiornata con successo!" , Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {
                //throw(DatabaseException("errore"))
            })

        queue.add(stringReq)


    }
}