package com.example.myapplication.myapplication.usamas.data.datastore.network

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Singleton

@Singleton
class DataStore(context: Context) {

    /**
     * Annotation defining a preference key.
     *
     * @param key the key value
     */
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Key(val key: String)

    private val sharedPrefs = EncryptedSharedPreferences.create(
        "News-DataStore",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Restores an instance of [T].
     *
     *     Example usage:
     *
     *     val restored: SomeClass? = restore(SomeClass::class.java, GsonUtils::deserialize)
     *
     * @param clazz the type of data you are restoring
     * @param b a function block that accepts a string and produces an instance of [T]
     * @return an instance of [T] or null
     */
    fun <T : Any> restore(clazz: Class<T>, b: (String?) -> T?): T? {

        val key = clazz.getAnnotation(Key::class.java)?.key

        checkNotNull(key)

        val data = sharedPrefs.getString(key, null)

        return b.invoke(data)

    }

    /**
     * Saves an instance of [T].
     *
     *     Example usage:
     *
     *     save(data, GsonUtils::serialize)
     *
     * @param data the data instance you need persisted
     * @param b a function block that accepts a data instance and serializes the data instance to
     *          a String
     */
    fun <T : Any> save(data: T, b: (T) -> String) {

        val key = data::class.java.getAnnotation(Key::class.java)?.key

        checkNotNull(key)

        sharedPrefs.edit().putString(key, b.invoke(data)).apply()

    }

    fun <T : Any> clear(clazz: Class<T>) {

        val key = clazz.getAnnotation(Key::class.java)?.key

        checkNotNull(key)

        sharedPrefs.edit().putString(key, null).apply()

    }

}