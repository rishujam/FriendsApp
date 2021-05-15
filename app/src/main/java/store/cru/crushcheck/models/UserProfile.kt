package store.cru.crushcheck.models

import androidx.room.Entity

@Entity(
    tableName = "profile"
)
data class UserProfile(
    val instaName:String ="",
    val phone:String="",
    val name:String="",
    val dp:String="",
    val bio:String=""
)