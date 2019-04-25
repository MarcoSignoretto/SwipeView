package io.github.marcosignoretto.swipeviewexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.marcosignoretto.swipeview.SwipeView
import io.github.marcosignoretto.swipeview.SwipeViewListener
import it.marcosignoretto.swipeviewexample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val view1 = findViewById<SwipeView>(R.id.view1)
        view1.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Front view clicked", Toast.LENGTH_SHORT).show()
        })
        view1.setOnBackViewClickListener(View.OnClickListener {
            Toast.makeText(this, "Back view clicked", Toast.LENGTH_SHORT).show()
        })
        view1.setOnSwipeViewListener(object : SwipeViewListener {
            override fun onOpen() {
                Toast.makeText(this@MainActivity, "View opened", Toast.LENGTH_SHORT).show()
            }

            override fun onClose() {
                Toast.makeText(this@MainActivity, "View closed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
