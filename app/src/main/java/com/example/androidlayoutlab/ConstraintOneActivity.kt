package com.example.androidlayoutlab

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToLong

class ConstraintOneActivity : BaseActivity() {
    private lateinit var display: TextView

    private var displayValue = "0.0"
    private var storedValue: Double? = null
    private var pendingOperator: Char? = null
    private var replaceOnNextDigit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_one)

        display = findViewById(R.id.calculator_display)
        display.setOnLongClickListener {
            clearCalculator()
            true
        }

        val digitButtons = mapOf(
            R.id.calc_0 to "0",
            R.id.calc_1 to "1",
            R.id.calc_2 to "2",
            R.id.calc_3 to "3",
            R.id.calc_4 to "4",
            R.id.calc_5 to "5",
            R.id.calc_6 to "6",
            R.id.calc_7 to "7",
            R.id.calc_8 to "8",
            R.id.calc_9 to "9"
        )

        digitButtons.forEach { (id, digit) ->
            findViewById<Button>(id).setOnClickListener { appendDigit(digit) }
        }

        findViewById<Button>(R.id.calc_dot).setOnClickListener { appendDecimalPoint() }
        findViewById<Button>(R.id.calc_plus).setOnClickListener { selectOperator('+') }
        findViewById<Button>(R.id.calc_minus).setOnClickListener { selectOperator('-') }
        findViewById<Button>(R.id.calc_multiply).setOnClickListener { selectOperator('*') }
        findViewById<Button>(R.id.calc_divide).setOnClickListener { selectOperator('/') }
        findViewById<Button>(R.id.calc_equal).setOnClickListener { calculateResult() }
    }

    private fun appendDigit(digit: String) {
        displayValue = when {
            replaceOnNextDigit || displayValue == "0.0" || displayValue == "Error" -> digit
            displayValue == "0" -> digit
            else -> displayValue + digit
        }
        replaceOnNextDigit = false
        updateDisplay()
    }

    private fun appendDecimalPoint() {
        displayValue = when {
            replaceOnNextDigit || displayValue == "Error" -> "0."
            !displayValue.contains('.') -> "$displayValue."
            else -> displayValue
        }
        replaceOnNextDigit = false
        updateDisplay()
    }

    private fun selectOperator(operator: Char) {
        val currentValue = displayValue.toDoubleOrNull() ?: return
        if (pendingOperator != null && !replaceOnNextDigit) {
            storedValue = performCalculation(storedValue ?: 0.0, currentValue, pendingOperator!!)
            displayValue = formatNumber(storedValue ?: 0.0)
            updateDisplay()
        } else {
            storedValue = currentValue
        }
        pendingOperator = operator
        replaceOnNextDigit = true
    }

    private fun calculateResult() {
        val currentValue = displayValue.toDoubleOrNull() ?: return
        val operator = pendingOperator ?: return
        val result = performCalculation(storedValue ?: 0.0, currentValue, operator)
        displayValue = formatNumber(result)
        storedValue = null
        pendingOperator = null
        replaceOnNextDigit = true
        updateDisplay()
    }

    private fun performCalculation(left: Double, right: Double, operator: Char): Double {
        return when (operator) {
            '+' -> left + right
            '-' -> left - right
            '*' -> left * right
            '/' -> if (right == 0.0) Double.NaN else left / right
            else -> right
        }
    }

    private fun formatNumber(value: Double): String {
        if (!value.isFinite()) return "Error"
        if (abs(value - value.roundToLong()) < 0.00000001) {
            return value.roundToLong().toString()
        }
        return String.format(Locale.US, "%.8f", value)
            .trimEnd('0')
            .trimEnd('.')
    }

    private fun clearCalculator() {
        displayValue = "0.0"
        storedValue = null
        pendingOperator = null
        replaceOnNextDigit = false
        updateDisplay()
    }

    private fun updateDisplay() {
        display.text = displayValue
    }
}
