package store.cru.crushcheck.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import store.cru.crushcheck.models.UserProfile

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(profile: UserProfile): Long

    @Query("SELECT * FROM  profile")
    suspend fun getData() : List<UserProfile>
}