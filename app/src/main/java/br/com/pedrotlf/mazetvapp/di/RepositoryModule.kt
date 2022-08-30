package br.com.pedrotlf.mazetvapp.di

import br.com.pedrotlf.mazetvapp.data.api.MazeTvApi
import br.com.pedrotlf.mazetvapp.data.repository.MazeRepositoryImpl
import br.com.pedrotlf.mazetvapp.domain.repository.MazeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMazeRepository(mazeTvApi: MazeTvApi): MazeRepository {
        return MazeRepositoryImpl(mazeTvApi)
    }
}