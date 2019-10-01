package fr.cdreyfus.airqualitysensorapp.model

import java.util.*

data class Feed(val id: String, val name: String, val key:String)


data class FeedDataResponse(val value: String, val created_at: Date)
data class FeedDataPoint(val value: Float, val created_at: Float)