package com.example.mehdi.barcodedetection

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This sets up the event handler (onClick) for when the user presses the button.
        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener { }
        //getting a reference to our image view
        val  myImageView = findViewById<ImageView>(R.id.imgview)
        //use a BitMapFactory to decode the R.drawable.puppy resources into a Bitmap
        val myBitMap = BitmapFactory
                .decodeResource(applicationContext.resources, R.drawable.puppy)
        //and set it to be the bitmap for myImageView.
        myImageView.setImageBitmap(myBitMap)

        //create new BarcodeDetector using a builder, and tell it to look for QR codes and Data Matrices,...
        val detector = BarcodeDetector
                .Builder(applicationContext)
                .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
                .build()
        if (!detector.isOperational) {
            val txtView = findViewById<TextView>(R.id.txtContent)
            txtView.text = "Could not set up the detector!"
            return
        }
        //creates a frame from the bitmap, and passes it to the detector.
        val frame = Frame.Builder().setBitmap(myBitMap).build()
        val barcodes = detector.detect(frame)

        // Because for this sample, we have 1 and only 1 bar code, we take the Barcode called ‘thisCode' to be
        // the first element in the array and then assign it's rawValue to the textView.
        val thisCode = barcodes.valueAt(0)
        val txtView = findViewById<TextView>(R.id.txtContent)
        txtView.text = thisCode.rawValue
    }
}
