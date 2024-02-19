package com.mykolapetryk.simpleappsettings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mykolapetryk.easydatastore.DataStore
import com.mykolapetryk.easydatastore.DataStoreValue
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
    val stringExample = DataStoreValue(
        key = "simple-saved-string",
        default = "String"
    )

    val stringListExample = DataStoreValue(
        key = "simple-saved-string-list",
        default = emptySet<String>()
    )
}

object Examples : DataStoreValues() {

    // Declaring different types of values with their keys and defaults
    val booleanExample = DataStoreValue(
        key = "boolean-example",
        default = true
    )
    val intExample = DataStoreValue(
        key = "int-example",
        default = 1
    )
    val floatExample = DataStoreValue(
        key = "float-example",
        default = 1f
    )
    val longExample = DataStoreValue(
        key = "long-example",
        default = 1L
    )
    val doubleExample = DataStoreValue(
        key = "double-example",
        default = 1.0
    )
    val stringExample = DataStoreValue(
        key = "string-example",
        default = "String"
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialization of DataStore with the objects that contain the values
        DataStore.start(SavedValues, Examples)

        setContent {
            SimpleAppSettingsTheme(dynamicColor = false, darkTheme = false) {
                val list by DataStore(SavedValues.stringListExample).readAsFlow().collectAsState(
                    initial = emptyList()
                )

                LazyColumn(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { RestartAppButton(this@MainActivity) }

                    item { Spacer() }

                    // Showcases of how to use DataStore with different types of values
                    item { BooleanShowcase() }  // Example of DataStore of Boolean
                    item { NumberShowcase() }  // Example of DataStore of Int, Float and Long
                    item { StringShowcase() }  // Example of DataStore of String

                    // Example of DataStore of StringSet
                    items(items = list.toList()) { savedString ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge,
                                text = savedString
                            )
                        }
                    }
                }
            }
        }
    }
}

