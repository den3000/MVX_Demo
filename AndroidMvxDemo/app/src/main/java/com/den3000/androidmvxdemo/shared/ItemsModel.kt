package com.den3000.androidmvxdemo.shared

import kotlinx.coroutines.delay

class ItemsModel {

    var dataset = emptyList<String>()
        private set

    suspend fun all() {
        delay(1000)
        dataset = datasource
    }

    suspend fun filter(text: String) {
        delay(5000)
        dataset = datasource.filter { it.contains(text) }
    }

    private val datasource = listOf(
        "Multitier architecture",
        "Model–view–controller",
        "Domain-driven design",
        "Blackboard pattern",
        "Sensor–controller–actuator",
        "Presentation–abstraction–control",
        "Component-based",
        "Monolithic application",
        "Strangler fig pattern",
        "Layered",
        "Pipes and filters",
        "Database-centric",
        "Blackboard",
        "Rule-based",
        "Event-driven aka implicit invocation",
        "Publish-subscribe",
        "Asynchronous messaging",
        "Microkernel",
        "Reflection",
        "Client-server",
        "Shared nothing architecture",
        "Space-based architecture",
        "Object request broker",
        "Peer-to-peer",
        "Representational state transfer",
        "Service-oriented",
        "Cloud computing patterns",
    )
}