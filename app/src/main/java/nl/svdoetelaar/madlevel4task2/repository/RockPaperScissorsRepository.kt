package nl.svdoetelaar.madlevel4task2.repository

import android.content.Context
import nl.svdoetelaar.madlevel4task2.dao.RockPaperScissorsDao
import nl.svdoetelaar.madlevel4task2.database.RockPaperScissorsDatabase
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissorsGame

class RockPaperScissorsRepository(context: Context) {
    private val rockPaperScissorsDao: RockPaperScissorsDao

    init {
        val database = RockPaperScissorsDatabase.getDatabase(context)
        rockPaperScissorsDao = database!!.rockPaperScissorsDao()
    }


    suspend fun getAllGames(): List<RockPaperScissorsGame> =
        rockPaperScissorsDao.getAllGames()


    suspend fun insertGame(game: RockPaperScissorsGame) =
        rockPaperScissorsDao.insertGame(game)


    suspend fun clearGameHistory() =
        rockPaperScissorsDao.clearGameHistory()


}