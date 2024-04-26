package com.dicoding.asclepius.view.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.database.Analyze
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.DateHelper
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.helper.ViewModelFactory
import com.dicoding.asclepius.model.AnalyzeViewModel
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var analyzeViewModel: AnalyzeViewModel
    private var analize : Analyze? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        analyzeViewModel = obtainViewModel(this@ResultActivity)

        if (analize != null){
            analize = Analyze()
        }

        // get image from uri
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri.let {
            binding.resultImage.setImageURI(it)
            analyzeImage(it)
        }

        // setup save button
        binding.btnSave.setOnClickListener {
            analize.let {
                it?.date = DateHelper.getCurrentDate()
            }
            analyzeViewModel.insert(analize as Analyze)
            showToast(getString(R.string.data_added))
            finish()
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
                    results?.let {
                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                            println(it)
                            val categories = it[0].categories[0]
                            val label = categories.label
                            val score = categories.score
                            val time = inferenceTime.toString()

                            analize = Analyze(
                                imageUri = uri.toString(),
                                category = label,
                                score = score,
                                inferenceTime = time,
                            )

                            val displayResult = """
                                Hasil : $label
                                Score: ${NumberFormat.getPercentInstance().format(score)}
                                Waktu: $time ms
                                """.trimIndent()

                            binding.resultText.text = displayResult
                            Log.d("TES", displayResult)
                        } else {
                            showToast(getString(R.string.error_analyze_failed))
                        }

                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(uri)
    }

    private fun obtainViewModel(activity: AppCompatActivity): AnalyzeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AnalyzeViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}