package org.util

class Shipment(public var status:String, public var id: String, public var expectedDeliveryDateTimestamp: Long, public var currentLocation: String){

    private val _notes: MutableList<String> = mutableListOf()
    val notes
        get() = _notes.map { it }.toMutableList()
    private val _updateHistory: MutableList<ShipmentUpdate> = mutableListOf()
    val updateHistory
        get() = _updateHistory.map { it }.toMutableList()

    public fun addNote(note: String){
        _notes.add(note)
    }

    public fun addUpdate(shipmentUpdate: ShipmentUpdate){
        updateHistory.add(shipmentUpdate)
    }

}