package com.example.moviestmdb.core.data.movies.datasources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

class FirebaseDatabaseDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) {

    fun addToFavourite(uid: String, movieId: Int) {
        val childUpdates = hashMapOf<String, Any>(
            "favorites/$uid/$movieId" to true
        )
        database.reference.updateChildren(childUpdates)
    }

    fun removeFromFavourite(uid: String, movieId: Int) {
        val favoritesRef = database.getReference("favorites/$uid/$movieId")
        favoritesRef.removeValue()
    }

    fun observeFavouriteMovies(): Flow<Set<String>> =
        callbackFlow {
            val callback: (Set<String>) -> Unit = { list ->
                trySend(list)
            }

            val uid = auth.currentUser?.uid
            val favoritesRef = database.getReference("favorites/$uid")

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val map = snapshot.value as Map<String, Boolean>
                    callback(map.keys as Set<String>)
                }

                override fun onCancelled(error: DatabaseError) {
                    Timber.e(error.message)
                }
            }

            favoritesRef.addValueEventListener(listener)
            awaitClose { favoritesRef.removeEventListener(listener) }
        }
}