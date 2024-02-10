package com.mykolapetryk.simpleappsettings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mykolapetryk.easydatastore.DataStore
import com.mykolapetryk.easydatastore.SettingValue
import com.mykolapetryk.easydatastore.DataStoreValues
import com.mykolapetryk.simpleappsettings.presentation.BooleanShowcase
import com.mykolapetryk.simpleappsettings.presentation.NumberShowcase
import com.mykolapetryk.simpleappsettings.presentation.RestartAppButton
import com.mykolapetryk.simpleappsettings.presentation.Spacer
import com.mykolapetryk.simpleappsettings.presentation.StringShowcase
import com.mykolapetryk.simpleappsettings.ui.theme.SimpleAppSettingsTheme

// Creating object that holds all values that you want to save
// For values to be recognized as DataStore - extend object from DataStoreValues class
object SavedValues : DataStoreValues() {

    // default - is value that will be set as starting
    // It's type determines what value will be stored
    // Currently supporting: Boolean, String, Int, Float and Long

    // key - is value that used for finding data internally
    // It should be unique for each value that needs to be saved
    val stringExample = SettingValue(
        key = "simple-saved-string",
        default = "String"
    )
}

object Examples : DataStoreValues() {

    // Declaring different types of values with their keys and defaults
    val booleanExample = SettingValue(
        key = "boolean-example",
        default = true
    )
    val intExample = SettingValue(
        key = "int-example",
        default = 1
    )
    val floatExample = SettingValue(
        key = "float-example",
        default = 1f
    )
    val longExample = SettingValue(
        key = "long-example",
        default = 1L
    )
    val stringExample = SettingValue(
        key = "string-example",
        default = "String"
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialization of DataStore with the objects that contain the values
        DataStore.start(SavedValues, Examples)

        super.onCreate(savedInstanceState)
        setContent {
            SimpleAppSettingsTheme(dynamicColor = false, darkTheme = false) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RestartAppButton(this@MainActivity, Modifier.weight(1f))

                    Spacer()

                    // Showcases of how to use DataStore with different types of values
                    BooleanShowcase()  // Example of DataStore of Boolean
                    NumberShowcase()  // Example of DataStore of Int, Float and Long
                    StringShowcase()  // Example of DataStore of String
                }
            }
        }
    }
}

