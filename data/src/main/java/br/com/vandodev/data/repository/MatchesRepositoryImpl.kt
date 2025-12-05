package br.com.vandodev.data.repository

import android.content.Context
import br.com.vandodev.domain.model.Match
import br.com.vandodev.domain.model.MatchData
import br.com.vandodev.domain.repository.MatchesRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStream

class MatchesRepositoryImpl(
    private val context: Context
) : MatchesRepository {

    override fun getMatches(): Flow<List<Match>> = flow {
        val jsonString = readJsonFromAssets("matches.json")
        val matchData = Gson().fromJson(jsonString, MatchData::class.java)
        emit(matchData.matches)
    }

    private fun readJsonFromAssets(fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}