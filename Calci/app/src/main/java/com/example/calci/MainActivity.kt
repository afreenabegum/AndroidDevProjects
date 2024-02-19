package com.example.calci

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.calci.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    //ViewBinding
    private lateinit var binding: ActivityMainBinding

    // setting values for number, any error or dot
    var lastNumeric = false
    var stateError = false
    var dotValue = false

    private lateinit var expression : Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onAllClearClick(view: View) {
        // when we click this btn, values should be reset and data should be cleared
        binding.inputText.text = ""
        binding.outputText.text = ""
        stateError = false
        lastNumeric = false
        dotValue = false
        binding.outputText.visibility = View.GONE
    }

    fun onOperatorClick(view: View) {
        if(!stateError && lastNumeric)
            binding.inputText.append((view as Button).text)
            lastNumeric = false
            dotValue = false
            onEqualClick(view)
    }

    fun onClearClick(view: View) {
        // Not implemented, additional clear method made invisible

    }

    fun onDigitClick(view: View) {

        if(stateError){
            binding.inputText.text = (view as Button).text
            stateError = false

        }else{
            binding.inputText.append((view as Button).text)
        }
        lastNumeric = true
        onEqualClick(view)
    }


    fun onEqualClick(view: View) {

        if(lastNumeric && !stateError){ // number and no error
            val text = binding.inputText.text.toString()  // getting text
            expression = ExpressionBuilder(text).build()    // logic for calci

            try{
                val result = expression.evaluate()
                binding.outputText.visibility = View.VISIBLE
//                binding.inputText.visibility = View.INVISIBLE// making op screen visible
                binding.outputText.text = "=$result"

            }catch(ex : ArithmeticException){
                // CAUGHT WITH ANY ERROR
                Log.e("Evaluate Error", ex.toString())
                binding.outputText.text = "Error"   // displaying
                stateError = true   // we got error, so making it true
                lastNumeric = false // still the num is entered, chnc of entering the number
            }
        }

    }
    fun onDeleteClick(view: View) {
        // backspace
      //  val size =  binding.inputText.text.toString().length
        binding.inputText.text = binding.inputText.text.toString().dropLast(1)
        try {
            val lastChar = binding.inputText.toString().last()
            if(lastChar.isDigit()){
                onEqualClick(view)
            }
        }catch (e : Exception){
            binding.outputText.text =  ""
            binding.outputText.visibility = View.GONE
            Log.e("last char error",e.toString())
        }
        onEqualClick(view)
//        try{
//            val lastch
//        }catch(){
//
//
//        }
    }
}
