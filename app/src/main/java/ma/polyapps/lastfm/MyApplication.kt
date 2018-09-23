package ma.polyapps.lastfm

import android.app.Application
import android.content.Context
import ma.polyapps.lastfm.db.AppDatabase
import ma.polyapps.lastfm.db.DataRepository


/**
 * Created by Elmehdi Mellouk on 21/09/18.
 * mellouk.elmehdi@gmail.com
 */
class MyApplication: Application() {

    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    fun getDatabase(): AppDatabase? {
        return AppDatabase.getInstance(appContext!!)
    }

    fun getRepository(): DataRepository? {
        return DataRepository(getDatabase())
    }
}
