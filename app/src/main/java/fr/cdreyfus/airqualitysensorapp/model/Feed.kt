package fr.cdreyfus.airqualitysensorapp.model

import java.util.*

data class Feed(val id: String, val name: String, val key:String)


data class FeedDataPoint(val value: String, val created_at: Date)