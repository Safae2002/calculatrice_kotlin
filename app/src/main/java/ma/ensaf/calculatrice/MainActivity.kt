package ma.ensaf.calculatrice
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import org.mariuszgromada.math.mxparser.Expression
import android.content.Intent
import androidx.core.view.GravityCompat


import android.view.MenuItem

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var variable: String
    private lateinit var input: TextView
    private lateinit var output: TextView
    private var memoryValue: Double = 0.0
    private var lastCharEntered: Char = ' '
    override fun setSupportActionBar(toolbar: androidx.appcompat.widget.Toolbar?) {
        super.setSupportActionBar(toolbar)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawer:DrawerLayout=findViewById(R.id.drawerLayout)
        val Navigation:NavigationView=findViewById(R.id.nav_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        input = findViewById(R.id.input)
        output = findViewById(R.id.output)

        variable = ""
        Navigation.bringToFront()
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        Navigation.setNavigationItemSelectedListener(this)




        val egale: Button = findViewById(R.id.egale)
        val clearButton: Button = findViewById(R.id.c)
        val read:Button=findViewById(R.id.MR)


        val number0: Button = findViewById(R.id.button_0)
        val number1: Button = findViewById(R.id.button_1)
        val number2: Button = findViewById(R.id.button_2)
        val number3: Button = findViewById(R.id.button_3)
        val number4: Button = findViewById(R.id.button_4)
        val number5: Button = findViewById(R.id.button_5)
        val number6: Button = findViewById(R.id.button_6)
        val number7: Button = findViewById(R.id.button_7)
        val number8: Button = findViewById(R.id.button_8)
        val number9: Button = findViewById(R.id.button_9)
        val virgule: Button = findViewById(R.id.virgulr)
        val modulo: Button = findViewById(R.id.modulo)
        val addition: Button = findViewById(R.id.plus)
        val multiplication:Button=findViewById(R.id.fois)
        val sustraction: Button = findViewById(R.id.moin)
        val division: Button = findViewById(R.id.division)
        val buttonMPlus:Button=findViewById(R.id.mLeft)
        val buttonMmoin:Button=findViewById(R.id.mRight)
        val buttonMc:Button=findViewById(R.id.MC)
        val pareRight:Button=findViewById(R.id.pareRight)
        val pareLeft:Button=findViewById(R.id.pareLeft)


        pareRight.setOnClickListener{
            input.text = Add_data(")")
        }
        pareLeft.setOnClickListener{
            input.text = Add_data("(")
        }

        egale.setOnClickListener {
            showResult()
        }

        clearButton.setOnClickListener {
            input.text = ""
            output.text = ""
        }



        number0.setOnClickListener {
            input.text = Add_data("0")
        }

        number1.setOnClickListener {
            input.text = Add_data("1")
        }

        number2.setOnClickListener {
            input.text = Add_data("2")
        }

        number3.setOnClickListener {
            input.text = Add_data("3")
        }

        number4.setOnClickListener {
            input.text = Add_data("4")
        }

        number5.setOnClickListener {
            input.text = Add_data("5")
        }

        number6.setOnClickListener {
            input.text = Add_data("6")
        }

        number7.setOnClickListener {
            input.text = Add_data("7")
        }

        number8.setOnClickListener {
            input.text = Add_data("8")
        }

        number9.setOnClickListener {
            input.text = Add_data("9")
        }

        virgule.setOnClickListener {
            input.text = Add_data(".")
        }

        read.setOnClickListener{
            output.text=getValueToStore().toString()
        }

        addition.setOnClickListener {
            handleOperatorButton('+')
        }

        sustraction.setOnClickListener {
            handleOperatorButton('-')
        }

        multiplication.setOnClickListener {
            handleOperatorButton('*')
        }

        division.setOnClickListener {
            handleOperatorButton('/')
        }

        modulo.setOnClickListener {
            handleOperatorButton('%')
        }

        buttonMc.setOnClickListener{
            clearMemory()
        }
        buttonMPlus.setOnClickListener {
            memoryValue += Expression(getInputExpression()).calculate()
        }

        buttonMmoin.setOnClickListener {
            // Soustrayez la valeur actuelle de la mémoire
            memoryValue -= Expression(getInputExpression()).calculate()
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.standard -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            R.id.scientifique -> {
                val intent = Intent(this,MainActivity2::class.java)
                startActivity(intent)
            }

        }
        val drawer: DrawerLayout = findViewById(R.id.drawerLayout)
        drawer.closeDrawer(GravityCompat.START)

        return false
    }






    private fun Add_data(param: String): String {
        variable = input.text.toString()
        return "$variable$param"
    }

    private fun getInputExpression(): String {
        var expression = input.text.toString().replace(Regex("x"), "*")
        return expression
    }

    private fun showResult() {
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            output.text = result.toString()
            input.text=""
        } catch (e: Exception) {
            output.text = "Error"
        }
    }
    private fun getValueToStore(): Double {
        val valueToStore = memoryValue
        output.text = valueToStore.toString()
        return valueToStore
    }
    private fun clearMemory(){
       memoryValue=0.0
    }
    private fun handleOperatorButton(operator: Char) {
        val inputText = input.text.toString()
        if (inputText.isNotEmpty()) {
            val lastChar = inputText.last()

            if (lastChar.isOperator() && operator != lastChar) {
                input.text = inputText.dropLast(1) + operator
            } else if (!lastChar.isOperator()) {
                input.text = Add_data(operator.toString())
            }
        } else {

            input.text = Add_data(operator.toString())
        }
    }

    private fun Char.isOperator(): Boolean {
        return this in setOf('+', '-', '*', '/', '%')
    }


}