package org.util

import kotlin.test.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import java.io.File
import kotlin.io.path.createTempFile

class TrackingSimulatorTest {

    @Test
    fun testAddAndFindShipment() {
        val simulator = TrackingSimulator()
        val shipment = Shipment("created", "abc123")
        simulator.addShipment(shipment)
        val retrieved = simulator.findShipment("abc123")
        assertEquals(shipment, retrieved)
    }

    @Test
    fun testRunSimulationCreatesAndUpdatesShipments() {runBlocking {
        val tempFile = createTempFile().toFile()
        tempFile.writeText(
            """
            created,abc123
            location,abc123,1000,New York
            noteadded,abc123,1001,Arrived at warehouse
            shipped,abc123,1002,1650000000
            """.trimIndent()
        )

        val simulator = TrackingSimulator()

        val job = launch{
            simulator.runSimulation(tempFile, 0)
        }

        job.join()




        val shipment = simulator.findShipment("abc123")
        assertNotNull(shipment)
        assertEquals("shipped", shipment.status)
        assertEquals("New York", shipment.currentLocation)
        assertEquals(listOf("Arrived at warehouse"), shipment.notes)
        assertEquals(1650000000L, shipment.expectedDeliveryDateTimestamp)

        tempFile.delete()
    }}

    @Test
    fun testRunSimulationHandlesUnknownShipmentGracefully()  {runBlocking {
        val tempFile = createTempFile().toFile()
        tempFile.writeText(
            """
            location,doesnotexist,1000,Nowhere
            """.trimIndent()
        )

        val simulator = TrackingSimulator()
        // Should not throw exception even though shipment doesn't exist
        simulator.runSimulation(tempFile)
        val nonexistent = simulator.findShipment("doesnotexist")
        assertNull(nonexistent)
        tempFile.delete()
    }}

    @Test
    fun testRunSimulationWithInvalidLine() {
        runBlocking {
            val file = createTempFile().toFile()
            file.writeText("bad input")
            val sim = TrackingSimulator()
            sim.runSimulation(file)
            file.delete()
        }
    }
}