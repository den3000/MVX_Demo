package com.den3000.androidmvxdemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.den3000.androidmvxdemo.databinding.ActivityViewMvcBinding

class ViewMvcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewMvcBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMvcBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = "View MVC"

        val dataset = arrayOf(
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

        with(binding.rvItems) {
            layoutManager = LinearLayoutManager(this@ViewMvcActivity)
            adapter = ItemsAdapter(dataset)
        }
    }
}