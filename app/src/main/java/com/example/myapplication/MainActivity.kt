package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.BinaryOperator
import com.example.myapplication.databinding.ActivityMainBinding

typealias BinaryOperator = (Double, Double) -> Double


class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    val operatorArray = ArrayList<String>()
    val numberArray = ArrayList<Double>()
    private var addition : BinaryOperator = {a, b -> (a + b)}
    private var substraction : BinaryOperator = {a, b -> (a - b)}
    private var multiplication : BinaryOperator = {a, b -> (a * b)}
    private var division : BinaryOperator = {a, b -> (a / b)}
    var changed: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }


    fun input(view: View) {
        with(binding) {

            when ((view as AppCompatButton).text.toString()) {
                "+" -> {
                    result.text = result.text.toString() + view.text
                    operatorArray.add("+")
                }

                "-" -> {
                    result.text = result.text.toString() + view.text
                    operatorArray.add("-")

                }

                "X" -> {
                    result.text = result.text.toString() + view.text
                    operatorArray.add("X")

                }

                "/" -> {
                    result.text = result.text.toString() + view.text
                    operatorArray.add("/")

                }

                "DEL" -> {
                    operatorArray.clear()
                    numberArray.clear()
                    changed = false
                    result.text = resources.getString(R.string.default_text)
                }

                "=" -> {
                    Log.d("operator", operatorArray.size.toString())
                    Log.d("number", numberArray.size.toString())
                    Log.d("OperatorDebug", "Array content: ${operatorArray.joinToString(", ")}")
                    var splits = result.text.split("+", "-", "X", "/").map { it.toDouble() }
                    numberArray.addAll(splits)
                    Log.d("NumberDebug", "Array content: ${numberArray.joinToString(", ")}")
                    var i = 0
                    var j = 0
                    while (operatorArray.size > 0) {
                        while (i < operatorArray.size) {
                            if (operatorArray[i] == "X") {
                                val temp = multiplication(numberArray[i], numberArray[i + 1])
                                Log.d("tes", temp.toString())
                                numberArray[i] = temp
                                numberArray.removeAt(i + 1)
                                operatorArray.removeAt(i)
                                Log.d("OperatorDebug", "Array content: ${operatorArray.joinToString(", ")}")
                                Log.d("NumberDebug", "Array content: ${numberArray.joinToString(", ")}")
                                continue
                            } else if (operatorArray[i] == "/") {
                                val temp = division(numberArray[i], numberArray[i + 1])
                                numberArray[i] = temp
                                numberArray.removeAt(i + 1)
                                operatorArray.removeAt(i)
                                Log.d("OperatorDebug", "Array content: ${operatorArray.joinToString(", ")}")
                                Log.d("NumberDebug", "Array content: ${numberArray.joinToString(", ")}")
                                continue
                            }
                            i++
                        }
                        if (!operatorArray.contains("X") && !operatorArray.contains("/")) {
                            while (j < operatorArray.size) {
                                if (operatorArray[j] == "+") {
                                    val temp = addition(numberArray[j], numberArray[j + 1])
                                    numberArray[j] = temp
                                    numberArray.removeAt(j + 1)
                                    operatorArray.removeAt(j)
                                    Log.d("OperatorDebug", "Array content: ${operatorArray.joinToString(", ")}")
                                    Log.d("NumberDebug", "Array content: ${numberArray.joinToString(", ")}")
                                    continue
                                } else if (operatorArray[j] == "-") {
                                    val temp = substraction(numberArray[j], numberArray[j + 1])
                                    numberArray[j] = temp
                                    numberArray.removeAt(j + 1)
                                    operatorArray.removeAt(j)
                                    Log.d("OperatorDebug", "Array content: ${operatorArray.joinToString(", ")}")
                                    Log.d("NumberDebug", "Array content: ${numberArray.joinToString(", ")}")
                                    continue
                                }
                                j++
                            }
                        }
                    }
                    val equal = numberArray[0] // change with result
                    numberArray.clear()
                    operatorArray.clear()
                    result.text = equal.toString()



                }

                else -> {
                    if (!changed){
                        result.text = ""
                        changed = true
                    }
                    result.text = result.text.toString() + view.text
//                    numberArray.add(view.text.toString().toDouble())
                }
            }
            Log.d("operator", operatorArray.size.toString())
            Log.d("number", numberArray.size.toString())

        }

    }
}