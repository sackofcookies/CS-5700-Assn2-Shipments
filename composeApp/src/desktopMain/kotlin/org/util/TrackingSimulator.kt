package org.util

import java.io.File
import kotlinx.coroutines.delay


class TrackingSimulator{
    private val shipments: MutableMap<String, Shipment> = mutableMapOf()

    fun findShipment(id: String): Shipment? = shipments.get(id)
    fun addShipment(shipment: Shipment) = shipments.put(shipment.id, shipment)

    suspend fun runSimulation(file: File){
        file.forEachLine() { 
            val entries = it.split(",", limit=3)
            if (entries[0] == "created"){
                this.addShipment(Shipment("created", entries[1]))
            }
            else{
                val shipment = this.findShipment(entries[1])
                if (shipment != null){
                    when (entries[0]){
                    "location" -> LocationUpdate(entries[3], entries[2].toLong()).applyUpdate(shipment)
                    "noteadded" -> NoteUpdate(entries[3], entries[2].toLong()).applyUpdate(shipment)
                    "delivered", "lost", "canceled" -> StatusUpdate(entries[0], entries[2].toLong()).applyUpdate(shipment)
                    "delayed", "shipped" -> ExpectedDeliveryUpdate(entries[0], entries[3].toLong(), entries[2].toLong()).applyUpdate(shipment)
                    }
                }
                
            }
            delay(1000)
        }
    }
}