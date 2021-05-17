package store.cru.crushcheck.db

import android.content.Context
import androidx.room.*
import store.cru.crushcheck.models.UserProfile

@Database(
    entities = [UserProfile::class],
    version = 1
)
abstract class ProfileDatabase:RoomDatabase(){

    abstract fun getProfileDao(): ProfileDao

    companion object{
        @Volatile
        private var instance: ProfileDatabase?=null
        private val LOCK = Any()

        operator  fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also {
                instance =it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ProfileDatabase::class.java,
                "profile_db.db"
            ).build()
    }
}