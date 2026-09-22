package moe.rukamori.archivetune.download

import moe.rukamori.archivetune.constants.FlacQuality
import moe.rukamori.archivetune.db.entities.Song

data class FlacDownloadSource(
    val url: String,
    val expiresAtMs: Long,
    val codec: String? = null,
    val bitsPerSample: Int? = null,
    val sampleRateHz: Int? = null,
    val bitrateKbps: Int? = null,
    val coverArtUrl: String? = null,
    val origin: String,
)

interface FlacDownloadProvider {
    suspend fun resolve(song: Song, quality: FlacQuality): FlacDownloadSource?
}
