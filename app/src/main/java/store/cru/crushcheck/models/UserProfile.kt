package store.cru.crushcheck.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile"
)
data class UserProfile(
        @PrimaryKey
        val instaName:String ="",
        val phone:String="",
        val name:String="",
        val dp:String="",
        val bio:String=""
)