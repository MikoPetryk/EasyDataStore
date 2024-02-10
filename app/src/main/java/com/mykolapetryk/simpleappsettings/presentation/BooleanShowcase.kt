package com.mykolapetryk.simpleappsettings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mykolapetryk.easydatastore.DataStore
import com.mykolapetryk.simpleappsettings.Examples

@Composable
fun BooleanShowcase() {
    val exampleBoolean = DataStore(Examples.booleanExample)
    val boolean by exampleBoolean.readAsFlow().collectAsState(initial = false)

    var notSavedBoolean by remember { mutableStateOf(false) }

    Card(shape = RoundedCornerShape(24.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = "Boolean - $boolean"
                )

                Switch(
                    checked = exampleBoolean.read(),
                    onCheckedChange = { exampleBoolean.updateInverted() }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = "Not saved boolean - $notSavedBoolean"
                )

                Switch(
                    checked = notSavedBoolean,
                    onCheckedChange = { notSavedBoolean = !notSavedBoolean }
                )
            }
        }
    }
}