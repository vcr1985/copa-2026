package br.com.vandodev.data.remote

import br.com.vandodev.data.remote.mappers.toDomain
import br.com.vandodev.domain.model.Match

class TeamRemoteDataSourceImpl(private val copa2022Api: Copa2022Api) : TeamRemoteDataSource {
    override suspend fun getMatches(): List<Match> {
        // Busca a lista de DTOs e a mapeia para o domínio, pulando os itens inválidos.
        return copa2022Api.getMatches().toDomain()
    }
}
