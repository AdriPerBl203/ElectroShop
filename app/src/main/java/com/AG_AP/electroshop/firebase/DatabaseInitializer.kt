package com.AG_AP.electroshop.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings

open class DatabaseInitializer {

    open var database: FirebaseFirestore = getInstance()

    fun getInstance(): FirebaseFirestore {
        database = FirebaseFirestore.getInstance()

        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings { setupCacheSize() })
            setLocalCacheSettings(persistentCacheSettings {})
        }

        database.firestoreSettings = settings

        return database
    }

    private fun setupCacheSize() {
        val settings = firestoreSettings {
            setLocalCacheSettings(persistentCacheSettings {
                // Set size to 100 MB
                setSizeBytes(1024 * 1024 * 100)
            })
        }
        database.firestoreSettings = settings
    }
}