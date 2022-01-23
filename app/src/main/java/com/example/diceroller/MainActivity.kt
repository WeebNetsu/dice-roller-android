package com.example.diceroller

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // without a type declaration, you have to tell findViewById the type
        val rollButton = findViewById<Button>(R.id.button)
        // with a type declaration you do not have to tell findViewById the type
        // val resultTextView: TextView = findViewById(R.id.textView)
        // resultTextView.text = diceRoll.toString() // will convert integer to string

        val numDiceEdit: EditText = findViewById(R.id.editTextNumber)

        // the reason the below function does not have () is because it's a lambda function
        rollButton.setOnClickListener {
            val numDice = numDiceEdit.text.toString().toIntOrNull()

            if (numDice != null) {
                if (numDice > 3 || numDice < 1) {
                    // create a small popup notification
                    Toast.makeText(this, "Num dice has to be between 1 and 3", Toast.LENGTH_LONG)
                        .show()
                } else {
                    rollDice(numDice) // roll the dice when the user clicks the button
                }
            } else {
                rollDice()
            }
        }

        rollDice() // add a random dice to the screen when the user opens the app
    }

    private fun rollDice(numDice: Int = 1) {
        val dice: Dice = Dice(6)

        for (die in 1..numDice) {
            val diceRoll: Int = dice.roll()

            // below is the basic way of doing it
            /* when (diceRoll) {
                // setImageResource -> change image
                // R.drawable.dice_1 -> images are stored in drawable, we then select dice_1 image
                1 -> diceImage.setImageResource(R.drawable.dice_1)
                2 -> diceImage.setImageResource(R.drawable.dice_2)
                ...
            }*/

            // however, you can do the below instead if you want to type less
            val drawableResource = when (diceRoll) {
                // setImageResource -> change image
                // R.drawable.dice_1 -> images are stored in drawable, we then select dice_1 image
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

            val diceImage: ImageView = when (die) {
                1 -> findViewById(R.id.imageView)
                2 -> findViewById(R.id.imageView2)
                else -> findViewById(R.id.imageView3)
            }

            // then at the end you set it like this
            diceImage.setImageResource(drawableResource)

            // this will create a new contentDescription (for screen readers)
            // every time you roll the dice
            diceImage.contentDescription = diceRoll.toString()
            diceImage.visibility = View.VISIBLE // make image visible
        }

        // if image still visible, but not in use, remove it
        if (numDice < 3) {
            findViewById<ImageView>(R.id.imageView3).visibility = View.INVISIBLE

            if (numDice < 2) {
                findViewById<ImageView>(R.id.imageView2).visibility = View.INVISIBLE
            }
        }
    }
}

class Dice(private val numSides: Int = 6) {
    fun roll(): Int {
        // 1..6 -> range, will basically be [1, 2, 3, 4, 5, 6] (but not as array)
        // you can go .random() instead of .shuffled().last(), however I feel random()
        // isn't as random. NOTE: the below is much slower than random, but for a list
        // as small as this it won't make a noticeable difference
        return (1..numSides).shuffled().last()
    }
}