package com.den3000.androidmvxdemo

class ItemsModel {

    fun all() : List<String> {
        return dataset
    }

    fun filter(string: String) : List<String> {
        return dataset.filter { it.contains(string) }
    }

    private val dataset = listOf(
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