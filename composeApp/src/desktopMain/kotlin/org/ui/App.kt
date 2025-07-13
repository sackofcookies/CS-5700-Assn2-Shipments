package org.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember

import org.util.Shipment
import org.util.LocationUpdate
import org.util.ExpectedDeliveryUpdate
import org.util.NoteUpdate


@Composable
@Preview
fun App() {
    MaterialTheme {
        val shipment = remember{Shipment("created", "1", 500, "test")}
        val observer = remember{TrackerViewHelper(shipment)}
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {NoteUpdate("test", 1000).applyUpdate(shipment)}) { Text("test") }
            observer.compose()
        }
    }
}