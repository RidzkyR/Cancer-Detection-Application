package com.dicoding.asclepius.view.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yalantis.ucrop.UCrop
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var currentImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.news_nav -> {
                    startActivity(Intent(this, NewsActivity::class.java))
                    true
                }

                R.id.home_nav -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.history_nav -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    true
                }

                else -> false
            }
        }

            binding.galleryButton.setOnClickListener { startGallery() }
            binding.historyButton.setOnClickListener { moveToHistory() }
            binding.analyzeButton.setOnClickListener {
                currentImageUri?.let {
                    analyzeImage(it)
                } ?: run {
                    showToast(getString(R.string.empty_image_warning))
                }
            }
        }

        private fun startGallery() {
            // TODO: Mendapatkan gambar dari Gallery.
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        private val launcherGallery = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->
            if (uri != null) {
                setupUcrop(uri)
            } else {
                Log.d("Photo Picker", getString(R.string.no_media_selected))
            }
        }

        private fun setupUcrop(sourceUri: Uri) {
            val image = "cropped_image_${System.currentTimeMillis()}.jpg"
            val destinationUri = Uri.fromFile(File(cacheDir, image))
            UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(1f, 1f)
                .withMaxResultSize(1000, 1000)
                .start(this)
        }

        @Deprecated("Deprecated in Java")
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
                val uri = UCrop.getOutput(data!!)

                uri?.let {
                    currentImageUri = uri
                    showImage(uri)
                } ?: showToast("Failed to crop image")

            } else if (resultCode == UCrop.RESULT_ERROR) {
                val cropError = UCrop.getError(data!!)
                showToast("Crop error: ${cropError?.message}")
            }
        }

        private fun showImage(uri: Uri) {
            // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
            currentImageUri?.let {
                Log.d("Image URI", "showImage: $it")
                binding.previewImageView.setImageURI(it)
            }
        }

        private fun analyzeImage(uri: Uri) {
            // TODO: Menganalisa gambar yang berhasil ditampilkan.
            moveToResult(uri)
        }

        private fun moveToResult(uri: Uri) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, uri.toString())
            startActivity(intent)
        }

        private fun moveToHistory() {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }