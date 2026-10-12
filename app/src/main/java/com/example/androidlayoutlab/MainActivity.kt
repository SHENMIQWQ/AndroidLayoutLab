package com.example.androidlayoutlab

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_linear_layout).setOnClickListener {
            startActivity(Intent(this, LinearLayoutActivity::class.java))
        }
        findViewById<Button>(R.id.button_table_layout).setOnClickListener {
            startActivity(Intent(this, TableLayoutActivity::class.java))
        }
        findViewById<Button>(R.id.button_constraint_one).setOnClickListener {
            startActivity(Intent(this, ConstraintOneActivity::class.java))
        }
        findViewById<Button>(R.id.button_constraint_two).setOnClickListener {
            startActivity(Intent(this, ConstraintTwoActivity::class.java))
        }
        findViewById<Button>(R.id.button_compose).setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }
}
