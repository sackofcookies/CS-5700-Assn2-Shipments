package org.util

class Shipment(public var status:String, public val id: String, public var expectedDeliveryDateTimestamp: Long, currentLocation: String): Subject<ShipmnetObserver>{

    private val _notes: MutableList<String> = mutableListOf()
    val notes
        get() = _notes.map { it }.toMutableList()
    private val _updateHistory: MutableList<StatusChange> = mutableListOf()
    val updateHistory
        get() = _updateHistory.map { it }.toMutableList()

    private val observers = mutableListOf<ShipmnetObserver>()

    public var currentLocation: String = currentLocation
        set(location){
            currentLocation = location
            this.notifyObservers()
        }

    public fun addNote(note: String){
        _notes.add(note)
        this.notifyObservers()
    }

    public fun addStatus(status: String, timeStamp: Long){
        updateHistory.add(StatusChange(this.status, status, timeStamp))
        this.status = status
        this.notifyObservers()
    }

    override fun unregisterObserver(observer: ShipmnetObserver){
        observers.remove(observer)
    }
    override fun registerObserver(observer: ShipmnetObserver){
        observers.add(observer)
    }
    override fun notifyObservers(){
        observers.forEach { it.update(this) }
    }

}