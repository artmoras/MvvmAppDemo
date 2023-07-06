package sinno.seacs.mvvmappdemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sinno.seacs.mvvmappdemo.models.EntityNote
import sinno.seacs.mvvmappdemo.utilities.DATABASE_NAME

@Database(entities = [EntityNote::class], version = 1, exportSchema = false)
abstract class Db : RoomDatabase() {

    abstract fun getNoteDao(): DaoNote

    /**
     * This is a SingletonPattern used to make this class a Singleton
     * so there could only be one and only one instance of this class
     * because it would make no sense to have more than one database so
     * each time is used, you would use the same instance. It would only
     * create a new instance if it has not been instantiated before.
     */
    companion object {
        @Volatile
        private var INSTANCE: Db? = null

        fun getDatabase(context: Context) : Db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Db::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}