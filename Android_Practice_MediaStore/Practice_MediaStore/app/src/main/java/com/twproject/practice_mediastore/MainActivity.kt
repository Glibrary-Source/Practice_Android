package com.twproject.practice_mediastore

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawable = getDrawable(R.drawable.apple)
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap

        findViewById<Button>(R.id.btn_media_store).setOnClickListener {
            saveImageInAlbum(this, "testfile", bitmap)
        }
    }

    private fun saveImageInAlbum(context: Context, fileName: String, bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            with(values) {
                put(MediaStore.Images.Media.TITLE, fileName)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/my_folder")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val uri = context.contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            val fos = context.contentResolver.openOutputStream(uri!!)
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            }
            fos?.run {
                flush()
                close()
            }

        } else {
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() +
                    File.separator +
                    "my_folder"
            val file = File(dir)
            if (!file.exists()) {
                file.mkdirs()
            }

            val imgFile = File(file, "test_capture.jpg")
            val os = FileOutputStream(imgFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()

            val values = ContentValues()
            with(values) {
                put(MediaStore.Images.Media.TITLE, fileName)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.BUCKET_ID, fileName)
                put(MediaStore.Images.Media.DATA, imgFile.absolutePath)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }
    }

}