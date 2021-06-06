package com.cap0488.homeagriculturemonitoringsystem.ui.page2.identification

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.cap0488.homeagriculturemonitoringsystem.databinding.ActivityIdentificationBinding
import com.cap0488.homeagriculturemonitoringsystem.ml.ModetomatQuant
import com.cap0488.homeagriculturemonitoringsystem.ui.page2.result.ResultActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


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

        filename = "labelsTomat.txt"
        inputString = application.assets.open(filename).bufferedReader().use { it.readText() }
        townList = inputString.split("\n")


        binding.buttonIdentifikasi.setOnClickListener {

            val hasil = allModel()
            Log.d("cekhasil1",hasil)

            val i = Intent(this, ResultActivity::class.java)
            i.putExtra(ResultActivity.EXTRA,foto)
            i.putExtra(ResultActivity.HASIL,hasil)
            startActivity(i)
            finish()


        }

    }


    private fun allModel():String{

        var resized = Bitmap.createScaledBitmap(bitmap,224, 224, true)
        val model = ModetomatQuant.newInstance(this)

        var tbuffer = TensorImage.fromBitmap(resized)
        var byteBuffer = tbuffer.buffer


        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)


        Log.d("hasilinputFeature0",inputFeature0.toString())
        inputFeature0.loadBuffer(byteBuffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        Log.d("hasilcek",outputFeature0.toString())

        var max = getMax(outputFeature0)
       // binding.resultPred.text = townList[max]

        model.close()

        return townList[max]

    }



    private fun getMax(arr:FloatArray):Int{


        var ind = 0
        var min = 0.0f
        for (i in 0..9){
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