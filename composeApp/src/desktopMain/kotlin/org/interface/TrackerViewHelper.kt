package org.interface

import org.util.ShipmnetObserver
import org.util.Shipment
import org.util.StatusChange

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class TrackerViewHelper(shipment: Shipment): ShipmnetObserver{
    val shipmentId: String = shipment.id
    val shipmentNotes = remember{mutableStateListOf<String>()}
    val shipmentUpdateHistory = remember{mutableStateListOf<StatusChange>()}
    var expectedShipmentDeliveryDate = remember{mutableStateOf<>(shipment.expectedDeliveryDateTimestamp)}
    var shipmentStatus = remember{mutableStateOf<String>(shipment.status)}
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
    }
}