package com.example.androidlayoutlab

import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ConstraintTwoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_two)

        findViewById<Button>(R.id.depart_button).setOnClickListener {
            Toast.makeText(this, "航班准备出发", Toast.LENGTH_SHORT).show()
        }
    }
}
