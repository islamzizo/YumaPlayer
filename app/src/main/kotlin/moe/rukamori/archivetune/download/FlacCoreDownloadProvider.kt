package moe.rukamori.archivetune.download

import moe.rukamori.archivetune.constants.FlacQuality
import moe.rukamori.archivetune.db.entities.Song
import moe.rukamori.archivetune.flaccore.model.TrackQuery
import moe.rukamori.archivetune.flaccore.streaming.FlacStreamRegistry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlacCoreDownloadProvider @Inject constructor(
    private val registry: FlacStreamRegistry,
) : FlacDownloadProvider {
    override suspend fun resolve(song: Song, quality: FlacQuality): FlacDownloadSource? {
        val artistName = song.artists
            .mapNotNull { it.name.takeIf(String::isNotBlank) }
            .joinToString(", ")

        val query = TrackQuery(
            artist = artistName,
            title = song.title,
            album = song.album?.title,
            isrc = song.song.isrc?.takeIf { it.isNotBlank() },
            durationMs = song.song.duration * 1000L,
            explicit = song.song.explicit,
        )

        val flacUrl = registry.resolve(query, quality.streamQuality) ?: return null

        return FlacDownloadSource(
            url = flacUrl.url,
            expiresAtMs = flacUrl.expiresAtMs,
            codec = flacUrl.codec,
            bitsPerSample = flacUrl.bitsPerSample,
            sampleRateHz = flacUrl.sampleRateHz,
            bitrateKbps = flacUrl.bitrateKbps,
            coverArtUrl = flacUrl.coverArtUrl,
            origin = flacUrl.origin,
        )
    }
}
