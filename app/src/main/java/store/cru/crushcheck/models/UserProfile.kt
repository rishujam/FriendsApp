package store.cru.crushcheck.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "profile"
)
data class UserProfile(
        @PrimaryKey
        val instaName:String ="",
        val phone:String="",
        val name:String="",
        val bio:String=""
)