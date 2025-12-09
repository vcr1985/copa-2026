package br.com.vandodev.data.remote

import br.com.vandodev.data.remote.dto.MatchDto
import retrofit2.http.GET

interface Copa2022Api {
    @GET("matches.json")
    suspend fun getMatches(): List<MatchDto>
}