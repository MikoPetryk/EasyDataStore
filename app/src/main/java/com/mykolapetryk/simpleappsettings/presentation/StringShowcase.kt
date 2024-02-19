package com.mykolapetryk.simpleappsettings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mykolapetryk.easydatastore.DataStore
import com.mykolapetryk.simpleappsettings.Examples
import com.mykolapetryk.simpleappsettings.SavedValues

@Composable
fun StringShowcase() {
    val exampleStringData = DataStore(Examples.stringExample)
    val stringSetData = DataStore(SavedValues.stringListExample)

    val anotherStringData = DataStore(SavedValues.stringExample)
    val anotherStringFlow by anotherStringData.readAsFlow().collectAsState(initial = "")

    var text by remember { mutableStateOf("") }
    var textField by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Card(shape = RoundedCornerShape(24.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(
                    4.dp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    text = exampleStringData.read()
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    placeholder = {
                        Text(
                            text = "Enter string to save to Example String...",
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    },
                    onValueChange = { text = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        onClick = {
                            exampleStringData.update(text)
                            text = ""
                        },
                        colors = ButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )
                    ) {
                        Text(text = "Save string")
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = {
                            exampleStringData.reset()
                            text = ""
                        },
                        colors = ButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )
                    ) {
                        Text(text = "Reset")
                    }
                }
            }
        }

        Card(shape = RoundedCornerShape(24.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(
                    4.dp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = anotherStringFlow,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textField,
                    placeholder = {
                        Text(
                            text = "Enter string to save to Another String...",
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    },
                    onValueChange = { textField = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        onClick = {
                            stringSetData.update(stringSetData.read().apply { add(textField) })
                            anotherStringData.update(textField)
                            textField = ""
                        },
                        colors = ButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )
                    ) {
                        Text(text = "Save string")
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = {
                            stringSetData.reset()
                            anotherStringData.reset()
                            textField = ""
                        },
                        colors = ButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )
                    ) {
                        Text(text = "Reset")
                    }
                }
            }
        }
    }
}