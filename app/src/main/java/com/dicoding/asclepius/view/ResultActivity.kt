package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get image from uri
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri.let {
            binding.resultImage.setImageURI(it)
            analyzeImage(it)
        }
    }

    private fun analyzeImage(uri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    results?.let { it ->
                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                            println(it)
                            val categories = it[0].categories[0]
                            val label = categories.label
                            val score = categories.score
                            val time = inferenceTime.toString()

                            val displayResult = "Hasil : $label" + "Score: " + NumberFormat.getPercentInstance().format(score).toString() + "$time ms"

                            binding.resultText.text = displayResult
                            Log.d("TES", displayResult)
                        } else {
                            binding.resultText.text = "GAGAL"
                        }

                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(uri)
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}