package com.example.apitestapp.datastore

import android.content.Context

object SetingsDataStoreSingleton {
    private var instance: SetingsDataStore? = null

    fun getInstance(context: Context): SetingsDataStore {
        return instance ?: synchronized(this) {
            instance ?: SetingsDataStore(context).also { instance = it }
        }
    }
}