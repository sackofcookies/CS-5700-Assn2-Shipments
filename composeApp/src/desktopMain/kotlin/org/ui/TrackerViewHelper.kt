package org.ui

import org.util.ShipmnetObserver
import org.util.Shipment
import org.util.StatusChange

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text

import java.text.SimpleDateFormat
import java.util.Date

private val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")

class TrackerViewHelper(shipment: Shipment): ShipmnetObserver{
    val shipmentId: String = shipment.id
    val shipmentNotes = remember{mutableStateListOf<String>()}
    val shipmentUpdateHistory = remember{mutableStateListOf<StatusChange>()}
    var expectedShipmentDeliveryDate = remember{mutableStateOf<Long>(shipment.expectedDeliveryDateTimestamp)}
    var shipmentStatus = remember{mutableStateOf<String>(shipment.status)}
    var shipmentLocation = remember{mutableStateOf<String>(shipment.currentLocation)}
    init{
        shipment.notes.forEach {shipmentNotes.add(it)}
        shipment.updateHistory.forEach {shipmentUpdateHistory.add(it)}
    }

    override fun update(shipment: Shipment){
        shipmentNotes.clear()
        shipment.notes.forEach {shipmentNotes.add(it)}
        shipmentUpdateHistory.clear()
        shipment.updateHistory.forEach {shipmentUpdateHistory.add(it)}
        expectedShipmentDeliveryDate.value = shipment.expectedDeliveryDateTimestamp
        shipmentStatus.value = shipment.status
        shipmentLocation.value = shipment.currentLocation
    }

    @Composable
    public fun compose(){
        Column {
            Text("Tracking Shipment: " + shipmentId)
            Text("Status: " + shipmentStatus.value)
            Text("Location: " + shipmentLocation.value)
            Text("Expected Delivery Date: " + expectedShipmentDeliveryDate.value)
            Text("Status Updates:")
            shipmentUpdateHistory.forEach { Text("Shipment went from " + it.previousStatus + " to " + it.newStatus + " on " + it.timeStamp) }
            Text("Notes:")
            shipmentNotes.forEach { Text(it) }
        }
    }
}