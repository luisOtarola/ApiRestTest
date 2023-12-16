package com.example.apiresttest.ui.theme

import com.example.apiresttest.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.apiresttest.Background.ApiTask
import com.example.apiresttest.Background.ApiCallback
import org.json.JSONArray
import org.json.JSONException

class APIRestActivity : AppCompatActivity(),ApiCallback {
    private lateinit var listDataFromJson : ListView
    private lateinit var getRequestButton : Button
    private lateinit var adapter : ArrayAdapter<String>
    private lateinit var listData : MutableList<String>
    private  var URL : String = "https://apis.digital.gob.cl/fl/feriados/2013/09"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apirest)

        listDataFromJson = findViewById(R.id.listViewDataFromJson)
        getRequestButton = findViewById(R.id.getRequest)

        listData = mutableListOf(
            getString(R.string.api_instruction)
        )

        adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, // Built-in layout for simple items
            listData
        )

        listDataFromJson.adapter = adapter

        // llama al apiTask y ejecuta la url
        getRequestButton.setOnClickListener{
            val apiRequestTask = ApiTask(this)
            apiRequestTask.execute(URL)
        }
    }

    // se llama cuando la solicitud de la api se completa
    override fun onRequestComplete(result: String) {
        //Update GUI
        listData = processingData(result)
        Log.i("APIRestActivity",listData.toString())
        adapter.clear()
        adapter.addAll(listData)
        adapter.notifyDataSetChanged()

    }
    fun processingData(result: String): MutableList<String> {
        val list: MutableList<String> = mutableListOf()
        try {
            val jsonArray = JSONArray(result)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val nombre = jsonObject.getString("nombre")
                val comentarios = jsonObject.getString("comentarios")
                val fecha = jsonObject.getString("fecha")
                val irrenunciable = jsonObject.getString("irrenunciable")
                val tipo = jsonObject.getString("tipo")

                //list.add("$nombre - $comentarios - $fecha - $irrenunciable - $tipo")
                list.add("$fecha")

                // Access the "leyes" array
                val leyesArray = jsonObject.getJSONArray("leyes")
                for (j in 0 until leyesArray.length()) {
                    val leyObject = leyesArray.getJSONObject(j)
                    val leyNombre = leyObject.getString("nombre")
                    val leyUrl = leyObject.getString("url")

                    //list.add("    Ley: $leyNombre - URL: $leyUrl")
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            list.add(getString(R.string.api_error))
        }
        return list.toMutableList()
    }
}
