package nl.svdoetelaar.madlevel4task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.svdoetelaar.madlevel4task2.dao.RockPaperScissorsDao
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissorsGame

@Database(entities = [RockPaperScissorsGame::class], version = 2, exportSchema = false)
abstract class RockPaperScissorsDatabase : RoomDatabase() {

    abstract fun rockPaperScissorsDao(): RockPaperScissorsDao

    companion object {
        private const val DATABASE_NAME = "SHOPPING_LIST_DATABASE"

        @Volatile
        private var shoppingListRoomDatabaseInstance: RockPaperScissorsDatabase? = null

        fun getDatabase(context: Context): RockPaperScissorsDatabase? {
            if (shoppingListRoomDatabaseInstance == null) {
                synchronized(RockPaperScissorsDatabase::class.java) {
                    if (shoppingListRoomDatabaseInstance == null) {
                        shoppingListRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            RockPaperScissorsDatabase::class.java,
                            DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return shoppingListRoomDatabaseInstance
        }
    }
}