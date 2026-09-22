package moe.rukamori.archivetune.download

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import moe.rukamori.archivetune.db.MusicDatabase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FlacDownloaderEntryPoint {
    fun flacDownloadProvider(): FlacDownloadProvider

    fun database(): MusicDatabase
}
