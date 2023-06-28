package com.example.myviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myviewmodel.databinding.ActivityMainBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        displayResult()

        binding.btnCalculate.setOnClickListener{
            val width = binding.edtWidth.text.toString()
            val length = binding.edtLength.text.toString()
            val height = binding.edtHeight.text.toString()

            when {
                width.isEmpty() -> binding.edtWidth.error = "This Field Can't Be Empty"
                length.isEmpty() -> binding.edtLength.error = "This Field Can't Be Empty"
                height.isEmpty() -> binding.edtHeight.error = "This Field Can't Be Empty"
                else -> {
                    viewModel.calculate(width,length,height)
                    displayResult()
                }
            }
        }
    }

    private fun displayResult() {
        binding.tvResult.text = viewModel.result.toString()
    }
}
