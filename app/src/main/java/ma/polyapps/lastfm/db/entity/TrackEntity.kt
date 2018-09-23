package ma.polyapps.lastfm.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import ma.polyapps.lastfm.db.converter.ArtistConverters
import java.io.Serializable


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */

@Entity(tableName = "track")
class TrackEntity(
        @PrimaryKey
        val name: String,
        val duration: Int = 0,
        val mbid: String?,
        val url: String?,
        @TypeConverters(ArtistConverters::class)
        val artist: ArtistEntity
): Serializable