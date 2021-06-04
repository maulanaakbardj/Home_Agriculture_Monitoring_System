package com.cap0488.homeagriculturemonitoringsystem.ui.page2.identification

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.cap0488.homeagriculturemonitoringsystem.databinding.ActivityIdentificationBinding
import com.cap0488.homeagriculturemonitoringsystem.ui.page2.result.ResultActivity


@Suppress("DEPRECATION")
class IdentificationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIdentificationBinding
    private lateinit var titleJenis:String
    private lateinit var bitmap: Bitmap
    private lateinit var filename:String
    private lateinit var inputString:String
    private lateinit var townList : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foto = intent.getStringExtra("extrafoto")
        binding.foto.setImageURI(foto?.toUri())
        titleJenis = intent.getStringExtra("extrajenis").toString()
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,foto?.toUri())
        title = titleJenis

        Log.d("cektitel",titleJenis)

        //access label
       /* when(titleJenis){
            "Apel" ->{
                filename = "labelapel.txt"
                inputString = application.assets.open(filename).bufferedReader().use { it.readText() }
                townList = inputString.split("\n")
            }
            "Jagung" ->{
                filename = "labeljagung.txt"
                inputString = application.assets.open(filename).bufferedReader().use { it.readText() }
                townList = inputString.split("\n")
            }

            "Tomat"->{
                filename = "labeltomat.txt"
                inputString = application.assets.open(filename).bufferedReader().use { it.readText() }
                townList = inputString.split("\n")
            }
        }
*/




/*
        binding.buttonIdentifikasi.setOnClickListener {


            when(titleJenis){
                "Apel" ->{
                    //val hasil = apelModel()
                    val i = Intent(this, ResultActivity::class.java)
                    i.putExtra(ResultActivity.EXTRA,foto)
                    i.putExtra(ResultActivity.HASIL,titleJenis)
                    startActivity(i)
                    finish()

                }
                "Jagung" ->{
                    //val hasil = jagungModel()
                    val i = Intent(this, ResultActivity::class.java)
                    i.putExtra(ResultActivity.EXTRA,foto)
                    i.putExtra(ResultActivity.HASIL,titleJenis)
                    startActivity(i)
                    finish()
                }

                "Tomat"->{
                    //val hasil = tomatModel()

                    val i = Intent(this, ResultActivity::class.java)
                    i.putExtra(ResultActivity.EXTRA,foto)
                    i.putExtra(ResultActivity.HASIL,titleJenis)
                    startActivity(i)
                    finish()

                }
            }


        }
*/

    }

    /*private fun apelModel():String{
        val model = ModelApel.newInstance(this)
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        val max = getMax(outputFeature0)
        val hasil = townList[max]
        Log.d("hasil",hasil)
        //binding.tvHasil.text = hasil

        model.close()

        return hasil

    }

    private fun jagungModel():String{
        val model = ModelJagung.newInstance(this)
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        val max = getMax(outputFeature0)
        val hasil = townList[max]
        Log.d("hasil",hasil)
        //binding.tvHasil.text = hasil

        model.close()

        return hasil

    }

    private fun tomatModel():String{
        val model = ModelTomat.newInstance(this)
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        val max = getMax(outputFeature0)
        val hasil = townList[max]
        Log.d("hasil",hasil)
        //binding.tvHasil.text = hasil

        model.close()

        return hasil

    }*/


    private fun getMax(arr:FloatArray):Int{


        val max = if(titleJenis == "tomat")
            9
        else
            3

        var ind = 0
        var min = 0.0f
        for (i in 0..max){
            if (arr[i]>min){
                ind = i
                min = arr[i]
            }
        }

        return ind
    }

    companion object{
        const val EXTRAFOTO = "extrafoto"
        const val EXTRAJENIS = "extrajenis"
    }
}